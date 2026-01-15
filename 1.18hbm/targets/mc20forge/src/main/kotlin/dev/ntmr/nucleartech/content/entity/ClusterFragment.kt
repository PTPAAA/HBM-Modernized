/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity

import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

class ClusterFragment(entityType: EntityType<ClusterFragment>, level: Level) : ThrowableItemProjectile(entityType, level) {
    override fun getDefaultItem() = NTechItems.plutoniumCore.get()

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)
        ExplosionLarge.createAndStart(level(), position(), 5F, ExplosionLargeParams(cloud = true, rubble = false, shrapnel = true))
        discard()
    }

    override fun getGravity() = 0.0125F

    companion object {
        fun spawnClustered(level: Level, position: Vec3, count: Int) {
            val random = level.random
            for (i in 0 until count) {
                var dx = random.nextGaussian()
                val dy = random.nextGaussian()
                var dz = random.nextGaussian()
                if (random.nextBoolean()) dx = -dx
                if (random.nextBoolean()) dz = -dz

                level.addFreshEntity(ClusterFragment(NTechEntities.clusterFragment.get(), level).apply {
                    moveTo(position)
                    setDeltaMovement(dx, dy, dz)
                })
            }
        }
    }
}
