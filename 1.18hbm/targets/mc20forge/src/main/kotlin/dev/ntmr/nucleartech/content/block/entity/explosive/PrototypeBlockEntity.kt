package dev.ntmr.nucleartech.content.block.entity.explosive

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.api.explosion.NuclearExplosionMk4Params
import dev.ntmr.nucleartech.system.explosion.NukeExplosion
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PrototypeBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.prototype.get(), pos, state) {
    
    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: PrototypeBlockEntity) {
             // Placeholder for complex detonation logic
        }
    }
}
