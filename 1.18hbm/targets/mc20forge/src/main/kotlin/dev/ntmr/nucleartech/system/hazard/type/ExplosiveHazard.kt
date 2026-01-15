/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.hazard.type

import net.minecraft.world.level.Level
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.red
import dev.ntmr.nucleartech.system.hazard.modifier.HazardModifier
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Explosion
import kotlin.math.ln

class ExplosiveHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO config

        if (!target.level().isClientSide && target.isOnFire) {
            target.level().explode(null, target.x, target.y, target.z, level * (ln(itemStack.count.toFloat()) + 1), false, Level.ExplosionInteraction.BLOCK)
            itemStack.count = 0
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        // TODO config

        if (!itemEntity.level().isClientSide && itemEntity.isOnFire) {
            itemEntity.discard()
            itemEntity.level().explode(null, itemEntity.x, itemEntity.y, itemEntity.z, level * (ln(itemEntity.item.count.toFloat()) + 1), false, Level.ExplosionInteraction.BLOCK)
        }
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += LangKeys.HAZARD_EXPLOSIVE.red()
    }
}
