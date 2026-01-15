package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKBoilerBlockEntity
import dev.ntmr.nucleartech.content.block.rbmk.RBMKBaseBlock
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKBoilerRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKBoilerBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKBoilerBlockEntity) = SpecialModels.RBMK_CONTROL_ROD_COLUMN.get()
    override fun getLidModel(blockEntity: RBMKBoilerBlockEntity) = if (blockEntity.blockState.getValue(RBMKBaseBlock.LID_TYPE).seeThrough()) SpecialModels.RBMK_LID.get() else SpecialModels.RBMK_CONTROL_ROD_LID.get()
    override val texture = ntm("textures/other/rbmk/boiler.png")
    override val glassLidTexture = ntm("textures/other/rbmk/boiler_glass.png")
}
