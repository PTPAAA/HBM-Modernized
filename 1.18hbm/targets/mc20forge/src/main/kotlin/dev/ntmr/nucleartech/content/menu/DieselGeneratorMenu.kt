package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.OutputSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.DieselGeneratorBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class DieselGeneratorMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: DieselGeneratorBlockEntity,
) : NTechContainerMenu<DieselGeneratorBlockEntity>(NTechMenus.dieselGeneratorMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 44, 17)) // Fluid Input
        addSlot(OutputSlot(inv, 1, 44, 53)) // Fluid Output (Bucket)
        addSlot(SlotItemHandler(inv, 2, 116, 53)) // Battery Charge
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 3, intArrayOf(1)) {
        0 check hasFluidHandlerCondition()
        2 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            DieselGeneratorMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
