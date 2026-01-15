/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.extensions.green
import dev.ntmr.nucleartech.extensions.red
import dev.ntmr.nucleartech.system.world.ChunkPollution
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack

class PollutingFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getPollutionSpilled(data: AttachedFluidTrait<*>) = ChunkPollution.PollutionData(data.tag.getCompound("PollutionSpilled"))
    fun getPollutionBurned(data: AttachedFluidTrait<*>) = ChunkPollution.PollutionData(data.tag.getCompound("PollutionBurned"))

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait<*>, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(level, fluid, data, tooltip, flag)

        if (flag.isAdvanced) {

            val spillData = getPollutionSpilled(data)
            if (!spillData.isZeroed) {
                tooltip += LangKeys.FLUID_TRAIT_POLLUTING_SPILLED.green()
                for ((type, amount) in spillData.toMap()) {
                    if (amount == 0F)
                        continue
                    tooltip += TextComponent(" - ").append(typeToTranslationKey(type).format(String.format(Minecraft.getInstance().languageManager.selected.javaLocale, "%.2f", amount * 1000F))).green()
                }
            }

            val burnData = getPollutionBurned(data)
            if (!burnData.isZeroed) {
                tooltip += LangKeys.FLUID_TRAIT_POLLUTING_BURNED.red()
                for ((type, amount) in burnData.toMap()) {
                    if (amount == 0F)
                        continue
                    tooltip += TextComponent(" - ").append(typeToTranslationKey(type).format(String.format(Minecraft.getInstance().languageManager.selected.javaLocale, "%.2f", amount * 1000F))).red()
                }
            }
        }
    }

    private fun typeToTranslationKey(type: ChunkPollution.PollutionType) = when (type) {
        ChunkPollution.PollutionType.Soot -> LangKeys.FLUID_TRAIT_POLLUTING_TYPE_SOOT
        ChunkPollution.PollutionType.Poison -> LangKeys.FLUID_TRAIT_POLLUTING_TYPE_POISON
        ChunkPollution.PollutionType.HeavyMetal -> LangKeys.FLUID_TRAIT_POLLUTING_TYPE_HEAVY_METAL
    }

    override fun releaseFluidInWorld(level: Level, pos: BlockPos, fluid: FluidStack, releaseType: FluidTrait.FluidReleaseType, data: AttachedFluidTrait<*>) {
        super.releaseFluidInWorld(level, pos, fluid, releaseType, data)
        when (releaseType) {
            FluidTrait.FluidReleaseType.Spill -> ChunkPollution.addPollution(level, pos, getPollutionSpilled(data) * fluid.amount.toFloat())
            FluidTrait.FluidReleaseType.Burn -> ChunkPollution.addPollution(level, pos, getPollutionBurned(data) * fluid.amount.toFloat())
            FluidTrait.FluidReleaseType.Void -> {}
        }
    }

    override fun loadAdditionalData(id: ResourceLocation, json: JsonObject) = super.loadAdditionalData(id, json).apply {
        val spilled = GsonHelper.getAsJsonObject(json, "spilled", null)
        val burned = GsonHelper.getAsJsonObject(json, "burned", null)

        if (spilled != null)
            put("PollutionSpilled", pollutionDataFromJson(spilled).getSaveTag())

        if (burned != null)
            put("PollutionBurned", pollutionDataFromJson(burned).getSaveTag())

        if (burned == null && spilled == null) {
            throw JsonParseException("No pollution data defined for pollution fluid trait attachment $id")
        }
    }

    private fun pollutionDataFromJson(json: JsonObject) = ChunkPollution.PollutionData(
        soot = GsonHelper.getAsFloat(json, "soot"),
        poison = GsonHelper.getAsFloat(json, "poison"),
        heavyMetal = GsonHelper.getAsFloat(json, "heavy_metal"),
    )
}
