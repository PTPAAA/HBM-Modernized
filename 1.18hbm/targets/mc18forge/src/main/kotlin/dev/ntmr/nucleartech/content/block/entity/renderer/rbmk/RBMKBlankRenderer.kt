package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKBlankBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKBlankRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKBlankBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKBlankBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/blank.png")
}
