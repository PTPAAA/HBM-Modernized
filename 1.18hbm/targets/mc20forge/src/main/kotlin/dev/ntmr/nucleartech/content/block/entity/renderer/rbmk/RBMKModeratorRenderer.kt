package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratorBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKModeratorRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKModeratorBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKModeratorBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/moderator.png")
    override val glassLidTexture = ntm("textures/other/rbmk/moderator_glass.png")
}
