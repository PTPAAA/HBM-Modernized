package dev.ntmr.nucleartech.content.block.entity.rbmk

import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedReaSimRodBlockEntity(pos: BlockPos, state: BlockState) : RBMKReaSimRodBlockEntity(NTechBlockEntities.rbmkModeratedReaSimRodBlockEntityType.get(), pos, state) {
    override fun isModerated() = true
}
