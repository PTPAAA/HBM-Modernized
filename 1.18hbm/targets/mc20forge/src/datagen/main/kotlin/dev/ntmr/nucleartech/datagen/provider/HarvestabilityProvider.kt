package dev.ntmr.nucleartech.datagen.provider

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.datagen.CommonBlocks
import dev.ntmr.nucleartech.datagen.provider.tag.add
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper

class HarvestabilityProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(dataGenerator, MODID, existingFileHelper) {
    override fun getName() = "Nuclear Tech Mod Block Harvestability"

    override fun addTags() {
        for (definition in CommonBlocks.data) {
            for (toolType in definition.toolTypes)
                tag(toolType).add(definition.block)
            if (definition.toolLevel != null)
                tag(definition.toolLevel).add(definition.block)
        }
    }
}
