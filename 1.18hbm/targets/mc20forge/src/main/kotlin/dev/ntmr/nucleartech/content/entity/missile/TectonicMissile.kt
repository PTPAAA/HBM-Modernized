/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.missile

import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.content.NTechBlocks
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.block.VolcanoBlock
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class TectonicMissile : AbstractMissile {
    constructor(entityType: EntityType<TectonicMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(NTechEntities.missileTectonic.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_NUCLEAR
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level(), position(), 10F, ExplosionLargeParams(fire = true, cloud = true, rubble = true))

        for (x in -1..1) for (y in -1..1) for (z in -1..1)
            level().setBlockAndUpdate(blockPosition().offset(x, y, z), NTechFluids.volcanicLava.block.get().defaultBlockState())

        level().setBlockAndUpdate(blockPosition(), NTechBlocks.volcanoCore.get().defaultBlockState().let {
            if (random.nextInt(5) == 0) {
                it.setValue(VolcanoBlock.SMOLDERING, true)
            } else {
                it.setValue(VolcanoBlock.GROWS, random.nextInt(10) == 0).setValue(VolcanoBlock.EXTINGUISHES, random.nextInt(10) < 9)
            }
        })
    }
}
