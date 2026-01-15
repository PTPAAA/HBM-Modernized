/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.system.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack

class RadioactiveFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getRadiation(data: AttachedFluidTrait<*>) = data.tag.getFloat("Radiation")

    override fun releaseFluidInWorld(level: Level, pos: BlockPos, fluid: FluidStack, releaseType: FluidTrait.FluidReleaseType, data: AttachedFluidTrait<*>) {
        super.releaseFluidInWorld(level, pos, fluid, releaseType, data)
        ChunkRadiation.incrementRadiation(level, pos, fluid.amount * getRadiation(data))
    }

    override fun loadAdditionalData(id: ResourceLocation, json: JsonObject) = super.loadAdditionalData(id, json).apply {
        putFloat("Radiation", GsonHelper.getAsFloat(json, "radiation"))
    }
}
