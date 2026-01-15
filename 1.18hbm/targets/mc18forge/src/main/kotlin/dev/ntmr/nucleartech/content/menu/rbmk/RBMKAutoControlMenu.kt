/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu.rbmk

import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAutoControlBlockEntity
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.addPlayerInventory
import dev.ntmr.nucleartech.content.menu.getBlockEntityForContainer
import dev.ntmr.nucleartech.content.menu.tryMoveInPlayerInventory
import dev.ntmr.nucleartech.content.menu.*
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RBMKAutoControlMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKAutoControlBlockEntity
) : NTechContainerMenu<RBMKAutoControlBlockEntity>(NTechMenus.rbmkAutoControlMenu.get(), windowID, playerInventory, blockEntity) {
    init {
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun clickMenuButton(player: Player, index: Int): Boolean {
        if (index in RBMKAutoControlBlockEntity.ControlFunction.values().indices) {
            blockEntity.function = RBMKAutoControlBlockEntity.ControlFunction.values()[index]
            blockEntity.markDirty()
            return true
        }
        return false
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (!tryMoveInPlayerInventory(index, 0, itemStack)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }


    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            RBMKAutoControlMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
