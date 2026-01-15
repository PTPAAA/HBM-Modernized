/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block.entity.machine

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.world.level.Level

class TeleporterBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.teleporter.get(), pos, state) {
    // Minimal implementation for debugging

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: TeleporterBlockEntity) {
            // No-op
        }
    }
}
