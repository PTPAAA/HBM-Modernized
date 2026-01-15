/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.menu.LittleBoyMenu
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class LittleBoyScreen(
    container: LittleBoyMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<LittleBoyMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/little_boy.png")

    init {
        imageWidth = 176
        imageHeight = 222
        inventoryLabelY = imageHeight - 94
    }

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
        if (bombCompletion == 0) return
        if (bombCompletion shr 5 and 1 == 1) graphics.blit(texture, leftPos + 142, topPos + 90, 176, 0, 16, 16)
        if (bombCompletion shr 4 and 1 == 1) graphics.blit(texture, leftPos + 27, topPos + 87, 176, 16, 21, 22)
        if (bombCompletion shr 3 and 1 == 1) graphics.blit(texture, leftPos + 27, topPos + 89, 176, 38, 21, 18)
        if (bombCompletion shr 2 and 1 == 1) graphics.blit(texture, leftPos + 74, topPos + 94, 176, 57, 19, 8)
        if (bombCompletion shr 1 and 1 == 1) graphics.blit(texture, leftPos + 92, topPos + 95, 176, 66, 12, 6)
        if (bombCompletion and 1 == 1) graphics.blit(texture, leftPos + 107, topPos + 91, 176, 75, 16, 14)
    }
}






