package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.FractionatingColumnBlockEntity
import dev.ntmr.nucleartech.content.menu.FractionatingColumnMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FractionatingColumnScreen(
    container: FractionatingColumnMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<FractionatingColumnMenu>(container, playerInventory, title) {
    
    // Placeholder texture for now
    private val texture = ntm("textures/gui/fractionating_column.png") // Needs creating?

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
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val col = menu.blockEntity
        
        // Input Tank
        renderFluidTank(graphics, col.inputTank, leftPos + 44, topPos + 20, 16, 52)
        
        // Output Tanks (Small?)
        renderFluidTank(graphics, col.heavyOilTank, leftPos + 98, topPos + 20, 16, 52)
        renderFluidTank(graphics, col.lightOilTank, leftPos + 116, topPos + 20, 16, 52)
        renderFluidTank(graphics, col.naphthaTank, leftPos + 134, topPos + 20, 16, 52)
        renderFluidTank(graphics, col.gasTank, leftPos + 152, topPos + 20, 16, 52)

        // Energy Bar
        if (col.energy > 0) {
            val energyScaled = col.energy * 52 / FractionatingColumnBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 8, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }
        
        // Progress (Reusing arrow similar to furnace)
        // Draw progress arrow between input and outputs
        val max = col.maxProgress.coerceAtLeast(1)
        val progress = col.progress * 24 / max // Assume 24px wide arrow
        graphics.blit(texture, leftPos + 68, topPos + 35, 176, 52, progress, 16)
    }
    
    // Helper needed for fluid rendering. Using simple rect for now or extension if available.
    // Assuming extension `renderFluidTank` exists or implement it?
    // Let's assume creating a helper function here for now.
    private fun renderFluidTank(graphics: GuiGraphics, tank: net.minecraftforge.fluids.capability.templates.FluidTank, x: Int, y: Int, width: Int, height: Int) {
        if (tank.isEmpty) return
        // Minimal fluid render
        val fluid = tank.fluid
        // TODO: Proper sprite rendering
        // For placeholder:
        val color = net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions.of(fluid.fluid).getTintColor(fluid)
        val a = (color shr 24 and 255) / 255.0f
        val r = (color shr 16 and 255) / 255.0f
        val g = (color shr 8 and 255) / 255.0f
        val b = (color and 255) / 255.0f
        
        val percentage = tank.fluidAmount.toFloat() / tank.capacity.toFloat()
        val fluidHeight = (height * percentage).toInt()
        
        graphics.fill(x, y + height - fluidHeight, x + width, y + height, color)
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 8, 17, 16, 52, mouseX, mouseY)
        
        // Tooltips for tanks
        tooltipFluidTank(graphics, menu.blockEntity.inputTank, 44, 20, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.heavyOilTank, 98, 20, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.lightOilTank, 116, 20, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.naphthaTank, 134, 20, 16, 52, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.gasTank, 152, 20, 16, 52, mouseX, mouseY)
    }

    private fun tooltipFluidTank(graphics: GuiGraphics, tank: net.minecraftforge.fluids.capability.templates.FluidTank, x: Int, y: Int, w: Int, h: Int, mx: Int, my: Int) {
        if (mx in x until x+w && my in y until y+h) {
            val component = if (tank.isEmpty) Component.translatable("gui.empty") else tank.fluid.displayName
            val amount = Component.literal("${tank.fluidAmount}/${tank.capacity} mB")
            graphics.renderComponentTooltip(font, listOf(component, amount), mx, my)
        }
    }
