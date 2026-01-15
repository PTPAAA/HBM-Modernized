package dev.ntmr.nucleartech.system.hazard.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.util.RandomSource

interface HazardBlockEffect {
    /** Gets called every second */
    fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource)
}
