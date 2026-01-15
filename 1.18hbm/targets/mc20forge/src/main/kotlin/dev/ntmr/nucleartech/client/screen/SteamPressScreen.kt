/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import dev.ntmr.nucleartech.content.menu.PressMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.SteamPressBlockEntity
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class SteamPressScreen(
    container: PressMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<PressMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/steam_press.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight)

        val press = menu.blockEntity
        val powerPos = press.power * 12 / SteamPressBlockEntity.MAX_POWER
        graphics.blit(texture, leftPos + 25, topPos + 16, 176, 14 + 18 * powerPos, 18, 18)

        val burnProgress = press.litTime * 13 / press.litDuration.coerceAtLeast(1)
        graphics.blit(texture, leftPos + 27, topPos + 49 - burnProgress, 176, 13 - burnProgress, 13, burnProgress)

        if (press.progress > 0) {
            val pressProgress = press.progress * 16 / SteamPressBlockEntity.PRESS_TIME
            graphics.blit(texture, leftPos + 79, topPos + 35, 194, 0, 18, pressProgress)
        }
    }
}






