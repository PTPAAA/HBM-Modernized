/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.rendering.renderGuiFluid
import dev.ntmr.nucleartech.content.block.entity.CombustionGeneratorBlockEntity
import dev.ntmr.nucleartech.content.menu.CombustionGeneratorMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions

class CombustionGeneratorScreen(
    container: CombustionGeneratorMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<CombustionGeneratorMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/combustion_generator.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partials: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val combustionGenerator = menu.blockEntity
        val burnProgress = combustionGenerator.litTime * 14 / combustionGenerator.litDuration.coerceAtLeast(1)
        graphics.blit(texture, leftPos + 82, topPos + 50 - burnProgress, 192, 14 - burnProgress, 14, burnProgress)

        if (combustionGenerator.energy > 0) {
            val energyScaled = combustionGenerator.energy * 52 / CombustionGeneratorBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 152, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }

        if (combustionGenerator.water > 0) {
            val fluidLevelScaled = combustionGenerator.water * 52 / CombustionGeneratorBlockEntity.MAX_WATER
            val ext = IClientFluidTypeExtensions.of(Fluids.WATER)
            renderGuiFluid(graphics, leftPos + 8, topPos + 69, 16, fluidLevelScaled, ext.stillTexture ?: net.minecraft.resources.ResourceLocation("block/water_still"), ext.tintColor)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        if (isHovering(80, 35, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font, listOf(Component.literal("${menu.blockEntity.litTime / 20}s")), java.util.Optional.empty(), mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.tank, 8, 17, 16, 52, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 152, 17, 16, 52, mouseX, mouseY)
    }
}






