package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAutoControlBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKAutoControlRenderer(context: BlockEntityRendererProvider.Context) : RBMKControlRenderer<RBMKAutoControlBlockEntity>(context) {
    override val texture = ntm("textures/other/rbmk/auto_control.png")
}
