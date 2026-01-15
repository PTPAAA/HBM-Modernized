/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.system.world.ChunkPollution
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.fml.loading.FMLEnvironment

class DebugWandItem(properties: Properties) : TooltipItem(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (!FMLEnvironment.production) {
            if (!level.isClientSide) {
                ChunkPollution.addPollution(level, player.blockPosition(), ChunkPollution.PollutionData(
                    soot = 10F,
                    poison = 10F,
                    heavyMetal = 10F,
                ))
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide)
        }

        return super.use(level, player, hand)
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        if (FMLEnvironment.production)
            super.appendHoverText(stack, level, tooltip, flag)
    }
}
