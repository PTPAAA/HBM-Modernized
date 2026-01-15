/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.rendering

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.system.capability.Capabilities
import dev.ntmr.nucleartech.system.config.NuclearConfig
import net.minecraft.Util
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.gui.ForgeIngameGui
import net.minecraftforge.client.gui.IIngameOverlay
import net.minecraftforge.client.gui.OverlayRegistry
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object Overlays {
    private val minecraft = Minecraft.getInstance()
    val hideGuiOption: Boolean get() = minecraft.options.hideGui

    val GEIGER_ELEMENT = OverlayRegistry.registerOverlayBelow(ForgeIngameGui.HUD_TEXT_ELEMENT, "NTM Geiger Counter", GeigerOverlay)
    val PUSH_NOTIFICATIONS_ELEMENT = OverlayRegistry.registerOverlayBelow(ForgeIngameGui.HUD_TEXT_ELEMENT, "NTM Push Notifications", PushNotificationsOverlay)

    fun registerOverlays() {
        // Static initialization woo
        NuclearTech.LOGGER.debug("Registered HUD overlays")
    }

    // ForgeIngameGui#renderTextureOverlay
    fun renderTextureOverlay(texture: ResourceLocation, alpha: Float, width: Int, height: Int) {
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.defaultBlendFunc()
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, alpha)
        RenderSystem.setShaderTexture(0, texture)
        val tesselator = Tesselator.getInstance()
        val bufferbuilder = tesselator.builder
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
        bufferbuilder.vertex(0.0, height.toDouble(), -90.0).uv(0F, 1F).endVertex()
        bufferbuilder.vertex(width.toDouble(), height.toDouble(), -90.0).uv(1F, 1F).endVertex()
        bufferbuilder.vertex(width.toDouble(), 0.0, -90.0).uv(1F, 0F).endVertex()
        bufferbuilder.vertex(0.0, 0.0, -90.0).uv(0F, 0F).endVertex()
        tesselator.end()
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
    }

    private object GeigerOverlay : IIngameOverlay {
        private val GEIGER_OVERLAY = ntm("textures/gui/hud/geiger_hud_overlay.png")
        private var lastSurvey = 0L
        private var previousResult = 0F
        private var lastResult = 0F

        override fun render(gui: ForgeIngameGui, poseStack: PoseStack, partialTick: Float, width: Int, height: Int) {
            if (!hideGuiOption) {
                val player = minecraft.player ?: return
                if (player.inventory.hasAnyOf(setOf(NTechItems.geigerCounter.get()))) { // TODO check for FSB armor
                    val radiation = Capabilities.getContamination(player)?.getIrradiation() ?: return
                    renderGeigerHUD(poseStack, radiation)
                }
            }
        }

        private fun renderGeigerHUD(matrixStack: PoseStack, radValueIn: Float) {
            matrixStack.pushPose()
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)

            val radiation = lastResult - previousResult

            if (System.currentTimeMillis() >= lastSurvey + 1000) {
                lastSurvey = System.currentTimeMillis()
                previousResult = lastResult
                lastResult = radValueIn
            }

            val barFillAmount = min(radValueIn / 1000F * 74F, 74F).toInt()
            val posX = 16
            val posY = minecraft.window.guiScaledHeight - 20

            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.setShaderTexture(0, GEIGER_OVERLAY)
            GuiComponent.blit(matrixStack, posX, posY, 0, 0F, 0F, 94, 18, 128, 128)
            GuiComponent.blit(matrixStack, posX + 1, posY + 1, 0, 1F, 19F, barFillAmount, 16, 128, 128)

            // 74 = length of bar
            when {
                radiation >= 25 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 36F, 36F, 18, 18, 128, 128)
                radiation >= 10 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 18F, 36F, 18, 18, 128, 128)
                radiation >= 2.5 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 0F, 36F, 18, 18, 128, 128)
            }

            when {
                radiation > 1000 -> minecraft.font.draw(matrixStack, ">1000 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
                radiation >= 1 -> minecraft.font.draw(matrixStack, "${radiation.roundToInt()} RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
                radiation > 0 -> minecraft.font.draw(matrixStack, "<1 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
            }

            matrixStack.popPose()
        }
    }

    object PushNotificationsOverlay : IIngameOverlay {
        enum class OverlayPosition(val horizontalOffset: (screenWidth: Int, boxWidth: Int) -> Int, val verticalOffset: (screenHeight: Int, boxHeight: Int) -> Int) {
            TopLeft({ _, _ -> 15 }, { _, _ -> 15 }),
            TopRight({ screenWidth, boxWidth -> screenWidth - boxWidth - 15 }, { _, _ -> 15 }),
            CrosshairLeft({ screenWidth, boxWidth -> screenWidth / 2 - boxWidth - 6 }, { screenHeight, _ -> screenHeight / 2 + 7 }),
            CrosshairRight({ screenWidth, boxWidth -> screenWidth / 2 + 7 }, { screenHeight, _ -> screenHeight / 2 + 7 }),
        }

        private var nextID = 1000
        private val messages = mutableMapOf<Int, InfoEntry>()

        fun push(message: Component, start: Long = Util.getMillis(), duration: Int = 3000, id: Int = nextID++) {
            messages[if (id == -1) nextID++ else id] = InfoEntry(message, start, duration)
        }

        override fun render(gui: ForgeIngameGui, poseStack: PoseStack, partialTick: Float, width: Int, height: Int) {
            val now = Util.getMillis()

            // Remove messages after duration has passed
            run {
                val iterator = messages.iterator()
                while (iterator.hasNext()) {
                    val (id, message) = iterator.next()
                    if (message.start + message.duration < now)
                        iterator.remove()
                }
            }

            if (messages.isEmpty())
                return

            val entries = messages.values.sorted()
            val wrappedMessages = entries.associateWith { gui.font.split(it.message, max(width / 2, 200)) }
            val lines = wrappedMessages.values.flatten()
            val spacing = gui.font.lineHeight + 1
            val boxWidth = lines.maxOf { gui.font.width(it) }
            val boxHeight = lines.size * spacing

            val positionMode = NuclearConfig.client.pushNotificationPositionMode.get()
            val offsetX = positionMode.horizontalOffset(width, boxWidth) + NuclearConfig.client.pushNotificationPositionOffsetHorizontal.get().toDouble()
            val offsetY = positionMode.verticalOffset(height, boxHeight) + NuclearConfig.client.pushNotificationPositionOffsetVertical.get().toDouble()

            poseStack.pushPose()
            poseStack.translate(offsetX, offsetY, 0.0)
            poseStack.scale(1F, 1F, 1F)
            GuiComponent.fill(poseStack, -5, -5, boxWidth + 5, boxHeight + 2, minecraft.options.getBackgroundColor(0.3F))
            RenderSystem.enableBlend()
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)

            var offset = 0
            for ((entry, components) in wrappedMessages) {
                val elapsed = (now - entry.start).toInt()
                val alpha = max(min(510 * (entry.duration - elapsed) / entry.duration, 255), 5)
                for (component in components) {
                    gui.font.draw(poseStack, component, 0F, offset.toFloat(), 0xFFFFFF or (alpha shl 24))
                    offset += gui.font.lineHeight + 1
                }
            }

            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            poseStack.popPose()
        }

        private data class InfoEntry(val message: Component, val start: Long, val duration: Int) : Comparable<InfoEntry> {
            override fun compareTo(other: InfoEntry) = duration - other.duration
        }
    }
}
