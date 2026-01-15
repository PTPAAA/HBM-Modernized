/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.content.menu.ShredderMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.ShredderBlockEntity
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ShredderScreen(
    container: ShredderMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ShredderMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/shredder.png")

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

        val shredder = menu.blockEntity
        val shreddingProgressScaled = shredder.progress * 33 / ShredderBlockEntity.SHREDDING_TIME
        if (shreddingProgressScaled > 0) {
            graphics.blit(texture, leftPos + 64, topPos + 90, 177, 54, shreddingProgressScaled, 13)
        }

        val energyScaled = shredder.energy * 88 / ShredderBlockEntity.MAX_ENERGY
        if (energyScaled > 0) {
            graphics.blit(texture, leftPos + 8, topPos + 106 - energyScaled, 176, 160 - energyScaled, 16, energyScaled)
        }

        val leftBladeState = menu.getLeftBladeState()
        if (leftBladeState != 0) graphics.blit(texture, leftPos + 43, topPos + 71, 176, leftBladeState * 18 - 18, 18, 18)

        val rightBladeState = menu.getRightBladeState()
        if (rightBladeState != 0) graphics.blit(texture, leftPos + 79, topPos + 71, 194, rightBladeState * 18 - 18, 18, 18)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 8, 16, 16, 88, mouseX, mouseY)
    }
}






