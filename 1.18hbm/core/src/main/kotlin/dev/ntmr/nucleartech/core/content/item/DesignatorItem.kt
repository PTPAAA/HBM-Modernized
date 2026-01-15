/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content.item

import dev.ntmr.nucleartech.core.LangKeys
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.InteractionResult
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.context.UseOnContext

class DesignatorItem(properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.getLevel()
        val player = context.getPlayer()
        val pos = context.getClickedPos()

        if (level.isClientSide) {
            player?.displayClientMessage(LangKeys.DEVICE_POSITION_SET.get(), true)
        } else with(context.getItemInHand().getOrCreateTag()) {
            putIntArray("TargetPos", intArrayOf(pos.x, pos.y, pos.z))
        }

        return InteractionResult.sidedSuccess(level.isClientSide)
    }
}
