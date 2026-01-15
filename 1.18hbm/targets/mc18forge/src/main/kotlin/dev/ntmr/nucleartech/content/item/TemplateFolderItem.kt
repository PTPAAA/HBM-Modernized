/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.client.screen.UseTemplateFolderScreen
import dev.ntmr.nucleartech.content.CreativeTabs
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class TemplateFolderItem : TooltipItem(Properties().stacksTo(1).tab(CreativeTabs.Templates.tab)) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = player.getItemInHand(hand)

        if (world.isClientSide) {
            Minecraft.getInstance().setScreen(UseTemplateFolderScreen())
        }

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide)
    }
}
