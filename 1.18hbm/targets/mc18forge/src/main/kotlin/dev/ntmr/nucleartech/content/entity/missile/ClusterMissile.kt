/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.entity.ClusterFragment
import dev.ntmr.nucleartech.math.component1
import dev.ntmr.nucleartech.math.component2
import dev.ntmr.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class ClusterMissile : AbstractClusterMissile {
    constructor(entityType: EntityType<ClusterMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileCluster.get(), level, startPos, targetPos)

    override fun onImpact() {
        val (x, y, z) = position()
        level.explode(this, x, y, z, 5F, false, Explosion.BlockInteraction.DESTROY)
        ClusterFragment.spawnClustered(level, position(), 25)
    }
}
