/*
SPDX-FileCopyrightText: 2022-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.api

import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.resources.ResourceLocation

/**
 * Common interface for all mods that wish to create plugins for NTM.
 *
 * Implementations of this interfaces must be annotated with [NuclearTechPlugin]
 * and have a single constructor with no arguments for NTM to detect them.
 */
interface ModPlugin {
    val id: ResourceLocation

    /**
     * Called when the [runtime] is ready.
     *
     * It is safe to store the [NuclearTechRuntime] instance it in a variable
     * for later use.
     */
    fun onRuntime(runtime: NuclearTechRuntime) {}
}
