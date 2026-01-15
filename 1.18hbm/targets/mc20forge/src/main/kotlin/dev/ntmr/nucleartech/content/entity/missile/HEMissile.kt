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

open class HEMissile : AbstractMissile {
    constructor(entityType: EntityType<HEMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileHE.get(), level, startPos, targetPos)

    override fun onImpact() {
        ExplosionLarge.createAndStart(level(), position(), 10F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
    }
}
