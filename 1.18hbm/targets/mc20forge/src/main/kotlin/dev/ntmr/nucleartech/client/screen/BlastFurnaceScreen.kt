/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.BlastFurnaceBlockEntity
import dev.ntmr.nucleartech.content.menu.BlastFurnaceMenu
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.player.Inventory

class BlastFurnaceScreen(
    container: BlastFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<BlastFurnaceMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/blast_furnace.png")

    init {
        imageWidth = 176
        imageHeight = 166
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
        graphics.blit(texture, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight)

        val blastFurnace = menu.blockEntity
        if (blastFurnace.litTime > 0) {
            val burnTime = blastFurnace.litTime * 52 / BlastFurnaceBlockEntity.MAX_BURN_TIME
            graphics.blit(texture, leftPos + 44, topPos + 70 - burnTime, 201, 53 - burnTime, 16, burnTime)
        }

        val blastProgress = blastFurnace.progress * 24 / BlastFurnaceBlockEntity.MAX_BLAST_TIME
        graphics.blit(texture, leftPos + 101, topPos + 35, 176, 14, blastProgress + 1, 17)

        if (blastFurnace.progress > 0 && blastFurnace.canProgress) {
            graphics.blit(texture, leftPos + 63, topPos + 37, 176, 0, 14, 14)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        if (isHovering(44, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font, listOf(Component.literal("${menu.blockEntity.litTime}/${BlastFurnaceBlockEntity.MAX_BURN_TIME}")), java.util.Optional.empty(), mouseX, mouseY)
    }
}






