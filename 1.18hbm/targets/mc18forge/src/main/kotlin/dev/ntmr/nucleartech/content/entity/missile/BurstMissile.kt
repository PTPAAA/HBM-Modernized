/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class BurstMissile : AbstractMissile {
    constructor(entityType: EntityType<BurstMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileBurst.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_HUGE
    override val renderScale = 2F

    override fun onImpact() {
        val position = position()
        for (i in 0 until 4) ExplosionLarge.createAndStart(level, position, 50F, ExplosionLargeParams())
        ExplosionLarge.createAndStart(level, position, 50F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
    }
}
