/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.content.entity.Meteor
import net.minecraft.Util
import net.minecraft.network.chat.ChatType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class MeteorRemoteItem(properties: Properties) : TooltipItem(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemInHand = player.getItemInHand(hand)

        itemInHand.hurtAndBreak(1, player) {
            it.broadcastBreakEvent(hand)
        }

        if (!level.isClientSide) {
            Meteor.spawnMeteorAtPlayer(player as ServerPlayer, false)
            player.displayClientMessage(LangKeys.DEVICE_METEOR_REMOTE_WATCH_OUT.get(), true)
        }

        level.playSound(player, player.x, player.y, player.z, NTechSounds.randomBleep.get(), SoundSource.PLAYERS, 1F, 1F)

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide)
    }
}
