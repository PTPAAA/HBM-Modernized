package dev.ntmr.nucleartech.content.block.explosive

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class LandmineBlock(properties: Properties) : Block(properties) {
    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!level.isClientSide) {
            level.explode(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 4.0F, Level.ExplosionInteraction.TNT)
            level.removeBlock(pos, false)
        }
        super.stepOn(level, pos, state, entity)
    }
}
