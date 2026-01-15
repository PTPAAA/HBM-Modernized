package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.DieselGeneratorBlockEntity
import dev.ntmr.nucleartech.content.menu.DieselGeneratorMenu
import dev.ntmr.nucleartech.extensions.tooltipEnergyStorage
import dev.ntmr.nucleartech.extensions.tooltipFluidTank
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class DieselGeneratorScreen(
    container: DieselGeneratorMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<DieselGeneratorMenu>(container, playerInventory, title) {
    // Reusing Combustion Generator texture for now as layout is similar (slots at 44,17 and 44,53 and 116,53)
    // Combustion Generator: 
    // Slot 1 (Water): 44, 17
    // Slot 2 (Output): 44, 53
    // Slot 3 (Energy): 116, 53
    // Slot 0 (Fuel): 80, 53 (We don't use this one in Diesel Gen)
    // So we can visually reuse the background but ignore the slot 0 visual if possible, or just accept it's there.
    // Ideally we should have a specific texture, but for now reusing or generic.
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

        val generator = menu.blockEntity
        
        // Fluid Tank Render (at 8, 69? CombustionGenerator uses 8, 69 for water)
        if (generator.tank.fluidAmount > 0) {
            val fluidLevelScaled = generator.tank.fluidAmount * 52 / DieselGeneratorBlockEntity.MAX_FLUID
            // Render fluid at same position as water in combustion generator
            // 8, 17 is top of tank? Combustion gen renders at 8, 69 upwards 16x52?
            // CombustionGeneratorScreen: renderGuiFluid(..., leftPos + 8, topPos + 69, 16, fluidLevelScaled, ...)
            // topPos + 69 is the BOTTOM of the tank.
            
            // We need to get fluid texture from the fluid in tank
            val fluid = generator.tank.fluid.fluid
            val clientExtensions = net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions.of(fluid)
            dev.ntmr.nucleartech.client.rendering.renderGuiFluid(graphics, leftPos + 8, topPos + 69, 16, fluidLevelScaled, clientExtensions.stillTexture ?: net.minecraft.resources.ResourceLocation("block/water_still"), clientExtensions.tintColor)
        }

        // Energy Render
        if (generator.energy > 0) {
            val energyScaled = generator.energy * 52 / DieselGeneratorBlockEntity.MAX_ENERGY
            graphics.blit(texture, leftPos + 152, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }
        
        // TODO: Render burn flame if lit?
        // Reuse flame location: leftPos + 82, topPos + 50 - burnProgress
        // We only have isLit state roughly? Or we can say if energy is increasing?
        // We don't track burnTime in Diesel Generator per tick in the same way (just consumption), 
        // but if we want flame, we can just show it if there is fuel and energy < max?
        if (generator.blockState.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT)) {
             graphics.blit(texture, leftPos + 82, topPos + 50 - 13, 192, 14 - 13, 14, 13) // Constant flame for now
        }
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)
        tooltipFluidTank(graphics, menu.blockEntity.tank, 8, 17, 16, 52, mouseX, mouseY)
        tooltipEnergyStorage(graphics, menu.blockEntity.energyStorage, 152, 17, 16, 52, mouseX, mouseY)
    }
}
