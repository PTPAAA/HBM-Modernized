/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.entity.NuclearCreeper
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.EntityAttributeModificationEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechAttributes : NTechRegistry<Attribute> {
    override val forgeRegistry: DeferredRegister<Attribute> = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID)

    val RADIATION_RESISTANCE = register("generic.radiation_resistance") { RangedAttribute(ntmAttributeName("generic.radiation_resistance"), 0.0, 0.0, 10.0) }

    private fun ntmAttributeName(name: String) = "nucleartech.attribute.name.$name"

    init {
        NuclearTech.submitListener { event: EntityAttributeCreationEvent ->
            event.put(NTechEntities.nuclearCreeper.get(), NuclearCreeper.createAttributes())
        }
        NuclearTech.submitListener { event: EntityAttributeModificationEvent ->
            for (type in event.types) {
                event.add(type, RADIATION_RESISTANCE.get())
            }
        }
    }
}
