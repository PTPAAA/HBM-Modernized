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
import com.mojang.blaze3d.vertex.PoseStack
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.level.block.entity.BlockEntity

class UpgradeInfoWidget<T>(
    x: Int, y: Int, xSize: Int, ySize: Int,
    val menu: NTechContainerMenu<T>,
    val renderTooltipFunc: (PoseStack, List<Component>, Int, Int) -> Unit
) : AbstractWidget(x, y, xSize, ySize, TextComponent.EMPTY)
    where T : BlockEntity,
          T : UpgradeableMachine
{
    private val texture = ntm("textures/gui/widgets.png")

    override fun renderButton(matrix: PoseStack, mouseX: Int, mouseY: Int, partials: Float) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1F, 1F, 1F, alpha)
        RenderSystem.enableBlend()
        blit(matrix, x, y, 0, 32, 8, 8)
        RenderSystem.defaultBlendFunc()
        RenderSystem.enableDepthTest()

        if (isHoveredOrFocused)
            renderToolTip(matrix, mouseX, mouseY)
    }

    override fun renderToolTip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        val tooltips = buildList<Component> {
            add(LangKeys.UPGRADE_INFO_ACCEPTED_UPGRADES.get())
            for (upgrade in menu.blockEntity.getSupportedUpgrades()) {
                if (upgrade.tier == 0) add(TextComponent(" • ").gray().append(upgrade.getName()))
                else add(TextComponent(" • ").gray().append(upgrade.getName().append(": ")).append(LangKeys.UPGRADE_INFO_STACKS_TO.format(upgrade.tier).gray()))
            }
        }
        renderTooltipFunc(matrix, tooltips, mouseX, mouseY)
    }

    override fun updateNarration(narration: NarrationElementOutput) {
        // TODO
    }
}
