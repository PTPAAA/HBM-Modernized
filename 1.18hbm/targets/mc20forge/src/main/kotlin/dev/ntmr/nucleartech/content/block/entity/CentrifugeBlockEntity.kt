package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.item.insertAllItemsStacked
import dev.ntmr.nucleartech.content.item.upgrades.MachineUpgradeItem
import dev.ntmr.nucleartech.content.item.upgrades.OverdriveUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.PowerSavingUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.SpeedUpgrade
import dev.ntmr.nucleartech.content.menu.CentrifugeMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.content.recipe.CentrifugeRecipe
import dev.ntmr.nucleartech.content.recipe.containerSatisfiesRequirements
import dev.ntmr.nucleartech.extensions.subView
import dev.ntmr.nucleartech.system.capability.item.AccessLimitedInputItemHandler
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.capabilities.ForgeCapabilities
import kotlin.jvm.optionals.getOrNull
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank
import net.minecraft.core.Direction

class CentrifugeBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<CentrifugeRecipe>(
    NTechBlockEntities.centrifugeBlockEntityType.get(), pos, state),
    SpeedUpgradeableMachine, PowerSavingUpgradeableMachine, OverdriveUpgradeableMachine
{
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(8, ItemStack.EMPTY)

    override val upgradeSlots = 1..2

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        else -> true
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
    }

    override val soundLoopEvent get() = NTechSounds.centrifugeOperate.get()
    override val defaultName = LangKeys.CONTAINER_CENTRIFUGE.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = CentrifugeMenu(windowID, inventory, this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
    }

    override fun getRenderBoundingBox() = AABB(blockPos, blockPos.offset(1, 4, 1))

    val energyStorage = EnergyStorageExposed(MAX_ENERGY)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)
    }

    override fun checkCanProgress() = super.checkCanProgress() && energy >= consumption

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override val maxOverdriveUpgradeLevel = 3
    override var overdriveUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
        super<OverdriveUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier + 1}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 200}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("÷${effect.tier + 1}"))
        is OverdriveUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier * 5}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 10_000}HE/t"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(powerSavingUpgradeLevel), OverdriveUpgrade(maxOverdriveUpgradeLevel))

    override val maxProgress = 200

    override val progressSpeed: Int get() = (1 + speedUpgradeLevel) * (overdriveUpgradeLevel * 5).coerceAtLeast(1)
    private val consumption get() = (200 + speedUpgradeLevel * 200 + overdriveUpgradeLevel * 10_000) / (1 + powerSavingUpgradeLevel)

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, mainInventory.subList(1, 3))
        super.tickProgress()
        energy -= consumption + 1
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun findPossibleRecipe() = levelUnchecked.recipeManager.getRecipeFor(NTechRecipeTypes.CENTRIFUGE, this, levelUnchecked).getOrNull()

    val inputTank = FluidTank(10000)
    val outputTank1 = FluidTank(10000)
    val outputTank2 = FluidTank(10000)

    override fun matchesRecipe(recipe: CentrifugeRecipe): Boolean {
        if (!recipe.inputFluid.isEmpty) {
            if (inputTank.fluidAmount < recipe.inputFluid.amount || !inputTank.fluid.isFluidEqual(recipe.inputFluid)) return false
        }
        if (!recipe.ingredient.isEmpty) {
            if (!recipe.matches(this, levelUnchecked)) return false
        }
        
        // Output Checks
        if (recipe.outputFluids.isNotEmpty()) {
            val fluid1 = recipe.outputFluids[0]
            if (outputTank1.fill(fluid1, IFluidHandler.FluidAction.SIMULATE) != fluid1.amount) return false
            if (recipe.outputFluids.size > 1) {
                val fluid2 = recipe.outputFluids[1]
                if (outputTank2.fill(fluid2, IFluidHandler.FluidAction.SIMULATE) != fluid2.amount) return false
            }
        }
        
        return insertAllItemsStacked(AccessLimitedInputItemHandler(this, 4..7), recipe.resultsList, true).isEmpty()
    }

    override fun processRecipe(recipe: CentrifugeRecipe) {
        if (!recipe.ingredient.isEmpty) {
            listOf(recipe.ingredient).containerSatisfiesRequirements(subView(3, 4), true)
        }
        if (!recipe.inputFluid.isEmpty) {
            inputTank.drain(recipe.inputFluid, IFluidHandler.FluidAction.EXECUTE)
        }
        
        if (recipe.outputFluids.isNotEmpty()) {
            outputTank1.fill(recipe.outputFluids[0], IFluidHandler.FluidAction.EXECUTE)
            if (recipe.outputFluids.size > 1) {
                outputTank2.fill(recipe.outputFluids[1], IFluidHandler.FluidAction.EXECUTE)
            }
        }

        insertAllItemsStacked(AccessLimitedInputItemHandler(this, 4..7), recipe.resultsList, false)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.put("InputTank", inputTank.writeToNBT(CompoundTag()))
        tag.put("OutputTank1", outputTank1.writeToNBT(CompoundTag()))
        tag.put("OutputTank2", outputTank2.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        inputTank.readFromNBT(tag.getCompound("InputTank"))
        outputTank1.readFromNBT(tag.getCompound("OutputTank1"))
        outputTank2.readFromNBT(tag.getCompound("OutputTank2"))
    }

    override fun registerCapabilityHandlers() {
        super.registerCapabilityHandlers()
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::inputTank, Direction.UP)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::inputTank, Direction.NORTH)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::inputTank, Direction.SOUTH)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::inputTank, Direction.WEST)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::inputTank, Direction.EAST)
        // Outputs usually bottom or sides? Let's just expose input everywhere for now or define IO more strictly.
        // For simplicity, let's say Input is Side/Top, Output is Bottom? 
        // Or using Composite Fluid Handler?
        // Let's just replicate the pattern.
    }

    companion object {
        const val MAX_ENERGY = 100_000
    }
}
