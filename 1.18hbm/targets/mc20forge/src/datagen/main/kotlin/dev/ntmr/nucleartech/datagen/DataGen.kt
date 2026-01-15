package dev.ntmr.nucleartech.datagen

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.datagen.provider.FalloutTransformationsProvider
import dev.ntmr.nucleartech.datagen.provider.FluidTraitProvider
import dev.ntmr.nucleartech.datagen.provider.HarvestabilityProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearBlockStateProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearItemModelProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearLanguageProviders
import dev.ntmr.nucleartech.datagen.provider.NuclearLootProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearModelProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearParticleProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearRecipeProvider
import dev.ntmr.nucleartech.datagen.provider.NuclearSoundsProvider
import dev.ntmr.nucleartech.datagen.provider.tag.BlockTagProvider
import dev.ntmr.nucleartech.datagen.provider.tag.FluidTagProvider
import dev.ntmr.nucleartech.datagen.provider.tag.ItemTagProvider
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Suppress("unused")
object DataGen {
    @SubscribeEvent
    @JvmStatic
    fun generateData(event: GatherDataEvent) {
        val dataGenerator = event.generator
        val existingFileHelper = event.existingFileHelper
        if (event.includeServer()) {
            val blockTagProvider = BlockTagProvider(dataGenerator, existingFileHelper)
            dataGenerator.addProvider(blockTagProvider)
            dataGenerator.addProvider(ItemTagProvider(dataGenerator, blockTagProvider, existingFileHelper))
            dataGenerator.addProvider(FluidTagProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(FluidTraitProvider(dataGenerator))
            dataGenerator.addProvider(HarvestabilityProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearRecipeProvider(dataGenerator))
            dataGenerator.addProvider(NuclearLootProvider(dataGenerator))
            dataGenerator.addProvider(FalloutTransformationsProvider(dataGenerator))
        }

        if (event.includeClient()) {
            dataGenerator.addProvider(NuclearBlockStateProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearItemModelProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearModelProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearSoundsProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearParticleProvider(dataGenerator, existingFileHelper))

            for (translation in NuclearLanguageProviders.getLanguageProviders(dataGenerator))
                dataGenerator.addProvider(translation)
        }
    }
}
