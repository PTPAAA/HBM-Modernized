/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import dev.ntmr.nucleartech.content.menu.SirenMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.item.SirenTrackItem
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.IItemHandler

class SirenScreen(
    container: SirenMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<SirenMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/siren.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)
    }

    override fun renderLabels(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderLabels(graphics, mouseX, mouseY)

        val invMaybe = menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER)
        val inv: IItemHandler
        if (!invMaybe.isPresent) return
        else inv = invMaybe.orElseThrow(::Error)
        val sirenTrack = inv.getStackInSlot(0).item
        if (sirenTrack is SirenTrackItem) {
            val trackName = sirenTrack.trackName
            val trackType = sirenTrack.trackType
            val trackRange = sirenTrack.trackRange
            val color = sirenTrack.color
            val textAreaMiddleX = 106F
            val textAreaStartY = 22F
            graphics.drawString(font, trackName, (textAreaMiddleX - font.width(trackName) * .5F).toInt(), (textAreaStartY + 5F).toInt(), color)
            graphics.drawString(font, trackType, (textAreaMiddleX - font.width(trackType) * .5F).toInt(), (textAreaStartY + 15F).toInt(), color)
            graphics.drawString(font, trackRange, (textAreaMiddleX - font.width(trackRange) * .5F).toInt(), (textAreaStartY + 25F).toInt(), color)
        }
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }
}






