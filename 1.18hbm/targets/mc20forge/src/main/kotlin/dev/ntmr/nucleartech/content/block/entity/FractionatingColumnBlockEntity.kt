package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.item.upgrades.MachineUpgradeItem
import dev.ntmr.nucleartech.content.item.upgrades.SpeedUpgrade
import dev.ntmr.nucleartech.content.menu.FractionatingColumnMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank

class FractionatingColumnBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(
    NTechBlockEntities.fractionatingColumnBlockEntityType.get(), pos, state),
    TickingServerBlockEntity, RecipeHolder, SpeedUpgradeableMachine
{
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(7, ItemStack.EMPTY)
    override val upgradeSlots = 3..6

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)
    
    // Tanks
    val inputTank = FluidTank(16000) { it.fluid.isSame(NTechFluids.oil.source.get()) }
    val heavyOilTank = FluidTank(16000)
    val lightOilTank = FluidTank(16000)
    val naphthaTank = FluidTank(16000)
    val gasTank = FluidTank(16000)

    var progress = 0
    var maxProgress = 100

    override fun createMenu(windowID: Int, inventory: Inventory) = FractionatingColumnMenu(windowID, inventory, this)
    override val defaultName = Component.translatable("container.nucleartech.fractionating_column")

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::speedUpgradeLevel, this::speedUpgradeLevel::set))
    }

    override val shouldPlaySoundLoop = false 
    override val soundLoopEvent get() = throw IllegalStateException()
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 50}HE/t"))
        else -> emptyList()
    }
    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel))

    private val consumption get() = 100 + (speedUpgradeLevel * 50)
    private fun calculateMaxProgress() = 100 - (speedUpgradeLevel * 20)

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val energyItemSlot = mainInventory[2]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)

        MachineUpgradeItem.applyUpgrades(this, upgradeSlots.map { mainInventory[it] })
        maxProgress = calculateMaxProgress().coerceAtLeast(1)
        
        var isProcessing = false
        
        if (inputTank.fluidAmount >= 100 && energy >= consumption) {
             if (canFitOutput(25)) {
                 energy -= consumption
                 progress++
                 isProcessing = true
                 if (progress >= maxProgress) {
                     inputTank.drain(100, IFluidHandler.FluidAction.EXECUTE)
                     heavyOilTank.fill(FluidStack(NTechFluids.heavyOil.source.get(), 25), IFluidHandler.FluidAction.EXECUTE)
                     lightOilTank.fill(FluidStack(NTechFluids.lightOil.source.get(), 25), IFluidHandler.FluidAction.EXECUTE)
                     naphthaTank.fill(FluidStack(NTechFluids.naphtha.source.get(), 25), IFluidHandler.FluidAction.EXECUTE)
                     gasTank.fill(FluidStack(NTechFluids.petroleumGas.source.get(), 25), IFluidHandler.FluidAction.EXECUTE)
                     progress = 0
                 }
             }
        } else {
             progress = 0
        }
        
        if (state.getValue(BlockStateProperties.LIT) != isProcessing) {
             level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, isProcessing))
        }
    }
    
    private fun canFitOutput(amount: Int): Boolean {
        return heavyOilTank.fill(FluidStack(NTechFluids.heavyOil.source.get(), amount), IFluidHandler.FluidAction.SIMULATE) == amount &&
               lightOilTank.fill(FluidStack(NTechFluids.lightOil.source.get(), amount), IFluidHandler.FluidAction.SIMULATE) == amount &&
               naphthaTank.fill(FluidStack(NTechFluids.naphtha.source.get(), amount), IFluidHandler.FluidAction.SIMULATE) == amount &&
               gasTank.fill(FluidStack(NTechFluids.petroleumGas.source.get(), amount), IFluidHandler.FluidAction.SIMULATE) == amount
    }

    override fun isItemValid(slot: Int, stack: ItemStack) = when (slot) {
        2 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        else -> true
    }
    
    override fun setRecipeUsed(recipe: Recipe<*>?) {}
    override fun getRecipeUsed(): Recipe<*>? = null
    override fun awardUsedRecipes(player: Player, items: MutableList<ItemStack>) {}

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.putInt("Progress", progress)
        tag.put("InputTank", inputTank.writeToNBT(CompoundTag()))
        tag.put("HeavyTank", heavyOilTank.writeToNBT(CompoundTag()))
        tag.put("LightTank", lightOilTank.writeToNBT(CompoundTag()))
        tag.put("NaphthaTank", naphthaTank.writeToNBT(CompoundTag()))
        tag.put("GasTank", gasTank.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        progress = tag.getInt("Progress")
        inputTank.readFromNBT(tag.getCompound("InputTank"))
        heavyOilTank.readFromNBT(tag.getCompound("HeavyTank"))
        lightOilTank.readFromNBT(tag.getCompound("LightTank"))
        naphthaTank.readFromNBT(tag.getCompound("NaphthaTank"))
        gasTank.readFromNBT(tag.getCompound("GasTank"))
    }
    
    init {
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 100_000
        const val ENERGY_TRANSFER_RATE = 1000
    }
}
