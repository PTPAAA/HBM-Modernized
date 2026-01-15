package dev.ntmr.nucleartech.client.model.renderable

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.resources.ResourceLocation

/**
 * Compatibility class for HBM 1.18 rendering logic
 * Moved from net.minecraftforge.client.model.renderable to avoid split package issue
 */
class MultipartTransforms {
    companion object {
        val EMPTY = MultipartTransforms()
    }
}

interface SimpleRenderable : BakedModel {
    fun render(
        matrix: PoseStack,
        buffers: MultiBufferSource,
        renderTypeGetter: (ResourceLocation) -> RenderType,
        light: Int,
        overlay: Int,
        partials: Float,
        transforms: MultipartTransforms
    )
}
