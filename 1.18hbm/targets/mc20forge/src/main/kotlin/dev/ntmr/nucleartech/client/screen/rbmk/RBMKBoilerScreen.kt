/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.rbmk

import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKBoilerMenu
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.NTechFluids
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory

class RBMKBoilerScreen(
    menu: RBMKBoilerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKBoilerMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/boiler.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        if (x.toInt() in leftPos + 33 .. leftPos + 53 && y.toInt() in topPos + 21 .. topPos + 85 && menu.clickMenuButton(minecraft!!.player!!, 0)) {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
            minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, 0)
            return true
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

        val boiler = menu.blockEntity
        val water = boiler.waterTank.fluidAmount * 58 / boiler.waterTank.capacity
        graphics.blit(texture, leftPos + 126, topPos + 82 - water, 176, 58 - water, 14, water)

        var steam = boiler.steamTank.fluidAmount * 22 / boiler.steamTank.capacity
        if (steam > 0) steam++
        if (steam > 22) steam++
        graphics.blit(texture, leftPos + 91, topPos + 65 - steam, 190, 24 - steam, 4, steam)

        val steamType = boiler.steamTank.fluid.rawFluid
        val steamTypeGuiXOffset = when {
            steamType.isSame(NTechFluids.steam.source.get()) -> 194
            steamType.isSame(NTechFluids.steamHot.source.get()) -> 208
            steamType.isSame(NTechFluids.steamSuperHot.source.get()) -> 222
            steamType.isSame(NTechFluids.steamUltraHot.source.get()) -> 236
            else -> 194
        }

        graphics.blit(texture, leftPos + 36, topPos + 24, steamTypeGuiXOffset, 0, 14, 58)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.waterTank, 126, 24, 15, 58, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.steamTank, 89, 39, 8, 28, mouseX, mouseY)
    }
}






