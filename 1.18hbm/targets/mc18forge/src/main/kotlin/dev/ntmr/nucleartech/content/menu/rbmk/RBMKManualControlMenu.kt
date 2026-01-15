/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu.rbmk

import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKManualControlBlockEntity
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.addPlayerInventory
import dev.ntmr.nucleartech.content.menu.getBlockEntityForContainer
import dev.ntmr.nucleartech.content.menu.tryMoveInPlayerInventory
import dev.ntmr.nucleartech.content.menu.*
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RBMKManualControlMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKManualControlBlockEntity
) : NTechContainerMenu<RBMKManualControlBlockEntity>(NTechMenus.rbmkManualControlMenu.get(), windowID, playerInventory, blockEntity) {
    init {
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun clickMenuButton(player: Player, index: Int): Boolean {
        if (index in 0..4) {
            blockEntity.targetLevel = 1.0 - index * 0.25
            blockEntity.markDirty()
            return true
        } else if (index in 5..9) {
            val newColor = RBMKManualControlBlockEntity.Color.values()[index - 5]
            if (newColor == blockEntity.color)
                blockEntity.color = null
            else
                blockEntity.color = newColor
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
            RBMKManualControlMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
