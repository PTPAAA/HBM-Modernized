package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.client.screen.widgets.UpgradeInfoWidget
import dev.ntmr.nucleartech.content.block.entity.ElectricFurnaceBlockEntity
import dev.ntmr.nucleartech.content.menu.ElectricFurnaceMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ElectricFurnaceScreen(
    container: ElectricFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ElectricFurnaceMenu>(container, playerInventory, title) {
    // We need a new texture effectively. 
    // Since I can't generate a real image, I will use the standard one and accept some visual misalignment if I can't overlay everything.
    // Or I can use generating tool? The user prompt said: "Use generate_image tool ... NO". I have generate_image tool.
    // But I can't use it to make a resource pack file easily accessible to the game logic without user restart/setup?
    // Actually, I can just write the file if I had the bytes.
    // Ideally I just use a placeholder or draw on top of blank.
    // For now, I'll stick to code-level changes.
    private val texture = ntm("textures/gui/electric_furnace.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun init() {
        super.init()
        // Adjust widget pos
        addRenderableWidget(UpgradeInfoWidget(leftPos + 152, topPos + 17, 16, 64, menu)) // Covering upgrade slots roughly
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

        val furnace = menu.blockEntity
        
        // Render Progress Arrow 1 (Input 46,17 -> Output 106,35)
        // Middle approx x=70?
        // Let's place arrow at x=80, y=35 (Standard)
        // With dual, maybe two arrows?
        // Arrow 1: x=66, y=35 ?
        // Arrow 2: x=86, y=35 ?
        
        val max1 = furnace.maxProgress1.coerceAtLeast(1)
        val progress1 = furnace.progress1 * 22 / max1
        graphics.blit(texture, leftPos + 66, topPos + 35, 177, 17, progress1, 16) // Reusing texture arrow
        
        val max2 = furnace.maxProgress2.coerceAtLeast(1)
        val progress2 = furnace.progress2 * 22 / max2
        graphics.blit(texture, leftPos + 88, topPos + 35, 177, 17, progress2, 16)

        // Energy Bar
        if (furnace.energy > 0) {
            val energyScaled = furnace.energy * 52 / ElectricFurnaceBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 20, 17, 16, 52, mouseX, mouseY)
    }
}
