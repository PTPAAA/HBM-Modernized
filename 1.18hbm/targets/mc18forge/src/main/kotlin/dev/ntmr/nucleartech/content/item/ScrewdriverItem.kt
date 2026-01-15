/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.api.item.ScrewdriverInteractable
import dev.ntmr.nucleartech.content.util.autoTooltip
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class ScrewdriverItem(properties: Properties) : TooltipItem(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL
        val hand = context.hand
        val block = context.level.getBlockState(context.clickedPos).block
        if (block is ScrewdriverInteractable) {
            val result = block.onScrew(context)
            if (result.shouldAwardStats()) {
                context.itemInHand.hurtAndBreak(1, player) {
                    it.broadcastBreakEvent(hand)
                }
            }
            return result
        }
        return InteractionResult.PASS
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        autoTooltip(stack, tooltip, true)
    }
}
