package dev.ntmr.nucleartech.extensions

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.fluids.capability.templates.FluidTank

fun AbstractContainerScreen<*>.tooltipEnergyStorage(graphics: GuiGraphics, energyStorage: EnergyStorage, startX: Int, startY: Int, width: Int, height: Int, mouseX: Int, mouseY: Int) {
    val mouseXNormalized = mouseX - guiLeft
    val mouseYNormalized = mouseY - guiTop
    if (mouseXNormalized in startX..startX + width && mouseYNormalized in startY..startY + height) {
        graphics.renderTooltip(minecraft!!.font, energyStorage.getTooltip(), java.util.Optional.empty(), mouseX, mouseY)
    }
}

fun AbstractContainerScreen<*>.tooltipFluidTank(graphics: GuiGraphics, tank: FluidTank, startX: Int, startY: Int, width: Int, height: Int, mouseX: Int, mouseY: Int) {
    val mouseXNormalized = mouseX - guiLeft
    val mouseYNormalized = mouseY - guiTop
    if (mouseXNormalized in startX..startX + width && mouseYNormalized in startY..startY + height) {
        graphics.renderTooltip(minecraft!!.font, tank.getTooltip(), java.util.Optional.empty(), mouseX, mouseY)
    }
}
