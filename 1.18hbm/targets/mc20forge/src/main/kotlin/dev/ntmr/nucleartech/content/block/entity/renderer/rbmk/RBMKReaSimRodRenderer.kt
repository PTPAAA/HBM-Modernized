package dev.ntmr.nucleartech.content.block.entity.renderer.rbmk

import dev.ntmr.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

open class RBMKReaSimRodRenderer(context: BlockEntityRendererProvider.Context) : RBMKRodRenderer(context) {
    override val texture = ntm("textures/other/rbmk/reasim_rods.png")
}
