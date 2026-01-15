/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.rbmk

import dev.ntmr.nucleartech.content.menu.rbmk.RBMKManualControlMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory

class RBMKManualControlScreen(
    menu: RBMKManualControlMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKManualControlMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/manual_control.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        for (i in 0..4) {
            if (x.toInt() in leftPos + 118..leftPos + 148 && y.toInt() in topPos + 26 + i * 11..topPos + 36 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i)
                return true
            }

            if (x.toInt() in leftPos + 28..leftPos + 40 && y.toInt() in topPos + 26 + i * 11..topPos + 36 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i + 5)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i + 5)
                return true
            }
        }

        return super.mouseClicked(x, y, button)
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

        val height = (56 * (1.0 - menu.blockEntity.rodLevel)).toInt()
        if (height > 0) graphics.blit(texture, leftPos + 75, topPos + 29, 176, 56 - height, 8, height)

        val color = menu.blockEntity.color
        if (color != null) graphics.blit(texture, leftPos + 28, topPos + 26 + color.ordinal * 11, 184, color.ordinal * 10, 12, 10)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        if (isHovering(71, 29, 16, 56, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font, listOf(Component.literal("${(menu.blockEntity.rodLevel * 100).toInt()}%")), java.util.Optional.empty(), mouseX, mouseY)
    }
}






