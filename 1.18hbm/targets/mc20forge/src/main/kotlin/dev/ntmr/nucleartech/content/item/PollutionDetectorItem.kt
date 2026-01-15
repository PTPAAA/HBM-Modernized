/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.Agnostics
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.yellow
import dev.ntmr.nucleartech.packets.PushNotificationMessage
import dev.ntmr.nucleartech.roundToPoints
import dev.ntmr.nucleartech.system.world.ChunkPollution
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PollutionDetectorItem(properties: Properties) : Item(properties) {
    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slot: Int, selected: Boolean) {
        if (entity !is ServerPlayer || level.gameTime % 10 != 0L) return

        val pollution = ChunkPollution.getPollution(level, entity.blockPosition())
        Agnostics.displayNotification(entity,
            message = LangKeys.DEVICE_POLLUTION_DETECTOR_SOOT.format(pollution.soot.roundToPoints(2)).append("\n")
                .append(LangKeys.DEVICE_POLLUTION_DETECTOR_POISON.format(pollution.poison.roundToPoints(2))).append("\n")
                .append(LangKeys.DEVICE_POLLUTION_DETECTOR_HEAVY_METAL.format(pollution.heavyMetal.roundToPoints(2)))
                .yellow(),
            id = PushNotificationMessage.Common.PollutionDetector.id
        )
    }
}
