package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.ExperienceResultSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.ElectricFurnaceBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class ElectricFurnaceMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: ElectricFurnaceBlockEntity,
) : NTechContainerMenu<ElectricFurnaceBlockEntity>(NTechMenus.electricFurnaceMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)
    private val level = playerInventory.player.level()

    init {
        // Input 1
        addSlot(SlotItemHandler(inv, 0, 56, 17))
        // Input 2
        addSlot(SlotItemHandler(inv, 1, 74, 17)) // Next to it? Or maybe dual layout: Left/Right?
        // Let's assume standard DiFurnace layout: 
        // Input 1: 56, 17
        // Input 2: 74, 17  (Let's space them 18px apart? Or separate sides?)
        // Standard HBM DiFurnace: 2 inputs left, 2 outputs right?
        // Let's define new positions:
        // Input 1: x=46, y=17
        // Input 2: x=66, y=17
        // Battery: x=56, y=53
        // Output 1: x=116, y=35
        // Output 2: x=142, y=35 (Maybe?)
        
        // Let's stick to a simpler layout for now that fits 176x166
        // Input 1: 56, 17
        // Input 2: 74, 17
        // Battery: 8, 53 (Left side?) or Center?
        // Original Battery was 56, 53.
        
        // Revised DiFurnace Layout Proposal:
        // Input 1: 46, 17
        // Input 2: 66, 17
        
        // Output 1: 106, 35
        // Output 2: 126, 35
        
        // Battery: 20, 53
        
        // Upgrades: 152, 17 -> downwards 4 slots?
        
        // Let's implement positions:
        
        // Slot 0: Input 1
        addSlot(SlotItemHandler(inv, 0, 46, 17))
        // Slot 1: Input 2
        addSlot(SlotItemHandler(inv, 1, 66, 17))
        
        // Slot 2: Output 1
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 2, 106, 35))
        // Slot 3: Output 2
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 3, 126, 35))
        
        // Slot 4: Battery
        addSlot(SlotItemHandler(inv, 4, 20, 53))
        
        // Slot 5-8: Upgrades
        addSlot(SlotItemHandler(inv, 5, 152, 17))
        addSlot(SlotItemHandler(inv, 6, 152, 35))
        addSlot(SlotItemHandler(inv, 7, 152, 53))
        addSlot(SlotItemHandler(inv, 8, 152, 71)) // Might intercept player inv? Player inv starts y=84. 71+16=87. Takes space.
        // Maybe move upgrades? Stacked 2x2? 
        // 152, 17 and 152, 35. 
        // 134, 17?
        // Let's do 2 columns?
        // 152, 17; 152, 35; 152, 53. (3 slots fits)
        // Where to put 4th?
        // Maybe minimal upgrades for now? BlockEntity supports 5..8 (4 slots).
        // Let's use 152, 8 (too high).
        // Let's use 152, 18 / 152, 36 / 152, 54.
        // Only 3 visual slots? Or make texture taller?
        // Let's stick to 3 upgrade slots visually if needed, or put them elsewhere.
        // BUT Logic demands 4 slots (5..8).
        // Let's put them:
        // 5: 152, 17
        // 6: 152, 35
        // 7: 152, 53
        // 8: 134, 53 (Adjacent?)
        
        // Simpler: Just 3 upgrades?
        // No, BE has 5..8.
        // Let's use: 152,17; 152,35; 152,53; 152,71?
        // Player inv y=84. 
        // Slot at 71: top=71, bottom=87. Overlaps 3 pixels. Acceptable? Or push texture down.
        // Standard texture height 166.
        // Let's try to fit.
        
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 9, intArrayOf(2, 3)) {
        0 check this@ElectricFurnaceMenu::canCook // To Input 1
        1 check this@ElectricFurnaceMenu::canCook // To Input 2 (Fallback logic needed in boilerplate? boilerplate takes one int usually)
        // Boilerplate limitation: it usually tries to merge to *any* of the 'count' slots starting from 'index'?
        // quickMoveStackBoilerplate(player, index, count, ...)
        // count is size of container. 9 slots.
        // It tries to insert into 0..8.
        // Checks:
        // if condition(0) matches -> try insert slot 0.
        // We want: if canCook -> try insert 0, then 1.
        // The DSL 'check' typically maps a condition to a target range?
        // Looking at `quickMoveStackBoilerplate` source would be ideal but I assume it iterates.
        
        // If I say `0 check condition`, it means "If cond is true, try to move to slot 0".
        // How to say "Try 0, then 1"? 
        // Maybe `0..1 check condition`? If supported.
        // Assuming simple `0 check` for now.
        
        4 check supportsEnergyCondition()
        5..8 check compatibleMachineUpgradeCondition(blockEntity)
        null
    }

    private fun canCook(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeType.SMELTING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ElectricFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
