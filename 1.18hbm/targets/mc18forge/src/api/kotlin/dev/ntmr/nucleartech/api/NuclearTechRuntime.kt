/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.api

import dev.ntmr.nucleartech.api.fluid.trait.FluidTraitManager
import dev.ntmr.nucleartech.api.world.ChunkRadiationHandler

public interface NuclearTechRuntime {
    public fun getChunkRadiationHandler(): ChunkRadiationHandler
    public fun getFluidTraitManager(): FluidTraitManager
}
