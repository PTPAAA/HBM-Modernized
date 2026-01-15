package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.extensions.gold
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper

class CorrosiveFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getCorrosionLevel(data: AttachedFluidTrait<*>) = data.tag.getInt("Corrosion")
    fun isStronglyCorrosive(data: AttachedFluidTrait<*>) = getCorrosionLevel(data) > 50

    override fun getName(data: AttachedFluidTrait<*>) = if (isStronglyCorrosive(data)) LangKeys.FLUID_TRAIT_CORROSIVE_STRONG.gold() else super.getName(data)

    override fun loadAdditionalData(id: ResourceLocation, json: JsonObject) = super.loadAdditionalData(id, json).apply {
        putFloat("Corrosion", GsonHelper.getAsFloat(json, "corrosion"))
    }
}
