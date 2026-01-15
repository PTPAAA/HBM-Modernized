package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.CentrifugeBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class CentrifugeRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<CentrifugeBlockEntity>(context) {
    override fun getModel(blockEntity: CentrifugeBlockEntity) = SpecialModels.CENTRIFUGE.get()
}
