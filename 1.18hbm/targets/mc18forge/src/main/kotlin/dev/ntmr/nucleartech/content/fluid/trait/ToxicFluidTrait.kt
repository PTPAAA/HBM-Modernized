package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper

class ToxicFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getLevel(data: AttachedFluidTrait<*>) = data.tag.getInt("Level")
    fun isWithering(data: AttachedFluidTrait<*>) = data.tag.getBoolean("Wither")

    override fun loadAdditionalData(id: ResourceLocation, json: JsonObject) = super.loadAdditionalData(id, json).apply {
        putInt("Level", GsonHelper.getAsInt(json, "level"))
        putBoolean("Wither", GsonHelper.getAsBoolean(json, "wither"))
    }
}
