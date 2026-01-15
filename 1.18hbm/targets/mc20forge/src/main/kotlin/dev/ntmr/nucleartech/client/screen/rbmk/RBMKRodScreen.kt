/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.rbmk

import dev.ntmr.nucleartech.content.menu.rbmk.RBMKRodMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.item.RBMKRodItem
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RBMKRodScreen(
    menu: RBMKRodMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKRodMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/rod.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val rod = menu.blockEntity
        val rodStack = rod.getItem(0)
        if (rodStack.item is RBMKRodItem) {
            graphics.blit(texture, leftPos + 34, topPos + 21, 176, 0, 18, 67)

            val depletion = 1.0 - RBMKRodItem.getEnrichment(rodStack)
            graphics.blit(texture, leftPos + 34, topPos + 21, 194, 0, 18, (depletion * 67).toInt())

            val xenon = RBMKRodItem.getPoisonLevel(rodStack)
            val xenonScaled = (xenon * 58).toInt()
            graphics.blit(texture, leftPos + 126, topPos + 82 - xenonScaled, 212, 58 - xenonScaled, 14, xenonScaled)
        }
    }
}






