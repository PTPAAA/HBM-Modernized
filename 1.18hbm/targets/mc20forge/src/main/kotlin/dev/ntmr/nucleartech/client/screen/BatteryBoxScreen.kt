package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.BatteryBoxBlockEntity
import dev.ntmr.nucleartech.content.menu.BatteryBoxMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BatteryBoxScreen(
    container: BatteryBoxMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<BatteryBoxMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/battery_box.png")

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

        val blockEntity = menu.blockEntity

        if (blockEntity.energy > 0) {
            val energyScaled = blockEntity.energy * 52 / BatteryBoxBlockEntity.MAX_ENERGY
            // Assuming texture has energy bar at classic location or similar to combustion generator
            // Combustion Generator: x=152, y=69 (bottom), height=52.
            // Let's assume same layout for simplicity or center it if texture is hypothetical.
            // Using standard placement similar to combustion generator for now.
            graphics.blit(texture, leftPos + 152, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 152, 17, 16, 52, mouseX, mouseY)
    }
}
