package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.OilDerrickBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class OilDerrickRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<OilDerrickBlockEntity>(context) {
    override fun getModel(blockEntity: OilDerrickBlockEntity) = SpecialModels.OIL_DERRICK.get()
}
