package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.FractionatingColumnBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class FractionatingColumnMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: FractionatingColumnBlockEntity,
) : NTechContainerMenu<FractionatingColumnBlockEntity>(NTechMenus.fractionatingColumnMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        // Layout:
        // Input Bucket: 30, 20
        // Battery: 8, 53
        // Upgrades: ?
        
        // Slot 0: Input Item
        addSlot(SlotItemHandler(inv, 0, 30, 20)) 
        
        // Slot 1: ? Output drain slot?
        
        // Slot 2: Battery
        addSlot(SlotItemHandler(inv, 2, 20, 53))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 7, intArrayOf()) {
        2 check supportsEnergyCondition()
        3..6 check compatibleMachineUpgradeCondition(blockEntity)
        null
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            FractionatingColumnMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
