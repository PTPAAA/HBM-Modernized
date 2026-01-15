package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKReflectorBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKReflectorRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKReflectorBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKReflectorBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/reflector.png")
    override val glassLidTexture = ntm("textures/other/rbmk/reflector_glass.png")
}
