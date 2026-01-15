/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.extensions.red
import dev.ntmr.nucleartech.math.format
import dev.ntmr.nucleartech.math.getPreferredUnit
import dev.ntmr.nucleartech.system.heat.HeatUnit
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraftforge.fluids.FluidStack

class FlammableFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getHeatEnergy(data: AttachedFluidTrait<*>) = data.tag.getInt("HeatEnergy")

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait<*>, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(level, fluid, data, tooltip, flag)
        if (flag.isAdvanced) {
            val energy = getHeatEnergy(data)
            if (energy > 0) {
                tooltip += LangKeys.FLUID_TRAIT_ENERGY_INFO.format(Component.literal(HeatUnit.UnitType.HBM.getPreferredUnit(energy).format(energy)).red()).withStyle(styleModifier)
            }
        }
    }

    override fun loadAdditionalData(id: ResourceLocation, json: JsonObject) = super.loadAdditionalData(id, json).apply {
        putInt("HeatEnergy", GsonHelper.getAsInt(json, "heat_energy"))
    }
}
