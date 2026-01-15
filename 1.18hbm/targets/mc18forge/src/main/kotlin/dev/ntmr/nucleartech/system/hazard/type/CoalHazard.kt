/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.hazard.type

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.darkGray
import dev.ntmr.nucleartech.system.capability.Capabilities
import dev.ntmr.nucleartech.system.capability.contamination.addBlackLung
import dev.ntmr.nucleartech.system.hazard.modifier.HazardModifier
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.min

class CoalHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO config

        // TODO protection

        if (!target.level.isClientSide) {
            Capabilities.getContamination(target)?.addBlackLung(min(level.toInt(), 10))
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += LangKeys.HAZARD_COAL.darkGray()
    }
}
