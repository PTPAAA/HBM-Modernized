package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.item.canTransferItem
import dev.ntmr.nucleartech.content.item.upgrades.MachineUpgradeItem
import dev.ntmr.nucleartech.content.item.upgrades.PowerSavingUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.SpeedUpgrade
import dev.ntmr.nucleartech.content.menu.ElectricFurnaceMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.BooleanDataSlot
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
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.SmeltingRecipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.capabilities.ForgeCapabilities
import java.util.*
import kotlin.math.ceil

class ElectricFurnaceBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(
    NTechBlockEntities.electricFurnaceBlockEntityType.get(), pos, state),
    TickingServerBlockEntity, RecipeHolder, ExperienceRecipeResultBlockEntity, SpeedUpgradeableMachine, PowerSavingUpgradeableMachine
{
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    // Slots: 0,1 inputs; 2,3 outputs; 4 battery; 5,6,7,8 upgrades
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(9, ItemStack.EMPTY)

    override val upgradeSlots = 5..8

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
        when (slot) {
            4 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent
            in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
            0, 1 -> true // Input slots
            else -> false
        }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
        // Reset cached recipes if inputs change? Not caching strictly here yet.
    }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)

    // Independent progress for two slots
    var progress1 = 0
    var progress2 = 0
    var maxProgress1 = 100
    var maxProgress2 = 100

    override fun createMenu(windowID: Int, inventory: Inventory) = ElectricFurnaceMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_ELECTRIC_FURNACE.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::progress1) { progress1 = it })
        menu.track(IntDataSlot.create(this::progress2) { progress2 = it })
        menu.track(IntDataSlot.create(this::maxProgress1) { maxProgress1 = it })
        menu.track(IntDataSlot.create(this::maxProgress2) { maxProgress2 = it })
        menu.track(IntDataSlot.create(this::speedUpgradeLevel, this::speedUpgradeLevel::set))
        menu.track(IntDataSlot.create(this::powerSavingUpgradeLevel, this::powerSavingUpgradeLevel::set))
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
    }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for electric furnace")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 50}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("-${effect.tier * 15}HE/t"), LangKeys.UPGRADE_INFO_DELAY.format("+${effect.tier * 10}%"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(maxPowerSavingUpgradeLevel))

    private val consumption get() = 50 + (speedUpgradeLevel * 50) - (powerSavingUpgradeLevel * 15)
    
    private fun calculateMaxProgress() = 100 - (speedUpgradeLevel * 25) + (powerSavingUpgradeLevel * 10)

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val energyItemSlot = mainInventory[4]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)

        // Apply upgrades from slots 5-8
        MachineUpgradeItem.applyUpgrades(this, upgradeSlots.map { mainInventory[it] })
        
        val currentMaxProgress = calculateMaxProgress().coerceAtLeast(1)
        maxProgress1 = currentMaxProgress
        maxProgress2 = currentMaxProgress

        var isProcessing = false
        
        // Slot 1 Logic (Index 0 -> 2)
        if (canProcess(0, 2)) {
            if (energy >= consumption) {
                energy -= consumption
                progress1++
                isProcessing = true
                if (progress1 >= maxProgress1) {
                    smelt(0, 2)
                    progress1 = 0
                }
            }
        } else {
            progress1 = 0
        }

        // Slot 2 Logic (Index 1 -> 3)
        if (canProcess(1, 3)) {
             // Share consumption? Or double consumption? 
             // Usually dual-smelters consume Energy PER OPERATION.
             if (energy >= consumption) {
                energy -= consumption
                progress2++
                isProcessing = true
                if (progress2 >= maxProgress2) {
                    smelt(1, 3)
                    progress2 = 0
                }
            }
        } else {
            progress2 = 0
        }

        if (state.getValue(BlockStateProperties.LIT) != isProcessing) {
             level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, isProcessing))
        }
    }

    private fun canProcess(inputSlot: Int, outputSlot: Int): Boolean {
        val input = mainInventory[inputSlot]
        if (input.isEmpty) return false
        val recipe = level!!.recipeManager.getRecipeFor(RecipeType.SMELTING, SimpleInventory(input), level!!).orElse(null) ?: return false
        
        val result = recipe.getResultItem(level!!.registryAccess())
        if (result.isEmpty) return false
        
        val output = mainInventory[outputSlot]
        if (output.isEmpty) return true
        if (!ItemStack.isSameItemSameTags(output, result)) return false
        return output.count + result.count <= output.maxStackSize
    }

    private fun smelt(inputSlot: Int, outputSlot: Int) {
        val input = mainInventory[inputSlot]
        val recipe = level!!.recipeManager.getRecipeFor(RecipeType.SMELTING, SimpleInventory(input), level!!).orElse(null) ?: return
        val result = recipe.getResultItem(level!!.registryAccess())
        val output = mainInventory[outputSlot]

        if (output.isEmpty) {
            mainInventory[outputSlot] = result.copy()
        } else {
            output.grow(result.count)
        }
        
        input.shrink(1)
        setRecipeUsed(recipe)
    }

    // Helper for recipe checking
    class SimpleInventory(val stack: ItemStack) : net.minecraft.world.Container {
        override fun clearContent() {}
        override fun getContainerSize() = 1
        override fun isEmpty() = stack.isEmpty
        override fun getItem(slot: Int) = stack
        override fun removeItem(slot: Int, amount: Int): ItemStack = ItemStack.EMPTY
        override fun removeItemNoUpdate(slot: Int): ItemStack = ItemStack.EMPTY
        override fun setItem(slot: Int, stack: ItemStack) {}
        override fun setChanged() {}
        override fun stillValid(player: Player) = true
    }

    // RecipeHolder Implementations
    private val recipesUsed = mutableMapOf<Constants.RecipeKey, Int>() // Simplification map
    // Using Object2IntOpenHashMap<ResourceLocation> like original
    private val recipesUsedMap = it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<net.minecraft.resources.ResourceLocation>()

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe != null) {
            recipesUsedMap.addTo(recipe.id, 1)
        }
    }

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun awardUsedRecipes(player: Player, items: MutableList<ItemStack>) {} // Vanilla logic handles this mostly?

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsedMap.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? AbstractCookingRecipe)?.experience?.times(amount)
        }.sum()

    // TODO: implement getRecipesToAward properly if needed for advancements?
    override fun getRecipesToAward(player: Player): List<Recipe<*>> = emptyList()

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.putInt("Progress1", progress1)
        tag.putInt("Progress2", progress2)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        progress1 = tag.getInt("Progress1")
        progress2 = tag.getInt("Progress2")
    }

    init {
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 400_000
        const val ENERGY_TRANSFER_RATE = MAX_ENERGY / 100
    }
}
