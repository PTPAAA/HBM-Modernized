package dev.ntmr.nucleartech.extensions

import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.renderer.texture.TextureAtlasSprite

import net.minecraft.util.FastColor

fun TextureAtlasSprite.getAverageColor(frame: Int) = getAverageColor(frame, 0, 0, contents().width(), contents().height())

fun TextureAtlasSprite.getAverageColor(frame: Int, u0: Int, v0: Int, u1: Int, v1: Int): Int {
    require(u0 <= u1 && v0 <= v1) { "Illegal UV coordinates: u0=$u0 v0=$v0 u1=$u1 v1=$v1" }

    var alphaSum = 0
    var redSum = 0
    var greenSum = 0
    var blueSum = 0

    val width = contents().width()
    val height = contents().height()
    val image = contents().originalImage

    for (u in u0..u1) for (v in v0..v1) {
        val pixel = image.getPixelRGBA(u, v + frame * height)
        // NativeImage is ABGR (Little Endian)
        alphaSum += (pixel shr 24) and 0xFF
        blueSum += (pixel shr 16) and 0xFF
        greenSum += (pixel shr 8) and 0xFF
        redSum += (pixel shr 0) and 0xFF
    }

    val pixelCount = (u1 - u0 + 1) * (v1 - v0 + 1)

    // Combine back to ABGR
    val a = alphaSum / pixelCount
    val b = blueSum / pixelCount
    val g = greenSum / pixelCount
    val r = redSum / pixelCount
    
    return (a shl 24) or (b shl 16) or (g shl 8) or (r shl 0)
}
