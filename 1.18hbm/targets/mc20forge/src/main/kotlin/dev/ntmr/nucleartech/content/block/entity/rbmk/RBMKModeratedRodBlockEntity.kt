package dev.ntmr.nucleartech.content.block.entity.rbmk

import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedRodBlockEntity(pos: BlockPos, state: BlockState) : RBMKRodBlockEntity(NTechBlockEntities.rbmkModeratedRodBlockEntityType.get(), pos, state) {
    override fun isModerated() = true
}
