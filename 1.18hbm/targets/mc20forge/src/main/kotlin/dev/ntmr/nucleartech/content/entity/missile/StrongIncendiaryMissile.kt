/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import dev.ntmr.nucleartech.math.burnSphericalFlammable
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class StrongIncendiaryMissile : AbstractMissile {
    constructor(entityType: EntityType<StrongIncendiaryMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileIncendiaryStrong.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_STRONG
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level(), position(), 25F, ExplosionLargeParams(fire = true, cloud = true, rubble = true, shrapnel = true))
        burnSphericalFlammable(level(), blockPosition(), 20)
        // TODO burn entities
    }
}
