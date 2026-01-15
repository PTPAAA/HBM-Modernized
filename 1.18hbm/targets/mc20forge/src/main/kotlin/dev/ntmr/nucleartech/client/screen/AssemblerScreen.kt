/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
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
import net.minecraft.network.chat.MutableComponent
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
        // UpgradeInfoWidget needs update to handle GuiGraphics, passing a dummy or changing constructor to not need this callback if possible, or adapting
        addRenderableWidget(UpgradeInfoWidget(leftPos + 141, topPos + 40, 8, 8, menu))
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val assembler = menu.blockEntity
        val energyScaled = assembler.energy * 52 / AssemblerBlockEntity.MAX_ENERGY
        if (energyScaled > 0) graphics.blit(texture, leftPos + 116, topPos + 70 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)

        val progressScaled = assembler.progress * 83 / assembler.maxProgress.coerceAtLeast(1)
        if (progressScaled > 0) graphics.blit(texture, leftPos + 45, topPos + 82, 2, 222, progressScaled, 32)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        if (isHovering(116, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font,
                listOf(
                    LangKeys.ENERGY.get(),
                    Component.literal("${EnergyUnit.UnitType.HBM.getPreferredUnit(menu.blockEntity.energy).format(menu.blockEntity.energy, false)}/${EnergyUnit.UnitType.HBM.getPreferredUnit(AssemblerBlockEntity.MAX_ENERGY).format(AssemblerBlockEntity.MAX_ENERGY)}")
                ), java.util.Optional.empty(), mouseX, mouseY
            )
    }
}






