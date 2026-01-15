package dev.ntmr.nucleartech.client.rendering

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
import net.minecraftforge.fluids.IFluidTank

// Adapted from Mekanism: https://github.com/mekanism/Mekanism/blob/807a081b149dd258e2d8475b8dfafd8b9bceb01f/src/main/java/mekanism/client/gui/GuiUtils.java#L178
fun renderGuiFluid(
    graphics: net.minecraft.client.gui.GuiGraphics,
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    stillTexture: ResourceLocation,
    tintColor: Int
) {
    val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture)
    RenderSystem.setShader(GameRenderer::getPositionTexShader)
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
    RenderSystem.setShaderTexture(0, sprite.atlasLocation())
    val textureWidth = sprite.contents().width()
    val textureHeight = sprite.contents().height()
    val xTileCount = width / textureWidth
    val xRemainder = width - xTileCount * textureWidth
    val yTileCount = height / textureHeight
    val yRemainder = height - yTileCount * textureHeight
    val uMin = sprite.u0
    val uMax = sprite.u1
    val vMin = sprite.v0
    val vMax = sprite.v1
    val uDif = uMax - uMin
    val vDif = vMax - vMin
    RenderSystem.enableBlend()
    RenderSystem.setShaderColor(
        (tintColor shr 16 and 0xFF) / 255F,
        (tintColor shr 8 and 0xFF) / 255F,
        (tintColor and 0xFF) / 255F,
        (tintColor shr 24 and 0xFF) / 255F
    )
    val vertexBuffer = Tesselator.getInstance().builder
    vertexBuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
    val matrix4f = graphics.pose().last().pose()
    for (xTile in 0..xTileCount) {
        val tileWidth = if (xTile == xTileCount) xRemainder else textureWidth
        if (tileWidth == 0) break
        val tileX = x + xTile * textureWidth
        val maskRight = textureWidth - tileWidth
        val shiftedX = tileX + textureWidth - maskRight
        val uLocalDif = uDif * maskRight / textureWidth
        val uLocalMax = uMax - uLocalDif
        for (yTile in 0..yTileCount) {
            val tileHeight = if (yTile == yTileCount) yRemainder else textureHeight
            if (tileHeight == 0) break
            val tileY = y - (yTile + 1) * textureHeight
            val maskTop = textureHeight - tileHeight
            val vLocalDif = vDif * maskTop / textureHeight
            val vLocalMin = vMin + vLocalDif
            // Z-level usage: matrix4f includes translation from graphics.pose()
            // We use 0 as Z, assuming translation handles layering.
            vertexBuffer.vertex(matrix4f, tileX.toFloat(), (tileY + textureHeight).toFloat(), 0f).uv(uMin, vMax).endVertex()
            vertexBuffer.vertex(matrix4f, shiftedX.toFloat(), (tileY + textureHeight).toFloat(), 0f).uv(uLocalMax, vMax).endVertex()
            vertexBuffer.vertex(matrix4f, shiftedX.toFloat(), (tileY + maskTop).toFloat(), 0f).uv(uLocalMax, vLocalMin).endVertex()
            vertexBuffer.vertex(matrix4f, tileX.toFloat(), (tileY + maskTop).toFloat(), 0f).uv(uMin, vLocalMin).endVertex()
        }
    }
    Tesselator.getInstance().end()
    RenderSystem.disableBlend()
    RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
}

fun renderGuiFluidTank(graphics: net.minecraft.client.gui.GuiGraphics, x: Int, y: Int, width: Int, height: Int, fluidTank: IFluidTank) {
    val fluidStack = fluidTank.fluid
    if (fluidStack.isEmpty) return
    val clientExtensions = IClientFluidTypeExtensions.of(fluidStack.fluid)
    val stillTexture = clientExtensions.getStillTexture(fluidStack) ?: return
    val tintColor = clientExtensions.getTintColor(fluidStack)
    val fluidLevelScaled = fluidTank.fluidAmount * height / fluidTank.capacity
    renderGuiFluid(graphics, x, y, width, fluidLevelScaled, stillTexture, tintColor)
}
