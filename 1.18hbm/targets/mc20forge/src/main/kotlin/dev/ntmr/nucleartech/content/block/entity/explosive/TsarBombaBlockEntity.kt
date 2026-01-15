package dev.ntmr.nucleartech.content.block.entity.explosive

import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class TsarBombaBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.tsarBomba.get(), pos, state) {
    
    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: TsarBombaBlockEntity) {
             // Placeholder: 50MT explosion
        }
    }
}
