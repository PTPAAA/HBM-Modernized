/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.rendering.renderGuiFluidTank
import dev.ntmr.nucleartech.content.block.entity.CentrifugeBlockEntity
import dev.ntmr.nucleartech.content.menu.CentrifugeMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import kotlin.math.min

class CentrifugeScreen(menu: CentrifugeMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<CentrifugeMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/centrifuge.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    override fun init() {
        super.init()
//        addRenderableWidget(UpgradeInfoWidget(leftPos + 105, topPos + 40, 8, 8, menu, this::renderComponentTooltip))
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

        val centrifuge = menu.blockEntity
        val energyScaled = centrifuge.energy * 35 / CentrifugeBlockEntity.MAX_ENERGY
        if (energyScaled > 0) graphics.blit(texture, leftPos + 9, topPos + 48 - energyScaled, 176, 35 - energyScaled, 16, energyScaled)

        if (centrifuge.canProgress) {
            var progress = centrifuge.progress * 145 / centrifuge.maxProgress

            for (i in 0..3) {
                val partHeight = min(progress, 36)
                graphics.blit(texture, leftPos + 65 + i * 20, topPos + 50 - partHeight, 176, 71 - partHeight, 12, partHeight)
                progress -= partHeight
                if (progress <= 0) break
            }
        }

        renderGuiFluidTank(graphics, leftPos + 30, topPos + 20, 16, 50, centrifuge.inputTank)
        renderGuiFluidTank(graphics, leftPos + 130, topPos + 20, 16, 50, centrifuge.outputTank1)
        renderGuiFluidTank(graphics, leftPos + 150, topPos + 20, 16, 50, centrifuge.outputTank2)
    }

    override fun renderLabels(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        graphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 9, 13, 16, 35, mouseX, mouseY)
        
        val centrifuge = menu.blockEntity
        tooltipFluidTank(graphics, centrifuge.inputTank, 30, 20, 16, 50, mouseX, mouseY)
        tooltipFluidTank(graphics, centrifuge.outputTank1, 130, 20, 16, 50, mouseX, mouseY)
        tooltipFluidTank(graphics, centrifuge.outputTank2, 150, 20, 16, 50, mouseX, mouseY)
    }
}






