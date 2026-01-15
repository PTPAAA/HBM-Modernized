package dev.ntmr.nucleartech.content.block.entity.explosive

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.entity.effect.BlackHoleEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlackHoleBombBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.blackHoleBomb.get(), pos, state) {
    var timer = -1

    fun arm() {
        if (timer == -1) {
            timer = 200 // 10 seconds
        }
    }

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: BlackHoleBombBlockEntity) {
            if (entity.timer > 0) {
                entity.timer--
                if (entity.timer == 0) {
                    // Detonate
                    if (!level.isClientSide) {
                        level.removeBlock(pos, false)
                        val blackHole = BlackHoleEntity(NTechEntities.blackHole.get(), level)
                        blackHole.setPos(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)
                        blackHole.size = 1.0F
                        level.addFreshEntity(blackHole)
                    }
                }
            }
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        timer = tag.getInt("Timer")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Timer", timer)
    }
}
