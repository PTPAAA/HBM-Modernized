/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity

import dev.ntmr.nucleartech.content.NTechEntities
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class WasteItemEntity : ItemEntity {
    constructor(type: EntityType<WasteItemEntity>, level: Level) : super(type, level)

    constructor(level: Level, x: Double, y: Double, z: Double, stack: ItemStack) : this(level, x, y, z, stack, level.random.nextDouble() * .2 - .1, .2, level.random.nextDouble() * .2 - .1)

    constructor(level: Level, x: Double, y: Double, z: Double, stack: ItemStack, dx: Double, dy: Double, dz: Double) : this(NTechEntities.wasteItemEntity.get(), level) {
        setPos(x, y, z)
        setDeltaMovement(dx, dy, dz)
        item = stack
        lifespan = if (stack.isEmpty) 6000 else stack.getEntityLifespan(level)
    }

    init {
        isInvulnerable = true
    }

    override fun hurt(source: DamageSource, damage: Float) = false
}
