/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.rendering

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.system.capability.Capabilities
import dev.ntmr.nucleartech.system.config.NuclearConfig
import net.minecraft.Util
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.gui.overlay.IGuiOverlay
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object Overlays {
    private val minecraft = Minecraft.getInstance()
    val hideGuiOption: Boolean get() = minecraft.options.hideGui

    // Overlays now registered via RegisterGuiOverlaysEvent in ClientRegistries
    
    fun registerOverlays() {
        // Static initialization - actual registration happens via event
        NuclearTech.LOGGER.debug("Overlay registration requested")
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

    object GeigerOverlay : IGuiOverlay {
        private val GEIGER_OVERLAY = ntm("textures/gui/hud/geiger_hud_overlay.png")
        private var lastSurvey = 0L
        private var previousResult = 0F
        private var lastResult = 0F

        override fun render(gui: net.minecraftforge.client.gui.overlay.ForgeGui, graphics: GuiGraphics, partialTick: Float, width: Int, height: Int) {
            if (!hideGuiOption) {
                val player = minecraft.player ?: return
                if (player.inventory.hasAnyOf(setOf(NTechItems.geigerCounter.get()))) {
                    val radiation = Capabilities.getContamination(player)?.getIrradiation() ?: return
                    renderGeigerHUD(graphics, radiation)
                }
            }
        }

        private fun renderGeigerHUD(graphics: GuiGraphics, radValueIn: Float) {
            graphics.pose().pushPose()
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
            graphics.blit(GEIGER_OVERLAY, posX, posY, 0F, 0F, 94, 18, 128, 128)
            graphics.blit(GEIGER_OVERLAY, posX + 1, posY + 1, 1F, 19F, barFillAmount, 16, 128, 128)

            // 74 = length of bar
            when {
                radiation >= 25 -> graphics.blit(GEIGER_OVERLAY, posX + 76, posY - 18, 36F, 36F, 18, 18, 128, 128)
                radiation >= 10 -> graphics.blit(GEIGER_OVERLAY, posX + 76, posY - 18, 18F, 36F, 18, 18, 128, 128)
                radiation >= 2.5 -> graphics.blit(GEIGER_OVERLAY, posX + 76, posY - 18, 0F, 36F, 18, 18, 128, 128)
            }

            val font = minecraft.font
            when {
                radiation > 1000 -> graphics.drawString(font, ">1000 RAD/s", posX, posY - 8, 0xFF0000)
                radiation >= 1 -> graphics.drawString(font, "${radiation.roundToInt()} RAD/s", posX, posY - 8, 0xFF0000)
                radiation > 0 -> graphics.drawString(font, "<1 RAD/s", posX, posY - 8, 0xFF0000)
            }

            graphics.pose().popPose()
        }
    }

    object PushNotificationsOverlay : IGuiOverlay {
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

        override fun render(gui: net.minecraftforge.client.gui.overlay.ForgeGui, graphics: GuiGraphics, partialTick: Float, width: Int, height: Int) {
            val now = Util.getMillis()
            val font = minecraft.font

            // Remove messages after duration has passed
            run {
                val iterator = messages.iterator()
                while (iterator.hasNext()) {
                    val (_, message) = iterator.next()
                    if (message.start + message.duration < now)
                        iterator.remove()
                }
            }

            if (messages.isEmpty())
                return

            val entries = messages.values.sorted()
            val wrappedMessages = entries.associateWith { font.split(it.message, max(width / 2, 200)) }
            val lines = wrappedMessages.values.flatten()
            val spacing = font.lineHeight + 1
            val boxWidth = lines.maxOf { font.width(it) }
            val boxHeight = lines.size * spacing

            val positionMode = NuclearConfig.client.pushNotificationPositionMode.get()
            val offsetX = positionMode.horizontalOffset(width, boxWidth) + NuclearConfig.client.pushNotificationPositionOffsetHorizontal.get().toDouble()
            val offsetY = positionMode.verticalOffset(height, boxHeight) + NuclearConfig.client.pushNotificationPositionOffsetVertical.get().toDouble()

            graphics.pose().pushPose()
            graphics.pose().translate(offsetX, offsetY, 0.0)
            graphics.pose().scale(1F, 1F, 1F)
            graphics.fill(-5, -5, boxWidth + 5, boxHeight + 2, minecraft.options.getBackgroundColor(0.3F))
            RenderSystem.enableBlend()
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)

            var offset = 0
            for ((entry, components) in wrappedMessages) {
                val elapsed = (now - entry.start).toInt()
                val alpha = max(min(510 * (entry.duration - elapsed) / entry.duration, 255), 5)
                for (component in components) {
                    graphics.drawString(font, component, 0, offset, 0xFFFFFF or (alpha shl 24))
                    offset += font.lineHeight + 1
                }
            }

            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            graphics.pose().popPose()
        }

        private data class InfoEntry(val message: Component, val start: Long, val duration: Int) : Comparable<InfoEntry> {
            override fun compareTo(other: InfoEntry) = duration - other.duration
        }
    }
}
