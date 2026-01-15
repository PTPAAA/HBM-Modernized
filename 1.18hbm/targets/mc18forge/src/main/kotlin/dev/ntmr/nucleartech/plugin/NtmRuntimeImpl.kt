package dev.ntmr.nucleartech.plugin

import dev.ntmr.nucleartech.api.NuclearTechRuntime
import dev.ntmr.nucleartech.content.fluid.trait.FluidTraitManager
import dev.ntmr.nucleartech.system.world.ChunkRadiation

class NtmRuntimeImpl : NuclearTechRuntime {
    override fun getChunkRadiationHandler() = ChunkRadiation
    override fun getFluidTraitManager() = FluidTraitManager
}
