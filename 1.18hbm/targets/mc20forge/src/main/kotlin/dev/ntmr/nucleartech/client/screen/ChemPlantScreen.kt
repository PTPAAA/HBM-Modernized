/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.rendering.renderGuiFluidTank
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.ChemPlantBlockEntity
import dev.ntmr.nucleartech.content.menu.ChemPlantMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ChemPlantScreen(menu: ChemPlantMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<ChemPlantMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/chem_plant.png")

    init {
        imageWidth = 176
        imageHeight = 222
        inventoryLabelY = imageHeight - 94
    }

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(leftPos + 105, topPos + 40, 8, 8, menu))
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

        val chemPlant = menu.blockEntity
        val energyScaled = chemPlant.energy * 52 / ChemPlantBlockEntity.MAX_ENERGY
        if (energyScaled > 0) graphics.blit(texture, leftPos + 44, topPos + 70 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)

        val progressScaled = chemPlant.progress * 90 / chemPlant.maxProgress.coerceAtLeast(1)
        if (progressScaled > 0) graphics.blit(texture, leftPos + 43, topPos + 89, 0, 222, progressScaled, 18)

        renderGuiFluidTank(graphics, leftPos + 8, topPos + 52, 16, 34, chemPlant.inputTank1)
        renderGuiFluidTank(graphics, leftPos + 26, topPos + 52, 16, 34, chemPlant.inputTank2)
        renderGuiFluidTank(graphics, leftPos + 134, topPos + 52, 16, 34, chemPlant.outputTank1)
        renderGuiFluidTank(graphics, leftPos + 152, topPos + 52, 16, 34, chemPlant.outputTank2)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        val chemPlant = menu.blockEntity
        tooltipEnergyStorage(graphics, chemPlant.energyStorage, 44, 18, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, chemPlant.inputTank1, 8, 18, 16, 34, mouseX, mouseY)
        tooltipFluidTank(graphics, chemPlant.inputTank2, 26, 18, 16, 34, mouseX, mouseY)
        tooltipFluidTank(graphics, chemPlant.outputTank1, 134, 18, 16, 34, mouseX, mouseY)
        tooltipFluidTank(graphics, chemPlant.outputTank2, 152, 18, 16, 34, mouseX, mouseY)
    }
}






