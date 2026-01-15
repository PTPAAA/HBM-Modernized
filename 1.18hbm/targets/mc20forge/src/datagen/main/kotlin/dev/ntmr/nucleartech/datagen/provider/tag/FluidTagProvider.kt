package dev.ntmr.nucleartech.datagen.provider.tag

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.content.NTechFluids
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.FluidTagsProvider
import net.minecraft.tags.FluidTags
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class FluidTagProvider(output: PackOutput, lookupProvider: CompletableFuture<HolderLookup.Provider>, existingFileHelper: ExistingFileHelper) : FluidTagsProvider(output, lookupProvider) {
    override fun getName(): String = "Nuclear Tech Mod Fluid Tags"

    override fun addTags(provider: HolderLookup.Provider) {
        tag(FluidTags.LAVA).addTag(NTechTags.Fluids.VOLCANIC_LAVA)

        tag(NTechTags.Fluids.CORIUM).add(NTechFluids.corium.source, NTechFluids.corium.flowing)
        tag(NTechTags.Fluids.CRUDE_OIL).add(NTechFluids.oil.source, NTechFluids.oil.flowing)
        tag(NTechTags.Fluids.GAS).addTag(NTechTags.Fluids.NATURAL_GAS)
        tag(NTechTags.Fluids.HOT_STEAM).add(NTechFluids.steamHot.source, NTechFluids.steamHot.flowing)
        tag(NTechTags.Fluids.NATURAL_GAS).add(NTechFluids.gas.source, NTechFluids.gas.flowing)
        tag(NTechTags.Fluids.OIL).addTag(NTechTags.Fluids.CRUDE_OIL)
        tag(NTechTags.Fluids.SPENT_STEAM).add(NTechFluids.spentSteam.source, NTechFluids.spentSteam.flowing)
        tag(NTechTags.Fluids.STEAM).add(NTechFluids.steam.source, NTechFluids.steam.flowing)
        tag(NTechTags.Fluids.SUPER_HOT_STEAM).add(NTechFluids.steamSuperHot.source, NTechFluids.steamSuperHot.flowing)
        tag(NTechTags.Fluids.ULTRA_HOT_STEAM).add(NTechFluids.steamUltraHot.source, NTechFluids.steamUltraHot.flowing)
        tag(NTechTags.Fluids.URANIUM_HEXAFLUORIDE).add(NTechFluids.uraniumHexafluoride.source, NTechFluids.uraniumHexafluoride.flowing)
        tag(NTechTags.Fluids.VOLCANIC_LAVA).add(NTechFluids.volcanicLava.source, NTechFluids.volcanicLava.flowing)
    }
}
