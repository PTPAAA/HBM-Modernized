/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.api.explosion.NuclearExplosionMk4Params
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.system.config.NuclearConfig
import dev.ntmr.nucleartech.system.explosion.Explosions
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class NuclearMissile : AbstractMissile {
    constructor(entityType: EntityType<NuclearMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileNuclear.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_NUCLEAR
    override val renderScale = 1.5F

    override fun onImpact() {
        Explosions.getBuiltinDefault().createAndStart(level(), position(), NuclearConfig.explosions.missileStrength.get().toFloat(), NuclearExplosionMk4Params())
    }
}
