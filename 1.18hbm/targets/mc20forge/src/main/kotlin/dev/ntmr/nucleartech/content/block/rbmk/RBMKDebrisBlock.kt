package dev.ntmr.nucleartech.content.block.rbmk

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType

@Suppress("OVERRIDE_DEPRECATION")
open class RBMKDebrisBlock(properties: Properties) : Block(properties) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F
}
