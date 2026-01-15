/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.hazard.blocks

import dev.ntmr.nucleartech.system.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.util.RandomSource

class RadioactiveBlockEffect(val radiationPerTick: Float) : HazardBlockEffect {
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        ChunkRadiation.incrementRadiation(level, pos, radiationPerTick)
    }
}
