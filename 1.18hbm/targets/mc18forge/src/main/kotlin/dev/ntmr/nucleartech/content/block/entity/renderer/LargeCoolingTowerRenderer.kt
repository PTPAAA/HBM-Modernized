package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.LargeCoolingTowerBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class LargeCoolingTowerRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<LargeCoolingTowerBlockEntity>(context) {
    override fun getModel(blockEntity: LargeCoolingTowerBlockEntity) = SpecialModels.COOLING_TOWER_LARGE.get()
}
