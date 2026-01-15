/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.ExperienceResultSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.ElectricFurnaceBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.IItemHandler

class ElectricFurnaceMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: ElectricFurnaceBlockEntity,
) : NTechContainerMenu<ElectricFurnaceBlockEntity>(NTechMenus.electricFurnaceMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)
    private val level = playerInventory.player.level()

    init {
        addSlot(SlotItemHandler(inv, 0, 56, 17))
        addSlot(SlotItemHandler(inv, 1, 56, 53))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 2, 116, 35))
        addSlot(SlotItemHandler(inv, 3, 147, 34))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 3, intArrayOf(2)) {
        0 check this@ElectricFurnaceMenu::canCook
        1 check supportsEnergyCondition()
        3 check compatibleMachineUpgradeCondition(blockEntity)
        null
    }

    private fun canCook(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeType.SMELTING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ElectricFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
