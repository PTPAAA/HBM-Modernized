package dev.ntmr.nucleartech.content.block.entity.rbmk

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.block.entity.IOConfiguration
import dev.ntmr.nucleartech.content.block.entity.IODelegatedBlockEntity
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPort
import dev.ntmr.nucleartech.content.block.rbmk.RBMKColumnBlock
import dev.ntmr.nucleartech.content.entity.RBMKDebris
import dev.ntmr.nucleartech.content.fluid.FluidInputTank
import dev.ntmr.nucleartech.content.fluid.FluidOutputTank
import dev.ntmr.nucleartech.content.fluid.ForceableFluidHandler
import dev.ntmr.nucleartech.content.fluid.MutableFluidTank
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKBoilerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.FluidStackDataSlot
import dev.ntmr.nucleartech.system.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.registries.ForgeRegistries
import kotlin.math.min

class RBMKBoilerBlockEntity(pos: BlockPos, state: BlockState) : InventoryRBMKBaseBlockEntity(NTechBlockEntities.rbmkBoilerBlockEntityType.get(), pos, state),
    ForceableFluidHandler, IODelegatedBlockEntity
{
    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK boilers")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)
    override val defaultName = LangKeys.CONTAINER_RBMK_BOILER.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = RBMKBoilerMenu(windowID, inventory, this)

    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        val isClient = isClientSide()
        menu.track(FluidStackDataSlot.create(waterTank, isClient))
        menu.track(FluidStackDataSlot.create(steamTank, isClient))
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val heatCap = steamTank.fluid.rawFluid.fluidType.temperature
        val heatProvided = heat - heatCap

        if (heatProvided > 0) {
            val heatConsumption = NuclearConfig.rbmk.boilerHeatConsumption.get()
            val steamType = steamTank.fluid.rawFluid
            val steamFactor = when {
                steamType.isSame(NTechFluids.steam.source.get()) -> 1.0
                steamType.isSame(NTechFluids.steamHot.source.get()) -> 10.0
                steamType.isSame(NTechFluids.steamSuperHot.source.get()) -> 100.0
                steamType.isSame(NTechFluids.steamUltraHot.source.get()) -> 1000.0
                else -> Double.POSITIVE_INFINITY
            }

            if ((waterTank.fluidAmount * 100.0 / steamFactor).toInt() > 0) {
                val waterUsed: Int = min((heatProvided / heatConsumption).toInt(), waterTank.fluidAmount)
                val steamProduced: Int = (waterUsed * 100.0 / steamFactor).toInt()

                if (steamProduced > 0) {
                    waterTank.forceFluid(FluidStack(waterTank.fluid.rawFluid, waterTank.fluidAmount - waterUsed))
                    steamTank.forceFluid(FluidStack(steamTank.fluid.rawFluid, (steamTank.fluidAmount + steamProduced).coerceAtMost(steamTank.capacity)))

                    heat -= waterUsed * heatConsumption
                }
            }
        }

        super.serverTick(level, pos, state)
    }

    override fun onMelt(reduce: Int) {
        super.onMelt(reduce)

        val count = 1 + levelUnchecked.random.nextInt(2)
        for (i in 0 until count) spawnDebris(RBMKDebris.DebrisType.BLANK)
    }

    fun cycleSteamType() {
        val steamType = steamTank.fluid.rawFluid

        when {
            steamType.isSame(NTechFluids.steam.source.get()) -> steamTank.forceFluid(FluidStack(NTechFluids.steamHot.source.get(), steamTank.fluidAmount / 10))
            steamType.isSame(NTechFluids.steamHot.source.get()) -> steamTank.forceFluid(FluidStack(NTechFluids.steamSuperHot.source.get(), steamTank.fluidAmount / 10))
            steamType.isSame(NTechFluids.steamSuperHot.source.get()) -> steamTank.forceFluid(FluidStack(NTechFluids.steamUltraHot.source.get(), steamTank.fluidAmount / 10))
            steamType.isSame(NTechFluids.steamUltraHot.source.get()) -> steamTank.forceFluid(FluidStack(NTechFluids.steam.source.get(), min(steamTank.fluidAmount * 1000, steamTank.capacity)))
            else -> steamTank.forceFluid(FluidStack(NTechFluids.steam.source.get(), 0))
        }

        markDirty()
    }

    private val validSteams: Array<Fluid> get() = arrayOf(NTechFluids.steam.source.get(), NTechFluids.steamHot.source.get(), NTechFluids.steamSuperHot.source.get(), NTechFluids.steamUltraHot.source.get())

    val waterTank = FluidInputTank(10_000) { it.fluid.isSame(Fluids.WATER) }.apply { forceFluid(FluidStack(Fluids.WATER, 0)) }
    val steamTank = FluidOutputTank(1_000_000) { validSteams.any { valid -> it.fluid.isSame(valid) } }.apply { forceFluid(FluidStack(NTechFluids.steam.source.get(), 0)) }

    override fun getTanks() = 2
    override fun getTankAccess(tank: Int): MutableFluidTank = when (tank) {
        0 -> waterTank
        1 -> steamTank
        else -> throw IndexOutOfBoundsException()
    }

    override fun getFluidInTank(tank: Int): FluidStack = when (tank) {
        0 -> waterTank.fluid
        1 -> steamTank.fluid
        else -> FluidStack.EMPTY
    }

    override fun getTankCapacity(tank: Int): Int = when (tank) {
        0 -> waterTank.capacity
        1 -> steamTank.capacity
        else -> 0
    }

    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean = when (tank) {
        0 -> waterTank.isFluidValid(stack)
        1 -> validSteams.any { stack.fluid.isSame(it) }
        else -> false
    }

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = waterTank.fill(resource, action)
    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = steamTank.drain(resource, action)
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = steamTank.drain(maxDrain, action)

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Water", waterTank.fluidAmount)
        tag.putInt("Steam", steamTank.fluidAmount)
        tag.putString("SteamType", ForgeRegistries.FLUIDS.getKey(steamTank.fluid.rawFluid)!!.toString())
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        waterTank.forceFluid(FluidStack(Fluids.WATER, tag.getInt("Water")))
        steamTank.forceFluid(FluidStack(ForgeRegistries.FLUIDS.getValue(ResourceLocation(tag.getString("SteamType"))) ?: NTechFluids.steam.source.get(), tag.getInt("Steam")))
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.BOILER
    override fun getConsoleData() = CompoundTag().apply {
        putInt("Water", waterTank.fluidAmount)
        putInt("MaxWater", waterTank.capacity)
        putInt("Steam", steamTank.fluidAmount)
        putInt("MaxSteam", steamTank.capacity)
        putString("SteamType", ForgeRegistries.FLUIDS.getKey(steamTank.fluid.rawFluid)!!.toString())
    }

    init {
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::steamTank, Direction.UP)
    }

    override fun onLoad() {
        super.onLoad()
        if (hasLevel() && !levelUnchecked.isClientSide) {
            // only run on server, otherwise the client will get a cached column height of 0
            registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, getWaterInputOffset(), this::waterTank, Direction.DOWN)
            refreshSteamConnectors()
        }
    }

    private fun refreshSteamConnectors() {
        (levelUnchecked.getBlockEntity(blockPos.offset(0, -getColumnHeight(), 0)) as? RBMKSteamConnectorBlockEntity)?.reviveCaps()
        (levelUnchecked.getBlockEntity(blockPos.offset(0, -getColumnHeight() - 1, 0)) as? RBMKSteamConnectorBlockEntity)?.reviveCaps()
    }

    private fun getWaterInputOffset() = BlockPos(0, -(getColumnHeight() - 1), 0)

    override val ioConfigurations: Table<BlockPos, Direction, List<IOConfiguration>> by lazy {
        if (getColumnHeight() == 1) HashBasedTable.create()
        else IODelegatedBlockEntity.fromTriples(blockPos, getHorizontalBlockRotation(),
            Triple(getWaterInputOffset(), Direction.DOWN, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION)))
        )
    }

    class WaterInputBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPort.MultiBlockPortBlockEntity(NTechBlockEntities.rbmkBoilerWaterInputBlockEntityType.get(), pos, state), TickingServerBlockEntity {
        override fun onLoad() {
            super.onLoad()
            if (hasLevel() && !level!!.isClientSide)
                core = blockPos.offset(0, blockState.getValue(RBMKColumnBlock.LEVEL), 0)
        }
    }
}

