/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import dev.ntmr.nucleartech.math.component1
import dev.ntmr.nucleartech.math.component2
import dev.ntmr.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class StrongBunkerBusterMissile : AbstractMissile {
    constructor(entityType: EntityType<StrongBunkerBusterMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileBunkerBusterStrong.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_STRONG
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level(), position(), 7.5F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
        val (x, y, z) = position()
        for (i in 1 until 20)
            level().explode(this, x, y - i, z, 7.5F, Level.ExplosionInteraction.BLOCK)
    }
}
