/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.fluid.trait.*
import dev.ntmr.nucleartech.ntm
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Style
import net.minecraftforge.registries.DeferredRegister

object NTechFluidTraits : NTechRegistry<FluidTrait> {
    override val forgeRegistry: DeferredRegister<FluidTrait> = DeferredRegister.create(ntm("fluid_trait"), MODID)

    val liquid = register("liquid") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val gaseous = register("gaseous") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val gaseousRoomTemp = register("gaseous_at_room_temperature") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val viscous = register("viscous") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val plasma = register("plasma") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)) }
    val antimatter = register("antimatter") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)) }
    val delicious = register("delicious") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.DARK_GREEN)) }
    val combustible = register("combustible") { CombustibleFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.GOLD)) }
    val coolable = register("coolable") { CoolableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.AQUA)) }
    val corrosive = register("corrosive") { CorrosiveFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val flammable = register("flammable") { FlammableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val heatable = register("heatable") { HeatableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.AQUA)) }
    val polluting = register("polluting") { PollutingFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.GOLD)) }
    val radioactive = register("radioactive") { RadioactiveFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val toxic = register("toxic") { ToxicFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.GREEN)) }
}
