package dev.ntmr.nucleartech.content.block.hazard

import dev.ntmr.nucleartech.content.block.HazardBlock
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

class TaintBlock(properties: Properties) : HazardBlock(properties) {

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide) {
            // Spread logic
            if (random.nextInt(10) == 0) { // Chance to spread
                for (i in 0..2) { // Try multiple times
                    val spreadPos = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1)
                    if (spreadPos == pos) continue
                    
                    val targetState = level.getBlockState(spreadPos)
                    if (canSpreadTo(targetState)) {
                        level.setBlockAndUpdate(spreadPos, this.defaultBlockState())
                    }
                }
            }
        }
    }

    private fun canSpreadTo(state: BlockState): Boolean {
        return state.`is`(Blocks.DIRT) || 
               state.`is`(Blocks.GRASS_BLOCK) || 
               state.`is`(Blocks.STONE) ||
               state.`is`(Blocks.COBBLESTONE) ||
               state.`is`(Blocks.GRAVEL) ||
               state.`is`(Blocks.SAND)
    }

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!level.isClientSide && entity is LivingEntity) {
             entity.hurt(level.damageSources().magic(), 1.0f)
        }
        super.stepOn(level, pos, state, entity)
    }
}
