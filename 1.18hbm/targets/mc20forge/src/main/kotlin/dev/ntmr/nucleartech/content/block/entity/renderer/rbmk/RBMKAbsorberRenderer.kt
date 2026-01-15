package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAbsorberBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKAbsorberRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKAbsorberBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKAbsorberBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/absorber.png")
    override val glassLidTexture = ntm("textures/other/rbmk/absorber_glass.png")
}
