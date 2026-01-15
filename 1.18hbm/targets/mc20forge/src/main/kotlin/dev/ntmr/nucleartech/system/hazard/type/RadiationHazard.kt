/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.hazard.type

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.green
import dev.ntmr.nucleartech.extensions.yellow
import dev.ntmr.nucleartech.system.hazard.EntityContaminationEffects
import dev.ntmr.nucleartech.system.hazard.modifier.HazardModifier
import dev.ntmr.nucleartech.system.hazard.modifier.evaluateAllModifiers
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.floor

class RadiationHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        if (level <= 0 || target.level().isClientSide) return

        val radiation = level / 20F * itemStack.count // TODO reacher
        EntityContaminationEffects.contaminate(target, EntityContaminationEffects.HazardType.Radiation, EntityContaminationEffects.ContaminationType.Creative, radiation)
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        // TODO direct hit radiation
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val radiation = modifiers.evaluateAllModifiers(itemStack, player, level)

        if (radiation < 1E-5) return

        with(tooltip) {
            add(LangKeys.HAZARD_RADIATON.green())
            add(Component.literal("${floor(radiation * itemStack.count * 1000F) / 1000F} RAD/s").yellow())
        }
    }
}
