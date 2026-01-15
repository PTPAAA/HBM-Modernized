/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.ElectricFurnaceBlockEntity
import dev.ntmr.nucleartech.content.menu.ElectricFurnaceMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ElectricFurnaceScreen(
    container: ElectricFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ElectricFurnaceMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/electric_furnace.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(leftPos + 151, topPos + 19, 8, 8, menu))
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
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val electricFurnace = menu.blockEntity
        if (electricFurnace.canProgress) {
            graphics.blit(texture, leftPos + 56, topPos + 35, 176, 0, 14, 14)
        }

        val cookingProgressScaled = electricFurnace.progress * 22 / electricFurnace.maxProgress.coerceAtLeast(1)
        graphics.blit(texture, leftPos + 80, topPos + 35, 177, 17, cookingProgressScaled, 16)

        if (electricFurnace.energy > 0) {
            val energyScaled = electricFurnace.energy * 52 / ElectricFurnaceBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 20, 17, 16, 52, mouseX, mouseY)
    }
}






