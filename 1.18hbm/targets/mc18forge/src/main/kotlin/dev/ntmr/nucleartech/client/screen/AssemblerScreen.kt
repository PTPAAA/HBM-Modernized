/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.AssemblerBlockEntity
import dev.ntmr.nucleartech.content.menu.AssemblerMenu
import dev.ntmr.nucleartech.math.format
import dev.ntmr.nucleartech.math.getPreferredUnit
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.system.energy.EnergyUnit
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory

class AssemblerScreen(
    container: AssemblerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<AssemblerMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/assembler.png")

    init {
        imageWidth = 176
        imageHeight = 222
        inventoryLabelY = imageHeight - 94
    }

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(guiLeft + 141, guiTop + 40, 8, 8, menu, this::renderComponentTooltip))
    }

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(stack)
        super.render(stack, mouseX, mouseY, partialTicks)
        renderTooltip(stack, mouseX, mouseY)
    }

    override fun renderBg(stack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        val assembler = menu.blockEntity
        val energyScaled = assembler.energy * 52 / AssemblerBlockEntity.MAX_ENERGY
        if (energyScaled > 0) blit(stack, guiLeft + 116, guiTop + 70 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)

        val progressScaled = assembler.progress * 83 / assembler.maxProgress.coerceAtLeast(1)
        if (progressScaled > 0) blit(stack, guiLeft + 45, guiTop + 82, 2, 222, progressScaled, 32)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(116, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix,
                listOf(
                    LangKeys.ENERGY.get(),
                    TextComponent("${EnergyUnit.UnitType.HBM.getPreferredUnit(menu.blockEntity.energy).format(menu.blockEntity.energy, false)}/${EnergyUnit.UnitType.HBM.getPreferredUnit(AssemblerBlockEntity.MAX_ENERGY).format(AssemblerBlockEntity.MAX_ENERGY)}")
                ), mouseX, mouseY, font
            )
    }
}
