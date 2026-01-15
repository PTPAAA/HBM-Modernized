package dev.ntmr.nucleartech.client.rendering

import dev.ntmr.nucleartech.ntm
import net.minecraft.client.model.geom.ModelLayerLocation

object NuclearModelLayers {
    val METEOR = createLocation("meteor")
    val RUBBLE = createLocation("rubble")
    val SHRAPNEL = createLocation("shrapnel")
    val STEAM_PRESS = createLocation("steam_press")

    private fun createLocation(name: String, sub: String = "main") = ModelLayerLocation(ntm(name), sub)
}
