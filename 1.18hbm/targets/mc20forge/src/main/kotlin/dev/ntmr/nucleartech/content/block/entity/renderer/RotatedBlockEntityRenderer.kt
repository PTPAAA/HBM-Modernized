package dev.ntmr.nucleartech.content.block.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import org.joml.Vector3f
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import dev.ntmr.nucleartech.client.model.renderable.MultipartTransforms
import dev.ntmr.nucleartech.client.model.renderable.SimpleRenderable

abstract class RotatedBlockEntityRenderer<T : BlockEntity>(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<T>(context) {
    override fun render(blockEntity: T, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val model = getModel(blockEntity)

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(com.mojang.math.Axis.YN.rotationDegrees(blockEntity.blockState.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        renderRotated(blockEntity, model, partials, matrix, buffers, light, overlay)
        matrix.popPose()
    }

    protected open fun renderRotated(blockEntity: T, model: SimpleRenderable, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        model.render(matrix, buffers, renderType, light, overlay, partials, MultipartTransforms.EMPTY)
    }
}
