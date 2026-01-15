package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.ElectricPressBlockEntity
import dev.ntmr.nucleartech.content.menu.ElectricPressMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ElectricPressScreen(
    container: ElectricPressMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ElectricPressMenu>(container, playerInventory, title) {
    
    // Reusing Steam Press texture if available or generic.
    // Ideally we want electric texture. 
    // Using generic machine texture or assume electric_press.png exists/created.
    private val texture = ntm("textures/gui/electric_press.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }
    
    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(leftPos + 152, topPos + 17, 16, 64, menu))
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

        val press = menu.blockEntity
        
        // Progress Arrow
        // Input 56, 17 -> Output 116, 35
        // Approx progress at 80, 35
        val max = press.maxProgress.coerceAtLeast(1)
        val progress = press.progress * 22 / max
        graphics.blit(texture, leftPos + 79, topPos + 35, 176, 0, progress, 16) // Reusing common arrow coords (176,0)

        // Energy Bar
        if (press.energy > 0) {
            val energyScaled = press.energy * 52 / ElectricPressBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 20, 17, 16, 52, mouseX, mouseY)
    }
}
