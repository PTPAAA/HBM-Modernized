/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.content.menu.OilWellMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.rendering.renderGuiFluidTank
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.AbstractOilWellBlockEntity
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class OilWellScreen(menu: OilWellMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<OilWellMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/oil_derrick.png")

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(leftPos + 156, topPos + 3, 8, 8, menu))
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

        val oilWell = menu.blockEntity
        val energyScaled = oilWell.energy * 34 / oilWell.maxEnergy
        if (energyScaled > 0) graphics.blit(texture, leftPos + 8, topPos + 51 - energyScaled, 176, 34 - energyScaled, 16, energyScaled)

        val status = oilWell.status
        if (status != AbstractOilWellBlockEntity.STATUS_OK) {
            graphics.blit(texture, leftPos + 35, topPos + 17, 176 + 16 * (status - 1), 52, 16, 16)
        }

        if (oilWell.getTanks() < 3) {
            graphics.blit(texture, leftPos + 34, topPos + 36, 192, 0, 18, 34)
        } else {
            renderGuiFluidTank(graphics, leftPos + 40, topPos + 69, 6, 32, oilWell.tanks[2])
        }

        renderGuiFluidTank(graphics, leftPos + 62, topPos + 69, 16, 52, oilWell.oilTank)
        renderGuiFluidTank(graphics, leftPos + 107, topPos + 69, 16, 52, oilWell.gasTank)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        val oilWell = menu.blockEntity

        tooltipEnergyStorage(graphics, oilWell.energyStorage, 8, 17, 16, 34, mouseX, mouseY)

        if (isHovering(35, 17, 16, 16, mouseX.toDouble(), mouseY.toDouble())) {
            val status = when (oilWell.status) {
                AbstractOilWellBlockEntity.STATUS_OK -> LangKeys.OIL_WELL_STATUS_OK.get()
                AbstractOilWellBlockEntity.STATUS_NO_OIL_SOURCE -> LangKeys.OIL_WELL_STATUS_NO_OIL_SOURCE.get()
                AbstractOilWellBlockEntity.STATUS_ERROR -> LangKeys.OIL_WELL_STATUS_ERROR.get()
                AbstractOilWellBlockEntity.STATUS_OUT_OF_FLUID -> LangKeys.OIL_WELL_STATUS_OUT_OF_FLUID.get()
                AbstractOilWellBlockEntity.STATUS_LOOKING_FOR_OIL -> LangKeys.OIL_WELL_STATUS_LOOKING_FOR_OIL.get()
                AbstractOilWellBlockEntity.STATUS_NO_POWER -> LangKeys.OIL_WELL_STATUS_NO_POWER.get()
                else -> LangKeys.OIL_WELL_STATUS_ERROR.get()
            }
            graphics.renderTooltip(font, listOf(status), java.util.Optional.empty(), mouseX, mouseY)
        }
        tooltipFluidTank(graphics, oilWell.oilTank, 62, 17, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, oilWell.gasTank, 107, 17, 16, 52, mouseX, mouseY)
        if (oilWell.getTanks() > 2) tooltipFluidTank(graphics, oilWell.tanks[2], 40, 37, 6, 32, mouseX, mouseY)
    }
}






