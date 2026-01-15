/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.content.menu.TurbineMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.rendering.renderGuiFluidTank
import dev.ntmr.nucleartech.content.block.entity.TurbineBlockEntity
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class TurbineScreen(menu: TurbineMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<TurbineMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/turbine.png")

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    @Suppress("DEPRECATION")
    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val turbine = menu.blockEntity
        val energyScaled = turbine.energy * 34 / TurbineBlockEntity.MAX_ENERGY
        if (energyScaled > 0) graphics.blit(texture, leftPos + 123, topPos + 69 - energyScaled, 176, 34 - energyScaled, 7, energyScaled)

        val inputFluid = turbine.inputTank.fluid.rawFluid
        if (inputFluid.`is`(NTechTags.Fluids.STEAM)) graphics.blit(texture, leftPos + 99, topPos + 18, 183, 0, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.HOT_STEAM)) graphics.blit(texture, leftPos + 99, topPos + 18, 183, 14, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.SUPER_HOT_STEAM)) graphics.blit(texture, leftPos + 99, topPos + 18, 183, 28, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.ULTRA_HOT_STEAM)) graphics.blit(texture, leftPos + 99, topPos + 18, 183, 42, 14, 14)

        renderGuiFluidTank(graphics, leftPos + 62, topPos + 69, 16, 52, turbine.inputTank)
        renderGuiFluidTank(graphics, leftPos + 134, topPos + 69, 16, 52, turbine.outputTank)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        val turbine = menu.blockEntity
        tooltipEnergyStorage(graphics, turbine.energyStorage, 123, 35, 7, 34, mouseX, mouseY)
        tooltipFluidTank(graphics, turbine.inputTank, 62, 17, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, turbine.outputTank, 134, 17, 16, 52, mouseX, mouseY)
    }
}






