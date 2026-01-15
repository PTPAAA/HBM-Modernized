package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.fluid.*
import dev.ntmr.nucleartech.content.item.ChemPlantTemplateItem
import dev.ntmr.nucleartech.content.item.insertAllItemsStacked
import dev.ntmr.nucleartech.content.item.upgrades.MachineUpgradeItem
import dev.ntmr.nucleartech.content.item.upgrades.OverdriveUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.PowerSavingUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.SpeedUpgrade
import dev.ntmr.nucleartech.content.menu.ChemPlantMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.BooleanDataSlot
import dev.ntmr.nucleartech.content.menu.slots.data.FluidStackDataSlot
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.content.recipe.ChemRecipe
import dev.ntmr.nucleartech.content.recipe.containerSatisfiesRequirements
import dev.ntmr.nucleartech.extensions.acceptFluids
import dev.ntmr.nucleartech.extensions.subView
import dev.ntmr.nucleartech.extensions.subViewWithFluids
import dev.ntmr.nucleartech.extensions.writeToNBTRaw
import dev.ntmr.nucleartech.math.component1
import dev.ntmr.nucleartech.math.component2
import dev.ntmr.nucleartech.math.component3
import dev.ntmr.nucleartech.math.rotate
import dev.ntmr.nucleartech.system.capability.item.AccessLimitedInputItemHandler
import dev.ntmr.nucleartech.system.capability.item.AccessLimitedOutputItemHandler
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.CapabilityItemHandler

class ChemPlantBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<ChemRecipe>(NTechBlockEntities.chemPlantBlockEntityType.get(), pos, state),
    ContainerFluidHandler, ForceableFluidHandler, SpeedUpgradeableMachine, PowerSavingUpgradeableMachine, OverdriveUpgradeableMachine, IODelegatedBlockEntity
{
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(21, ItemStack.EMPTY)

    override val upgradeSlots = 1..3

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        4 -> stack.item is ChemPlantTemplateItem
        in 5..8 -> true
        9, 10 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        11, 12 -> true
        in 13..16 -> true
        17, 18 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        19, 20 -> true
        else -> false
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
        if (!isClientSide() && slot == 4) {
            checkCanProgress()
            setupTanks()
        }
    }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    val inputTank1 = FluidInputTank(24_000)
    val inputTank2 = FluidInputTank(24_000)
    val outputTank1 = FluidOutputTank(24_000)
    val outputTank2 = FluidOutputTank(24_000)

    private val tanks = arrayOf(inputTank1, inputTank2, outputTank1, outputTank2)

    override fun getTanks() = 4
    override fun getTankAccess(tank: Int): MutableFluidTank = tanks[tank]

    private fun setupTanks() {
        val level = level ?: return
        val recipe = recipe ?: ChemPlantTemplateItem.getRecipeFromStack(mainInventory[4], level.recipeManager) ?: return
        if (inputTank1.isEmpty) inputTank1.fluid = FluidStack(recipe.inputFluid1.fluid, 0)
        if (inputTank2.isEmpty) inputTank2.fluid = FluidStack(recipe.inputFluid2.fluid, 0)
        if (outputTank1.isEmpty) outputTank1.fluid = FluidStack(recipe.outputFluid1.fluid, 0)
        if (outputTank2.isEmpty) outputTank2.fluid = FluidStack(recipe.outputFluid2.fluid, 0)
        sendContinuousUpdatePacket()
    }

    override fun createMenu(windowID: Int, inventory: Inventory) = ChemPlantMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_CHEM_PLANT.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::maxProgress) { maxProgress = it })
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })

        val isClient = isClientSide()
        menu.track(FluidStackDataSlot.create(inputTank1, isClient))
        menu.track(FluidStackDataSlot.create(inputTank2, isClient))
        menu.track(FluidStackDataSlot.create(outputTank1, isClient))
        menu.track(FluidStackDataSlot.create(outputTank2, isClient))
    }

    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 3, 3))

    var renderTick: Int = 0
        private set

    override val soundLoopEvent = NTechSounds.chemPlantOperate.get()

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.clientTick(level, pos, state)

        if (canProgress && !isRemoved) {
            renderTick++
            if (renderTick >= 360) renderTick = 0
        } else renderTick = 0

        if (canProgress && !isRemoved && renderTick % 3 == 0) {
            val particleOffset = Vec3(1.125, 3.0, 1.125).rotate(getHorizontalBlockRotation())
            val (x, y, z) = Vec3.atBottomCenterOf(blockPos).add(particleOffset)
            level.addParticle(ParticleTypes.CLOUD, x, y, z, 0.0, 0.1, 0.0)
        }
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)

        if (!mainInventory[17].isEmpty) {
            val fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[17], inputTank1.asForcingHandler(), AccessLimitedInputItemHandler(this, 19), inputTank1.space, true)
            if (fluidTransferResult.success) mainInventory[17] = fluidTransferResult.result
        }

        if (!mainInventory[18].isEmpty) {
            val fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[18], inputTank2.asForcingHandler(), AccessLimitedInputItemHandler(this, 20), inputTank2.space, true)
            if (fluidTransferResult.success) mainInventory[18] = fluidTransferResult.result
        }

        if (!mainInventory[9].isEmpty) {
            val fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[9], outputTank1.asForcingHandler(), AccessLimitedInputItemHandler(this, 11), outputTank1.fluidAmount, true)
            if (fluidTransferResult.success) mainInventory[9] = fluidTransferResult.result
        }

        if (!mainInventory[10].isEmpty) {
            val fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[10], outputTank2.asForcingHandler(), AccessLimitedInputItemHandler(this, 12), outputTank2.fluidAmount, true)
            if (fluidTransferResult.success) mainInventory[10] = fluidTransferResult.result
        }

        sendContinuousUpdatePacket() // TODO make this happen only when fluids change and not all the time
    }

    override fun checkCanProgress() = energy >= consumption && super.checkCanProgress()

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override val maxOverdriveUpgradeLevel = 9
    override var overdriveUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
        super<OverdriveUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 300}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("-${effect.tier * 30}HE/t"), LangKeys.UPGRADE_INFO_DELAY.format("+${effect.tier * 5}%"))
        is OverdriveUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier + 1}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("×${effect.tier + 1}"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(maxPowerSavingUpgradeLevel), OverdriveUpgrade(maxOverdriveUpgradeLevel))

    private val consumption get() = (100 + (speedUpgradeLevel * 300) - (powerSavingUpgradeLevel * 30)) * (overdriveUpgradeLevel + 1)
    private val speed get() = ((100 - (speedUpgradeLevel  * 25) + (powerSavingUpgradeLevel * 5)) / (overdriveUpgradeLevel + 1)).coerceAtLeast(1)

    override var maxProgress = 100
        private set

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, mainInventory.subList(1, 4))
        super.tickProgress()
        val recipe = recipe
        if (recipe != null)
            maxProgress = recipe.duration * speed / 100
        energy -= consumption
    }

    override fun findPossibleRecipe() = ChemPlantTemplateItem.getRecipeFromStack(mainInventory[4], getLevelUnchecked().recipeManager)

    // this method is quite computationally expensive
    override fun matchesRecipe(recipe: ChemRecipe) =
        recipe.matches(subViewWithFluids(13..16, 0..1), getLevelUnchecked())
            && insertAllItemsStacked(AccessLimitedInputItemHandler(this, 5..8), recipe.resultsList, true).isEmpty()
            && SimpleFluidHandler(outputTank1, outputTank2).acceptFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), true).isEmpty()

    override fun processRecipe(recipe: ChemRecipe) {
        recipe.ingredientsList.containerSatisfiesRequirements(subView(13..16), true)
        val handler = SimpleForceableFluidHandler(inputTank1, inputTank2)
        handler.forceDrain(recipe.inputFluid1, IFluidHandler.FluidAction.EXECUTE)
        handler.forceDrain(recipe.inputFluid2, IFluidHandler.FluidAction.EXECUTE)
        insertAllItemsStacked(AccessLimitedInputItemHandler(this, 5..8), recipe.resultsList, false)
        SimpleForceableFluidHandler(outputTank1, outputTank2).forceFillFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), false)
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        put("Tank1", inputTank1.writeToNBTRaw(CompoundTag()))
        put("Tank2", inputTank2.writeToNBTRaw(CompoundTag()))
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        inputTank1.readFromNBT(tag.getCompound("Tank1"))
        inputTank2.readFromNBT(tag.getCompound("Tank2"))
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.putInt("MaxProgress", maxProgress)

        val tanks = CompoundTag()
        tanks.put("InputTank1", inputTank1.writeToNBT(CompoundTag()))
        tanks.put("InputTank2", inputTank2.writeToNBT(CompoundTag()))
        tanks.put("OutputTank1", outputTank1.writeToNBT(CompoundTag()))
        tanks.put("OutputTank2", outputTank2.writeToNBT(CompoundTag()))
        tag.put("Tanks", tanks)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        maxProgress = tag.getInt("MaxProgress")

        val tanks = tag.getCompound("Tanks")
        inputTank1.readFromNBT(tanks.getCompound("InputTank1"))
        inputTank2.readFromNBT(tanks.getCompound("InputTank2"))
        outputTank1.readFromNBT(tanks.getCompound("OutputTank1"))
        outputTank2.readFromNBT(tanks.getCompound("OutputTank2"))
        setupTanks()
    }


    private val inputInventory = AccessLimitedInputItemHandler(this, 13..16)
    private val outputInventory = AccessLimitedOutputItemHandler(this, 5..8)

    init {
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::inputInventory, Direction.WEST)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::outputInventory, Direction.EAST)
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, { this })
    }

    override val ioConfigurations = IODelegatedBlockEntity.fromTriples(blockPos, getHorizontalBlockRotation(),
        Triple(BlockPos(-1, 0, 1), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ITEM_ACTION))),
        Triple(BlockPos(2, 0, 0), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_ITEM_ACTION))),
        Triple(BlockPos(0, 0, -1), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(1, 0, -1), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(0, 0, 2), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(1, 0, 2), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
    )

    companion object {
        const val MAX_ENERGY = 100_000
    }
}
