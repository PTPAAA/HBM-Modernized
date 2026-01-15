/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.ExperienceResultSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.block.entity.BlastFurnaceBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.IItemHandler

class BlastFurnaceMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: BlastFurnaceBlockEntity,
) : NTechContainerMenu<BlastFurnaceBlockEntity>(NTechMenus.blastFurnaceMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)
    private val level = playerInventory.player.level()

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        addSlot(SlotItemHandler(inv, 1, 80, 54))
        addSlot(SlotItemHandler(inv, 2, 8, 36))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 3, 134, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 4, intArrayOf(3)) {
        0..1 check this@BlastFurnaceMenu::canBlast
        2 check AbstractFurnaceBlockEntity::isFuel
        null
    }

    private fun canBlast(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(NTechRecipeTypes.BLASTING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            BlastFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
