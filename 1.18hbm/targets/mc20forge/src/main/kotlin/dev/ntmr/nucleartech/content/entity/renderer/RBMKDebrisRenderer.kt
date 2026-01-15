/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.renderer
import com.mojang.math.Axis

import com.mojang.blaze3d.vertex.PoseStack
import org.joml.Vector3f
import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.entity.RBMKDebris
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import dev.ntmr.nucleartech.client.model.renderable.MultipartTransforms

class RBMKDebrisRenderer(context: EntityRendererProvider.Context) : EntityRenderer<RBMKDebris>(context) {
    override fun getTextureLocation(debris: RBMKDebris): ResourceLocation = throw UnsupportedOperationException("Texture is baked with model")

    private val rotationVector = Vector3f(1F, 1F, 1F).apply(Vector3f::normalize)

    override fun render(
        entity: RBMKDebris,
        yaw: Float,
        partialTicks: Float,
        matrix: PoseStack,
        renderer: MultiBufferSource,
        light: Int
    ) {
        super.render(entity, yaw, partialTicks, matrix, renderer, light)

        matrix.pushPose()
        matrix.translate(0.0, 0.125, 0.0)
        matrix.mulPose(Axis.YP.rotationDegrees(entity.yRot))
        val degrees = entity.xRotO + (entity.xRot - entity.xRotO) * partialTicks
        matrix.mulPose(org.joml.Quaternionf().setAngleAxis(Math.toRadians(degrees.toDouble()).toFloat(), rotationVector.x, rotationVector.y, rotationVector.z))

        val model = when (entity.getDebrisType()) {
            RBMKDebris.DebrisType.BLANK -> SpecialModels.RBMK_DEBRIS_BLANK
            RBMKDebris.DebrisType.ELEMENT -> SpecialModels.RBMK_DEBRIS_ELEMENT
            RBMKDebris.DebrisType.FUEL -> SpecialModels.RBMK_DEBRIS_FUEL
            RBMKDebris.DebrisType.ROD -> SpecialModels.RBMK_DEBRIS_ROD
            RBMKDebris.DebrisType.GRAPHITE -> SpecialModels.RBMK_DEBRIS_GRAPHITE
            RBMKDebris.DebrisType.LID -> SpecialModels.RBMK_DEBRIS_LID
        }

        model.get().render(matrix, renderer, RenderType::entityCutout, light, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY)

        matrix.popPose()
    }
}


