/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.widgets

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.gray
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.BlockEntity

class UpgradeInfoWidget<T>(
    x: Int, y: Int, xSize: Int, ySize: Int,
    val menu: NTechContainerMenu<T>
) : AbstractWidget(x, y, xSize, ySize, Component.empty())
    where T : BlockEntity,
          T : UpgradeableMachine
{
    private val texture = ntm("textures/gui/widgets.png")

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partials: Float) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, alpha)
        RenderSystem.enableBlend()
        graphics.blit(texture, x, y, 0, 32, 8, 8)
        RenderSystem.defaultBlendFunc()
        RenderSystem.enableDepthTest()

        if (isHoveredOrFocused)
            renderToolTip(graphics, mouseX, mouseY)
    }

    fun renderToolTip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        val tooltips = buildList<Component> {
            add(LangKeys.UPGRADE_INFO_ACCEPTED_UPGRADES.get())
            for (upgrade in menu.blockEntity.getSupportedUpgrades()) {
                if (upgrade.tier == 0) add(Component.literal(" - ").gray().append(upgrade.getName())) 
                else add(Component.literal(" - ").gray().append(upgrade.getName().append(": ")).append(LangKeys.UPGRADE_INFO_STACKS_TO.format(upgrade.tier).gray()))
            }
        }
        val font = net.minecraft.client.Minecraft.getInstance().font
        graphics.renderTooltip(font, tooltips, java.util.Optional.empty(), mouseX, mouseY)
    }

    override fun updateWidgetNarration(narration: NarrationElementOutput) {
        // TODO
    }
}
