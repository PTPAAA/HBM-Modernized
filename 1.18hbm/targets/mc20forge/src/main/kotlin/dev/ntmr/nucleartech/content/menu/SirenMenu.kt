/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.SlotItemHandlerUnglitched
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.SirenBlockEntity
import dev.ntmr.nucleartech.content.item.SirenTrackItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.IItemHandler

class SirenMenu(windowId: Int, playerInventory: Inventory, blockEntity: SirenBlockEntity) : NTechContainerMenu<SirenBlockEntity>(NTechMenus.sirenMenu.get(), windowId, playerInventory, blockEntity) {
    init {
        val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

        addSlot(SlotItemHandlerUnglitched(inv, 0, 8, 35))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 1, intArrayOf()) {
        0 check itemIsInstanceCondition<SirenTrackItem>()
        null
    }

    override fun removed(player: Player) {
        super.removed(player)
        blockEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            SirenMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
