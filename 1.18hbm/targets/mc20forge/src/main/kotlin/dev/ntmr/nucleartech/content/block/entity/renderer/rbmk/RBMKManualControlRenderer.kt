package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKManualControlBlockEntity
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

open class RBMKManualControlRenderer(context: BlockEntityRendererProvider.Context) : RBMKControlRenderer<RBMKManualControlBlockEntity>(context) {
    override val texture = ntm("textures/other/rbmk/manual_control.png")
}
