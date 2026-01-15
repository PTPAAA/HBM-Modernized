package dev.ntmr.nucleartech.content.block.entity.renderer

import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.block.entity.SmallCoolingTowerBlockEntity
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class SmallCoolingTowerRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<SmallCoolingTowerBlockEntity>(context) {
    override fun getModel(blockEntity: SmallCoolingTowerBlockEntity) = SpecialModels.COOLING_TOWER_SMALL.get()
}
