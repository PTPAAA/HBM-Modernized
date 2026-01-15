package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.BatteryBoxBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class BatteryBoxMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: BatteryBoxBlockEntity,
) : NTechContainerMenu<BatteryBoxBlockEntity>(NTechMenus.batteryBoxMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        // Slot 0: Discharge (Left)
        addSlot(SlotItemHandler(inv, 0, 56, 35))
        // Slot 1: Charge (Right)
        addSlot(SlotItemHandler(inv, 1, 116, 35))
        
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 2, intArrayOf()) {
        0 check supportsEnergyCondition() // To Discharge slot
        1 check supportsEnergyCondition() // To Charge slot (Ambiguous? Let's default to 1 if 0 is full? or just check if it can extract?)
        // Ideally: if item has energy > 0 -> slot 0. If energy < max -> slot 1.
        // For simplicity, let's just say supportsEnergyCondition puts it in first valid.
        null
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            BatteryBoxMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
