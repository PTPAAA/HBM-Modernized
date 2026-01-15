package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.FatManBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class FatManRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<FatManBlockEntity>(context) {
    override fun getModel(blockEntity: FatManBlockEntity) = SpecialModels.FAT_MAN.get()
}
