/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu.rbmk

import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKRodBlockEntity
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.addPlayerInventory
import dev.ntmr.nucleartech.content.menu.getBlockEntityForContainer
import dev.ntmr.nucleartech.content.menu.tryMoveInPlayerInventory
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.IItemHandler

class RBMKRodMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKRodBlockEntity
) : NTechContainerMenu<RBMKRodBlockEntity>(NTechMenus.rbmkRodMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 45))
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 0) {
                if (!moveItemStackTo(itemStack, 1, slots.size, true)) return ItemStack.EMPTY
            } else {
                if (!blockEntity.isItemValid(0, itemStack) || !moveItemStackTo(itemStack, 0, 1, false))
                    if (!tryMoveInPlayerInventory(index, 1, itemStack)) return ItemStack.EMPTY
            }

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            RBMKRodMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
