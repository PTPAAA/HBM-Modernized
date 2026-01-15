package dev.ntmr.nucleartech.content.block.entity.rbmk

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedControlBlockEntity(pos: BlockPos, state: BlockState) : RBMKManualControlBlockEntity(NTechBlockEntities.rbmkModeratedControlBlockEntityType.get(), pos, state) {
    override val defaultName = LangKeys.CONTAINER_RBMK_CONTROL_MODERATED.get()
    override fun isModerated() = true
}
