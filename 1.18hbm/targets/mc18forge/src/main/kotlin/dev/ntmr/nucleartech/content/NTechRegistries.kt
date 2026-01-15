/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.NTechWorldFeatures.MeteoritePlacerType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegistryBuilder

object NTechRegistries {
    private val registries = listOf<NTechRegistry<*>>(
        NTechBlocks,
        NTechFluids,
        NTechFluidTraits,
        NTechBlockItems,
        NTechItems,
        NTechBlockEntities,
        NTechEntities,
        NTechMenus,
        NTechRecipeSerializers,
        NTechAttributes,
        NTechWorldFeatures.MeteoritePlacerType.Registry,
        NTechWorldFeatures.Features,
        NTechSounds,
        NTechParticles,
    )

    // Initialization of non-registry objects
    init {
        NTechArmorMaterials
        NTechRecipeTypes
    }

    val FLUID_TRAITS = NTechFluidTraits.forgeRegistry.makeRegistry(FluidTrait::class.java) { RegistryBuilder<FluidTrait>().disableSaving() }
    val METEORITE_PLACERS = NTechWorldFeatures.MeteoritePlacerType.forgeRegistry.makeRegistry(MeteoritePlacerType::class.java) { RegistryBuilder<MeteoritePlacerType<*>>().disableSaving() }

    fun register(bus: IEventBus) {
        registries.forEach { it.forgeRegistry.register(bus) }
    }
}
