/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.entity.renderer
import com.mojang.math.Axis

import com.mojang.blaze3d.vertex.PoseStack
import org.joml.Vector3f
import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.entity.missile.AbstractMissile
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.Mth
import net.minecraftforge.client.model.data.ModelData

class SimpleMissileRenderer<M : AbstractMissile>(context: EntityRendererProvider.Context) : EntityRenderer<M>(context) {
    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer

    override fun getTextureLocation(missile: M) = missile.renderTexture

    override fun render(missile: M, yaw: Float, partials: Float, stack: PoseStack, buffers: MultiBufferSource, light: Int) {
        super.render(missile, yaw, partials, stack, buffers, light)

        stack.pushPose()
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partials, missile.yRotO,  missile.yRot) - 90F))
        stack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partials, missile.xRotO, missile.xRot) - 90F))

        val model = SpecialModels.getBakedModel(missile.renderModel)
        val renderBuffer = buffers.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(missile)))
        stack.pushPose()
        stack.scale(missile.renderScale, missile.renderScale, missile.renderScale)
        modelRenderer.renderModel(stack.last(), renderBuffer, null, model, 1F, 1F, 1F, light, OverlayTexture.NO_OVERLAY)
        stack.popPose()

        stack.popPose()
    }
}



