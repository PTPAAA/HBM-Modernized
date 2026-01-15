package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.LittleBoyBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class LittleBoyRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<LittleBoyBlockEntity>(context) {
    override fun getModel(blockEntity: LittleBoyBlockEntity) = SpecialModels.LITTLE_BOY.get()
}
