/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity

import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.NTechParticles
import dev.ntmr.nucleartech.content.particle.sendParticles
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

class OilSpill(entityType: EntityType<out OilSpill>, level: Level) : ThrowableProjectile(entityType, level) {
    constructor(level: Level) : this(NTechEntities.oilSpill.get(), level)

    override fun defineSynchedData() {}

    override fun tick() {
        super.tick()

        val level = level
        if (level is ServerLevel) {
            level.sendParticles(NTechParticles.OIL_SPILL.get(), true, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
            if (isOnFire) {
                discard()
                level.explode(this, x, y, z, 1.5F, true, Explosion.BlockInteraction.DESTROY)
            }
        }
    }

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)

        if (tickCount > 5)
            discard()
    }

    companion object {
        fun spawnOilSpills(level: Level, pos: Vec3, amount: Int) {
            for (i in 0 until amount) {
                val oilSpill = OilSpill(level)
                oilSpill.moveTo(pos)
                oilSpill.setDeltaMovement(
                    level.random.nextGaussian() * (1 + (amount / 50)) * .15,
                    (level.random.nextDouble() * .5 + .5) * (1 + (amount / (15 + level.random.nextInt(21)))) + (level.random.nextDouble() / 50 * amount) * .25,
                    level.random.nextGaussian() * (1 + (amount / 50)) * .15
                )
                level.addFreshEntity(oilSpill)
            }
        }
    }
}
