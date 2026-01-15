/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.capability

import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.extensions.getOrNull
import dev.ntmr.nucleartech.system.capability.contamination.ContaminationHandler
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent

object Capabilities {
    val CONTAMINATION_CAPABILITY: Capability<ContaminationHandler> = CapabilityManager.get(object : CapabilityToken<ContaminationHandler>() {})

    fun getContamination(entity: LivingEntity) = entity.getCapability(CONTAMINATION_CAPABILITY).getOrNull()

    init {
        NuclearTech.submitListener { event: RegisterCapabilitiesEvent ->
            event.register(ContaminationHandler::class.java)
        }
    }
}
