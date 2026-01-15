/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity

import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.block.entity.VolcanoBlockEntity
import dev.ntmr.nucleartech.system.explosion.ExplosionVNT
import dev.ntmr.nucleartech.math.toVec3Middle
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult

class VolcanicShrapnel(entityType: EntityType<out VolcanicShrapnel>, level: Level) : Shrapnel(entityType, level) {
    constructor(level: Level) : this(NTechEntities.volcanicShrapnel.get(), level)

    override fun onHitBlock(hitResult: BlockHitResult) {
        super.onHitBlock(hitResult)

        if (deltaMovement.y < -0.2) {
            val above = hitResult.blockPos.above()
            if (level.getBlockState(above).material.isReplaceable)
                level.setBlockAndUpdate(above, NTechFluids.volcanicLava.block.get().defaultBlockState())

            // TODO set monoxide
        } else if (deltaMovement.y > 0.0) {
            ExplosionVNT.createStandard(level, hitResult.blockPos.toVec3Middle(), 7F).apply(VolcanoBlockEntity.volcanoExplosionAttributes).explode()
        }
    }
}
