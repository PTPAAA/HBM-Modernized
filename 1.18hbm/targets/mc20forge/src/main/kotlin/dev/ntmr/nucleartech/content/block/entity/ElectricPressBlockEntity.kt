package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.item.canTransferItem
import dev.ntmr.nucleartech.content.item.upgrades.MachineUpgradeItem
import dev.ntmr.nucleartech.content.item.upgrades.PowerSavingUpgrade
import dev.ntmr.nucleartech.content.item.upgrades.SpeedUpgrade
import dev.ntmr.nucleartech.content.menu.ElectricPressMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.content.recipe.PressingRecipe
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.ForgeCapabilities
import kotlin.jvm.optionals.getOrNull
import kotlin.math.abs

class ElectricPressBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<PressingRecipe>(
    NTechBlockEntities.electricPressBlockEntityType.get(), pos, state), 
    RecipeHolder, ExperienceRecipeResultBlockEntity, SpeedUpgradeableMachine, PowerSavingUpgradeableMachine {

    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    // Slots: 0 Input, 1 Stamp, 2 Output, 3 Battery, 4-7 Upgrades
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(8, ItemStack.EMPTY)
    
    override val upgradeSlots = 4..7

    override fun isItemValid(slot: Int, stack: ItemStack) = when (slot) {
        1 -> stack.`is`(NTechTags.Items.STAMPS)
        3 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        else -> true
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
    }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)

    override fun createMenu(windowID: Int, inventory: Inventory) = ElectricPressMenu(windowID, inventory, this)
    // TODO: Add LangKey
    override val defaultName = Component.translatable("container.nucleartech.electric_press")

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::speedUpgradeLevel, this::speedUpgradeLevel::set))
        menu.track(IntDataSlot.create(this::powerSavingUpgradeLevel, this::powerSavingUpgradeLevel::set))
    }
    
    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for electric press")
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
    override val maxProgress get() = 200 - (speedUpgradeLevel * 50) + (powerSavingUpgradeLevel * 20)
    
    override val progressRegression = 5 // Fast regression if no power?
    override val progressSpeed = 1

    override fun findPossibleRecipe() = getLevelUnchecked().recipeManager.getRecipeFor(NTechRecipeTypes.PRESSING, this, getLevelUnchecked()).getOrNull()

    override fun matchesRecipe(recipe: PressingRecipe) = 
        !mainInventory[0].isEmpty && !mainInventory[1].isEmpty &&
        recipe.matches(this, getLevelUnchecked()) && 
        canTransferItem(recipe.getResultItem(getLevelUnchecked().registryAccess()), mainInventory[2], this)

    @Suppress("UsePropertyAccessSyntax")
    override fun processRecipe(recipe: PressingRecipe) {
        val recipeResult = recipe.getResultItem(level!!.registryAccess())
        val resultStack = mainInventory[2]
        
        if (resultStack.isEmpty) mainInventory[2] = recipeResult.copy()
        else if (ItemStack.isSameItem(resultStack, recipeResult)) resultStack.grow(recipeResult.count)
        
        setRecipeUsed(recipe)
        mainInventory[0].shrink(1)
        
        // Damage stamp
        val stamp = mainInventory[1]
        if (stamp.isDamageableItem) {
             if (stamp.hurt(1, level!!.random, null)) {
                if (stamp.damageValue >= stamp.maxDamage) stamp.shrink(1)
            }
        }
        
        getLevelUnchecked().playSound(null, blockPos, NTechSounds.pressOperate.get(), SoundSource.BLOCKS, 1.5F, 1F)
    }

    override fun checkCanProgress() = energy > MIN_ENERGY_THRESHOLD && super.checkCanProgress()

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, upgradeSlots.map { mainInventory[it] })
        super.tickProgress()
        energy -= consumption
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val energyItemSlot = mainInventory[3]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)
        
        super.serverTick(level, pos, state)
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }
    override fun getRecipeUsed(): Recipe<*>? = null
    override fun clearUsedRecipes() = recipesUsed.clear()
    override fun awardUsedRecipes(player: Player, items: MutableList<ItemStack>) {}

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? PressingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level().recipeManager.byKey(it).orElse(null) }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
    }

    init {
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 400_000
        const val ENERGY_TRANSFER_RATE = MAX_ENERGY / 100
        const val MIN_ENERGY_THRESHOLD = 500
    }
}
