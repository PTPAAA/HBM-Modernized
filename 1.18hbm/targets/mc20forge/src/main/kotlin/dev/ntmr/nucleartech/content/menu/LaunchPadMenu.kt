/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.item.TargetDesignator
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.LaunchPadBlockEntity
import dev.ntmr.nucleartech.content.item.MissileItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.IItemHandler

class LaunchPadMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: LaunchPadBlockEntity,
) : NTechContainerMenu<LaunchPadBlockEntity>(NTechMenus.launchPadMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 26, 17))
        addSlot(SlotItemHandler(inv, 1, 80, 17))
        addSlot(SlotItemHandler(inv, 2, 134, 17))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 3, intArrayOf()) {
        0 check itemIsInstanceCondition<MissileItem<*>>()
        1 check itemIsInstanceCondition<TargetDesignator>()
        2 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            LaunchPadMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
