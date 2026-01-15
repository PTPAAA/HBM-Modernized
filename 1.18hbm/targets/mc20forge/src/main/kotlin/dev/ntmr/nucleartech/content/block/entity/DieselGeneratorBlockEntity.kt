package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.block.entity.transmitters.CableBlockEntity
import dev.ntmr.nucleartech.content.fluid.NTechFluidTank
import dev.ntmr.nucleartech.content.fluid.tryEmptyFluidContainerAndMove
import dev.ntmr.nucleartech.content.menu.DieselGeneratorMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.FluidStackDataSlot
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.system.capability.item.AccessLimitedInputItemHandler
import dev.ntmr.nucleartech.system.capability.item.AccessLimitedOutputItemHandler
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidType
import net.minecraftforge.fluids.capability.IFluidHandler
import java.util.function.Predicate

class DieselGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(NTechBlockEntities.dieselGeneratorBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }
        
    val tank = object : NTechFluidTank(MAX_FLUID, { NTechFluids.diesel.getSourceFluid().isSame(it.fluid) || NTechFluids.oil.getSourceFluid().isSame(it.fluid) }) {
         override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = FluidStack.EMPTY
         override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = FluidStack.EMPTY
    }
    
    val energyStorage = EnergyStorageExposed(MAX_ENERGY, 0, MAX_TRANSFER)

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(3, ItemStack.EMPTY) // 0: fluid input, 1: fluid output (container), 2: battery charge

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent
        2 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent
        else -> false
    }

    override fun createMenu(windowID: Int, inventory: Inventory) = DieselGeneratorMenu(windowID, inventory, this)
    // TODO: LangKey
    override val defaultName: Component = Component.translatable("container.nucleartech.diesel_generator")

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(FluidStackDataSlot.create(tank, isClientSide()))
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
    }
    
    // Simple burn logic: 1 mB diesel -> X Energy?
    // Let's say 1 mB diesel = 1000 HE? 
    // HBM 1.7.10: Diesel is high energy.
    // For now simple implementation: consume 1 mB per tick if needed?
    // Or consume batch.
    
    override val shouldPlaySoundLoop = true
    // TODO: Diesel sound
    override val soundLoopEvent get() = net.minecraft.sounds.SoundEvents.MINECART_RIDING 
    override val soundLoopStateMachine = SoundLoopBlockEntity.SoundStateMachine(this)

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        var contentsChanged = false
        val wasLit = state.getValue(BlockStateProperties.LIT)
        var isLit = false

        // Fluid Item Handling
        val fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[0], tank, this, tank.space, true)
        if (fluidTransferResult.success) mainInventory[0] = fluidTransferResult.result

        // Burning Logic
        if (energy < MAX_ENERGY) {
            if (tank.fluidAmount > 0) {
                // Determine fuel value
                val fuelValue = if (NTechFluids.diesel.getSourceFluid().isSame(tank.fluid.fluid)) 200 else 100 // Diesel better than Oil
                
                // Consumption rate? 
                // Let's consume 1 mb per tick for continuous generation?
                // Or only if we have room for energy.
                
                // If we have room for 'fuelValue' energy
                if (MAX_ENERGY - energy >= fuelValue) {
                     tank.fluid.shrink(1)
                     energy += fuelValue
                     isLit = true
                     contentsChanged = true
                }
            }
        }

        // Output Energy to Battery Item
        if (energy > 0 && !mainInventory[2].isEmpty) {
             transferEnergy(energyStorage, mainInventory[2])
        }

        // Output Energy to Neighbors
        if (energy > 0) {
             val hungryConsumers = Direction.values().associateWith {
                val tileEntity = level.getBlockEntity(blockPos.relative(it)) ?: return@associateWith null
                if (tileEntity is CableBlockEntity) return@associateWith null // Cables handle their own extraction usually? Or we push to them? 
                // In this mod, Cable pulls? Checking Cable.kt: 
                // Cable implements AbstractTransmitter.
                // EnergyNetwork.extractEnergy... 
                // Usually GENERATORS need to PUSH to cables if cables are passive recipients?
                // Or Cables form a network that extracts? 
                // CombustionGenerator pushes: `transferEnergy(energyStorage, consumer, energyForEach)` where consumer is capability.
                // It filters OUT CableBlockEntity. Why?
                // `if (tileEntity is CableBlockEntity) return@associateWith null`
                // This implies CombustionGenerator does NOT push to cables directly? 
                // Maybe Cables actively pull?
                // Checking EnergyNetwork again. `networkEmitter`?
                // `EnergyEmissionTicker`: `isEmitter(member) = member.extractEnergy(...) > 0`
                // So the NETWORK extracts from the generator if the generator exposes energy capability.
                // So we just need to expose capability.
                
                // BUT CombustionGenerator.kt lines 110-122 explicitly PUSH to neighbors EXCEPT cables.
                // This means it pushes to adjacent machines directly, but relies on Network for cables?
                // Correct.
                
                tileEntity.getCapability(ForgeCapabilities.ENERGY, it.opposite)
            }.filterValues { it != null && it.isPresent }.map { (_, consumer) -> consumer!!.resolve().get() }

            if (hungryConsumers.isNotEmpty()) {
                val energyForEach = energy / hungryConsumers.size
                for (consumer in hungryConsumers) {
                    transferEnergy(energyStorage, consumer, energyForEach)
                }
            }
        }
        
        if (wasLit != isLit) {
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, isLit))
            contentsChanged = true
        }

        if (contentsChanged) setChanged()
        
        soundLoopStateMachine.tick()
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        tank.readFromNBT(tag.getCompound("FuelTank"))
        energy = tag.getInt("Energy")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("FuelTank", tank.writeToNBT(CompoundTag()))
        tag.putInt("Energy", energy)
    }
    
    private val inputInventory = AccessLimitedInputItemHandler(this, 0)
    private val outputInventory = AccessLimitedOutputItemHandler(this, 1)

    init {
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::tank)
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
        registerCapabilityHandler(ForgeCapabilities.ITEM_HANDLER, this::inputInventory, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
        registerCapabilityHandler(ForgeCapabilities.ITEM_HANDLER, this::outputInventory, Direction.DOWN)
    }

    companion object {
        const val MAX_ENERGY = 500_000
        const val MAX_FLUID = FluidType.BUCKET_VOLUME * 16
        const val MAX_TRANSFER = 2000
    }
}
