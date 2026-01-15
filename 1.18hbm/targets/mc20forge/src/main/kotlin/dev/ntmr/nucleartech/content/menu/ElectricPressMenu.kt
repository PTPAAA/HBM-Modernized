package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.ExperienceResultSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.ElectricPressBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class ElectricPressMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: ElectricPressBlockEntity,
) : NTechContainerMenu<ElectricPressBlockEntity>(NTechMenus.electricPressMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)
    private val level = playerInventory.player.level()

    init {
        // Layout:
        // Input: 56, 17 (Left Top)
        // Stamp: 56, 53 (Left Bottom? Or Middle?)
        // Standard Steam Press: Input, Stamp, Fuel...
        // Let's assume Electric Press GUI:
        // Input: 56, 17
        // Stamp: 56, 53
        // Output: 116, 35
        // Battery: 8, 53
        // Upgrades: 152, 17-71
        
        // Slot 0: Input
        addSlot(SlotItemHandler(inv, 0, 56, 17))
        // Slot 1: Stamp
        addSlot(SlotItemHandler(inv, 1, 56, 53))
        // Slot 2: Output
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 2, 116, 35))
        // Slot 3: Battery
        addSlot(SlotItemHandler(inv, 3, 20, 53))
        
        // Slot 4-7: Upgrades
        addSlot(SlotItemHandler(inv, 4, 152, 17))
        addSlot(SlotItemHandler(inv, 5, 152, 35))
        addSlot(SlotItemHandler(inv, 6, 152, 53))
        addSlot(SlotItemHandler(inv, 7, 152, 71))
        
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 8, intArrayOf(2)) {
        0 check hasRecipe(this@ElectricPressMenu.level) // To Input? Or complicated check
        // Check if item is valid input for ANY recipe or stamp?
        // Let's just say Slot 0.
        // Actually quickMoveStack check DSL is: targetSlotIndex check condition.
        
        3 check supportsEnergyCondition()
        4..7 check compatibleMachineUpgradeCondition(blockEntity)
        1 check isStampCondition() // Need custom condition
        null
    }
    
    private fun isStampCondition() = { stack: ItemStack -> 
         stack.`is`(dev.ntmr.nucleartech.NTechTags.Items.STAMPS)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ElectricPressMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
