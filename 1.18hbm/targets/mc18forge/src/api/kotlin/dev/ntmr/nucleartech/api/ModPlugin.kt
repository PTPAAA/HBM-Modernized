/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.api

import dev.ntmr.nucleartech.api.explosion.ExplosionAlgorithmRegistration
import dev.ntmr.nucleartech.api.hazard.radiation.HazmatRegistry
import net.minecraft.resources.ResourceLocation

/**
 * Common interface for all mods that wish to create plugins for NTM.
 *
 * Implementations of this interfaces must be annotated with [NuclearTechPlugin]
 * and have a single constructor with no arguments for NTM to detect them.
 */
public interface ModPlugin {
    public val id: ResourceLocation

    public fun registerExplosions(explosions: ExplosionAlgorithmRegistration) {}

    public fun registerHazmatValues(hazmat: HazmatRegistry) {}

    /**
     * Called when the [runtime] is ready.
     *
     * It is safe to store the [NuclearTechRuntime] instance it in a variable
     * for later use.
     */
    public fun onRuntime(runtime: NuclearTechRuntime) {}
}
