/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.api.menu.slot.OutputSlot
import dev.ntmr.nucleartech.content.NTechMenus
import dev.ntmr.nucleartech.content.block.entity.TurbineBlockEntity
import dev.ntmr.nucleartech.content.fluid.trait.CoolableFluidTrait
import dev.ntmr.nucleartech.content.item.FluidIdentifierItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.IItemHandler

class TurbineMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: TurbineBlockEntity
) : NTechContainerMenu<TurbineBlockEntity>(NTechMenus.turbineMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 8, 17)) // Fluid ID
        addSlot(OutputSlot(inv, 1, 8, 53))
        addSlot(SlotItemHandler(inv, 2, 44, 17)) // Input
        addSlot(OutputSlot(inv, 3, 44, 53))
        addSlot(SlotItemHandler(inv, 4, 152, 17)) // Output
        addSlot(OutputSlot(inv, 5, 152, 53))
        addSlot(SlotItemHandler(inv, 6, 98, 53))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84) // Battery
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 7, intArrayOf(1, 3, 5)) {
        0 check (itemIsInstanceCondition<FluidIdentifierItem>() and fluidTraitCondition<CoolableFluidTrait>(FluidIdentifierItem::getFluid) { it.trait.getEfficiency(it, CoolableFluidTrait.CoolingType.Turbine) > 0F })
        2 check canFillTankCondition(blockEntity.inputTank)
        4 check canDrainTankCondition(blockEntity.outputTank)
        6 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            TurbineMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
