/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.hazard.type

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.red
import dev.ntmr.nucleartech.system.capability.Capabilities
import dev.ntmr.nucleartech.system.capability.contamination.addDigamma
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

class DigammaHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        if (!target.level().isClientSide) {
            Capabilities.getContamination(target)?.addDigamma(level / 20F * itemStack.count)
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val digamma = modifiers.evaluateAllModifiers(itemStack, player, level)

        with(tooltip) {
            add(LangKeys.HAZARD_DIGAMMA.red())
            add(Component.literal("${floor(digamma * itemStack.count * 10_000F) / 10F} mDRX/s"))
        }
    }
}
