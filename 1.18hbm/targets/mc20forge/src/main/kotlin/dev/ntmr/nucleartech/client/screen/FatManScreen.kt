/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.menu.FatManMenu
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FatManScreen(
    container: FatManMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<FatManMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/fat_man.png")

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val bombCompletion = menu.getBombCompletion()
        if (bombCompletion shr 5 and 1 == 1) graphics.blit(texture, leftPos + 82, topPos + 19, 176, 0, 24, 24)
        if (bombCompletion shr 4 and 1 == 1) graphics.blit(texture, leftPos + 106, topPos + 19, 200, 0, 24, 24)
        if (bombCompletion shr 3 and 1 == 1) graphics.blit(texture, leftPos + 82, topPos + 43, 176, 24, 24, 24)
        if (bombCompletion shr 2 and 1 == 1) graphics.blit(texture, leftPos + 106, topPos + 43, 200, 24, 24, 24)
        if (bombCompletion shr 6 and 1 == 1) graphics.blit(texture, leftPos + 134, topPos + 35, 176, 48, 16, 16)
    }
}






