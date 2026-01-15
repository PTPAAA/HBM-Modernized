package dev.ntmr.nucleartech.datagen.provider.tag

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.content.NTechBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraftforge.common.data.BlockTagsProvider
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture
import dev.ntmr.nucleartech.NTechTags.Blocks as NTTags

class BlockTagProvider(output: PackOutput, lookupProvider: CompletableFuture<HolderLookup.Provider>, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(output, lookupProvider, MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block Tags"

    fun getBlockTags(): CompletableFuture<TagLookup<Block>> = this.contentsGetter()

    override fun addTags(provider: HolderLookup.Provider) {
        oreTags()
        storageBlockTags()
        miscTags()
    }

    private fun oreTags() {
        tag(Tags.Blocks.ORES)
            .addTags(NTTags.ORES_ALUMINIUM, NTTags.ORES_ASBESTOS, NTTags.ORES_AUSTRALIUM, NTTags.ORES_BERYLLIUM, NTTags.ORES_COBALT, NTTags.ORES_DAFFERGON, NTTags.ORES_FLUORITE, NTTags.ORES_LEAD, NTTags.ORES_LIGNITE, NTTags.ORES_NITER, NTTags.ORES_OIL, NTTags.ORES_PHOSPHORUS, NTTags.ORES_PLUTONIUM, NTTags.ORES_RARE_EARTH, NTTags.ORES_REIIUM, NTTags.ORES_SCHRABIDIUM, NTTags.ORES_STARMETAL, NTTags.ORES_SULFUR, NTTags.ORES_THORIUM, NTTags.ORES_TITANIUM, NTTags.ORES_TRINITITE, NTTags.ORES_TRIXITE, NTTags.ORES_TUNGSTEN, NTTags.ORES_UNOBTAINIUM, NTTags.ORES_URANIUM, NTTags.ORES_VERTICIUM, NTTags.ORES_VOLCANIC_GEM, NTTags.ORES_WEIDANIUM)
            .add(NTechBlocks.meteorUraniumOre, NTechBlocks.meteorThoriumOre, NTechBlocks.meteorTitaniumOre, NTechBlocks.meteorSulfurOre, NTechBlocks.meteorCopperOre, NTechBlocks.meteorTungstenOre, NTechBlocks.meteorAluminiumOre, NTechBlocks.meteorLeadOre, NTechBlocks.meteorLithiumOre)

        tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(NTechBlocks.aluminiumOre, NTechBlocks.asbestosOre, NTechBlocks.australianOre, NTechBlocks.berylliumOre, NTechBlocks.cobaltOre, NTechBlocks.dellite, NTechBlocks.fluoriteOre, NTechBlocks.leadOre, NTechBlocks.ligniteOre, NTechBlocks.niterOre, NTechBlocks.oilDeposit, NTechBlocks.rareEarthOre, NTechBlocks.reiite, NTechBlocks.schrabidiumOre, NTechBlocks.sulfurOre, NTechBlocks.thoriumOre, NTechBlocks.titaniumOre, NTechBlocks.tungstenOre, NTechBlocks.brightblendeOre, NTechBlocks.uraniumOre, NTechBlocks.scorchedUraniumOre, NTechBlocks.dollarGreenMineral, NTechBlocks.weidite)
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(NTechBlocks.deepslateAluminiumOre, NTechBlocks.deepslateAsbestosOre, NTechBlocks.deepslateAustralianOre, NTechBlocks.deepslateBerylliumOre, NTechBlocks.deepslateCobaltOre, NTechBlocks.deepslateFluoriteOre, NTechBlocks.deepslateLeadOre, NTechBlocks.deepslateNiterOre, NTechBlocks.deepslateOilDeposit, NTechBlocks.deepslateRareEarthOre, NTechBlocks.deepslateSchrabidiumOre, NTechBlocks.deepslateSulfurOre, NTechBlocks.deepslateThoriumOre, NTechBlocks.deepslateTitaniumOre, NTechBlocks.deepslateTungstenOre, NTechBlocks.deepslateUraniumOre, NTechBlocks.scorchedDeepslateUraniumOre)
        tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(NTechBlocks.netherPhosphorusOre, NTechBlocks.netherPlutoniumOre, NTechBlocks.netherSchrabidiumOre, NTechBlocks.netherSulfurOre, NTechBlocks.netherTungstenOre, NTechBlocks.netherUraniumOre, NTechBlocks.scorchedNetherUraniumOre)
        tag(NTTags.ORES_IN_GROUND_BASALT).add(NTechBlocks.basaltSulfurOre, NTechBlocks.basaltFluoriteOre, NTechBlocks.basaltAsbestosOre, NTechBlocks.basaltVolcanicGemOre)
        tag(NTTags.ORES_IN_GROUND_END_STONE).add(NTechBlocks.trixite)

        tag(NTTags.ORES_ALUMINIUM).add(NTechBlocks.aluminiumOre, NTechBlocks.deepslateAluminiumOre)
        tag(NTTags.ORES_ASBESTOS).add(NTechBlocks.asbestosOre, NTechBlocks.deepslateAsbestosOre, NTechBlocks.basaltAsbestosOre)
        tag(NTTags.ORES_AUSTRALIUM).add(NTechBlocks.australianOre, NTechBlocks.deepslateAustralianOre)
        tag(NTTags.ORES_BERYLLIUM).add(NTechBlocks.berylliumOre, NTechBlocks.deepslateBerylliumOre)
        tag(NTTags.ORES_COBALT).add(NTechBlocks.cobaltOre, NTechBlocks.deepslateCobaltOre)
        tag(NTTags.ORES_DAFFERGON).add(NTechBlocks.dellite)
        tag(NTTags.ORES_FLUORITE).add(NTechBlocks.fluoriteOre, NTechBlocks.deepslateFluoriteOre, NTechBlocks.basaltFluoriteOre)
        tag(NTTags.ORES_LEAD).add(NTechBlocks.leadOre, NTechBlocks.deepslateLeadOre)
        tag(NTTags.ORES_LIGNITE).add(NTechBlocks.ligniteOre)
        tag(NTTags.ORES_NITER).add(NTechBlocks.niterOre, NTechBlocks.deepslateNiterOre)
        tag(NTTags.ORES_OIL).add(NTechBlocks.oilDeposit, NTechBlocks.deepslateOilDeposit)
        tag(NTTags.ORES_PHOSPHORUS).add(NTechBlocks.netherPhosphorusOre)
        tag(NTTags.ORES_PLUTONIUM).add(NTechBlocks.netherPlutoniumOre)
        tag(NTTags.ORES_RARE_EARTH).add(NTechBlocks.rareEarthOre, NTechBlocks.deepslateRareEarthOre)
        tag(NTTags.ORES_REIIUM).add(NTechBlocks.reiite)
        tag(NTTags.ORES_SCHRABIDIUM).add(NTechBlocks.schrabidiumOre, NTechBlocks.deepslateSchrabidiumOre, NTechBlocks.netherSchrabidiumOre)
        tag(NTTags.ORES_STARMETAL).add(NTechBlocks.starmetalOre)
        tag(NTTags.ORES_SULFUR).add(NTechBlocks.sulfurOre, NTechBlocks.deepslateSulfurOre, NTechBlocks.netherSulfurOre, NTechBlocks.basaltSulfurOre)
        tag(NTTags.ORES_THORIUM).add(NTechBlocks.thoriumOre, NTechBlocks.deepslateThoriumOre)
        tag(NTTags.ORES_TITANIUM).add(NTechBlocks.titaniumOre, NTechBlocks.deepslateTitaniumOre)
        tag(NTTags.ORES_TRINITITE).add(NTechBlocks.trinitite, NTechBlocks.redTrinitite)
        tag(NTTags.ORES_TRIXITE).add(NTechBlocks.trixite)
        tag(NTTags.ORES_TUNGSTEN).add(NTechBlocks.tungstenOre, NTechBlocks.deepslateTungstenOre, NTechBlocks.netherTungstenOre)
        tag(NTTags.ORES_UNOBTAINIUM).add(NTechBlocks.brightblendeOre)
        tag(NTTags.ORES_URANIUM).add(NTechBlocks.uraniumOre, NTechBlocks.scorchedUraniumOre, NTechBlocks.deepslateUraniumOre, NTechBlocks.scorchedDeepslateUraniumOre, NTechBlocks.netherUraniumOre, NTechBlocks.scorchedNetherUraniumOre)
        tag(NTTags.ORES_VERTICIUM).add(NTechBlocks.dollarGreenMineral)
        tag(NTTags.ORES_VOLCANIC_GEM).add(NTechBlocks.basaltVolcanicGemOre)
        tag(NTTags.ORES_WEIDANIUM).add(NTechBlocks.weidite)
    }

    private fun storageBlockTags() {
        tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTags(NTTags.STORAGE_BLOCKS_ALUMINIUM, NTTags.STORAGE_BLOCKS_ASBESTOS, NTTags.STORAGE_BLOCKS_AUSTRALIUM, NTTags.STORAGE_BLOCKS_BERYLLIUM, NTTags.STORAGE_BLOCKS_COBALT, NTTags.STORAGE_BLOCKS_COMBINE_STEEL, NTTags.STORAGE_BLOCKS_DAFFERGON, NTTags.STORAGE_BLOCKS_DESH, NTTags.STORAGE_BLOCKS_ELECTRICAL_SCRAP, NTTags.STORAGE_BLOCKS_EUPHEMIUM, NTTags.STORAGE_BLOCKS_FIBERGLASS, NTTags.STORAGE_BLOCKS_FLUORITE, NTTags.STORAGE_BLOCKS_HAZMAT, NTTags.STORAGE_BLOCKS_INSULATOR, NTTags.STORAGE_BLOCKS_LEAD, NTTags.STORAGE_BLOCKS_LITHIUM, NTTags.STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, NTTags.STORAGE_BLOCKS_MOX, NTTags.STORAGE_BLOCKS_NEPTUNIUM, NTTags.STORAGE_BLOCKS_NITER, NTTags.STORAGE_BLOCKS_NUCLEAR_WASTE, NTTags.STORAGE_BLOCKS_PHOSPHORUS, NTTags.STORAGE_BLOCKS_PLUTONIUM, NTTags.STORAGE_BLOCKS_PLUTONIUM_FUEL, NTTags.STORAGE_BLOCKS_RED_COPPER, NTTags.STORAGE_BLOCKS_REIIUM, NTTags.STORAGE_BLOCKS_SCHRABIDIUM, NTTags.STORAGE_BLOCKS_SCHRABIDIUM_FUEL, NTTags.STORAGE_BLOCKS_SCRAP, NTTags.STORAGE_BLOCKS_SOLINIUM, NTTags.STORAGE_BLOCKS_STARMETAL, NTTags.STORAGE_BLOCKS_STEEL, NTTags.STORAGE_BLOCKS_SULFUR, NTTags.STORAGE_BLOCKS_THORIUM, NTTags.STORAGE_BLOCKS_THORIUM_FUEL, NTTags.STORAGE_BLOCKS_TITANIUM, NTTags.STORAGE_BLOCKS_TRINITITE, NTTags.STORAGE_BLOCKS_TUNGSTEN, NTTags.STORAGE_BLOCKS_UNOBTAINIUM, NTTags.STORAGE_BLOCKS_URANIUM, NTTags.STORAGE_BLOCKS_URANIUM_FUEL, NTTags.STORAGE_BLOCKS_VERTICIUM, NTTags.STORAGE_BLOCKS_WEIDANIUM, NTTags.STORAGE_BLOCKS_YELLOWCAKE)
            .add(NTechBlocks.advancedAlloyBlock)

        tag(NTTags.STORAGE_BLOCKS_ALUMINIUM).add(NTechBlocks.aluminiumBlock)
        tag(NTTags.STORAGE_BLOCKS_ASBESTOS).add(NTechBlocks.asbestosBlock)
        tag(NTTags.STORAGE_BLOCKS_AUSTRALIUM).add(NTechBlocks.australiumBlock)
        tag(NTTags.STORAGE_BLOCKS_BERYLLIUM).add(NTechBlocks.berylliumBlock)
        tag(NTTags.STORAGE_BLOCKS_COBALT).add(NTechBlocks.cobaltBlock)
        tag(NTTags.STORAGE_BLOCKS_COMBINE_STEEL).add(NTechBlocks.combineSteelBlock)
        tag(NTTags.STORAGE_BLOCKS_DAFFERGON).add(NTechBlocks.daffergonBlock)
        tag(NTTags.STORAGE_BLOCKS_DESH).add(NTechBlocks.deshReinforcedBlock)
        tag(NTTags.STORAGE_BLOCKS_ELECTRICAL_SCRAP).add(NTechBlocks.electricalScrapBlock)
        tag(NTTags.STORAGE_BLOCKS_EUPHEMIUM).add(NTechBlocks.euphemiumBlock)
        tag(NTTags.STORAGE_BLOCKS_FIBERGLASS).add(NTechBlocks.fiberglassRoll)
        tag(NTTags.STORAGE_BLOCKS_FLUORITE).add(NTechBlocks.fluoriteBlock)
        tag(NTTags.STORAGE_BLOCKS_HAZMAT).add(NTechBlocks.hazmatBlock)
        tag(NTTags.STORAGE_BLOCKS_INSULATOR).add(NTechBlocks.insulatorRoll)
        tag(NTTags.STORAGE_BLOCKS_LEAD).add(NTechBlocks.leadBlock)
        tag(NTTags.STORAGE_BLOCKS_LITHIUM).add(NTechBlocks.lithiumBlock)
        tag(NTTags.STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN).add(NTechBlocks.magnetizedTungstenBlock)
        tag(NTTags.STORAGE_BLOCKS_MOX).add(NTechBlocks.moxFuelBlock)
        tag(NTTags.STORAGE_BLOCKS_NEPTUNIUM).add(NTechBlocks.neptuniumBlock)
        tag(NTTags.STORAGE_BLOCKS_NITER).add(NTechBlocks.niterBlock)
        tag(NTTags.STORAGE_BLOCKS_NUCLEAR_WASTE).add(NTechBlocks.nuclearWasteBlock)
        tag(NTTags.STORAGE_BLOCKS_PHOSPHORUS).add(NTechBlocks.whitePhosphorusBlock, NTechBlocks.redPhosphorusBlock)
        tag(NTTags.STORAGE_BLOCKS_PLUTONIUM).add(NTechBlocks.plutoniumBlock)
        tag(NTTags.STORAGE_BLOCKS_PLUTONIUM_FUEL).add(NTechBlocks.plutoniumFuelBlock)
        tag(NTTags.STORAGE_BLOCKS_RED_COPPER).add(NTechBlocks.redCopperBlock)
        tag(NTTags.STORAGE_BLOCKS_REIIUM).add(NTechBlocks.reiiumBlock)
        tag(NTTags.STORAGE_BLOCKS_SCHRABIDIUM).add(NTechBlocks.schrabidiumBlock)
        tag(NTTags.STORAGE_BLOCKS_SCHRABIDIUM_FUEL).add(NTechBlocks.schrabidiumFuelBlock)
        tag(NTTags.STORAGE_BLOCKS_SCRAP).add(NTechBlocks.scrapBlock)
        tag(NTTags.STORAGE_BLOCKS_SOLINIUM).add(NTechBlocks.soliniumBlock)
        tag(NTTags.STORAGE_BLOCKS_STARMETAL).add(NTechBlocks.starmetalBlock)
        tag(NTTags.STORAGE_BLOCKS_STEEL).add(NTechBlocks.steelBlock)
        tag(NTTags.STORAGE_BLOCKS_SULFUR).add(NTechBlocks.sulfurBlock)
        tag(NTTags.STORAGE_BLOCKS_THORIUM).add(NTechBlocks.thoriumBlock)
        tag(NTTags.STORAGE_BLOCKS_THORIUM_FUEL).add(NTechBlocks.thoriumFuelBlock)
        tag(NTTags.STORAGE_BLOCKS_TITANIUM).add(NTechBlocks.titaniumBlock)
        tag(NTTags.STORAGE_BLOCKS_TRINITITE).add(NTechBlocks.trinititeBlock)
        tag(NTTags.STORAGE_BLOCKS_TUNGSTEN).add(NTechBlocks.tungstenBlock)
        tag(NTTags.STORAGE_BLOCKS_UNOBTAINIUM).add(NTechBlocks.unobtainiumBlock)
        tag(NTTags.STORAGE_BLOCKS_URANIUM).add(NTechBlocks.uraniumBlock)
        tag(NTTags.STORAGE_BLOCKS_URANIUM_FUEL).add(NTechBlocks.uraniumFuelBlock)
        tag(NTTags.STORAGE_BLOCKS_VERTICIUM).add(NTechBlocks.verticiumBlock)
        tag(NTTags.STORAGE_BLOCKS_WEIDANIUM).add(NTechBlocks.weidaniumBlock)
        tag(NTTags.STORAGE_BLOCKS_YELLOWCAKE).add(NTechBlocks.yellowcakeBlock)
    }

    private fun miscTags() {
        tag(Tags.Blocks.SAND).addTag(NTTags.SAND_OIL)
        tag(NTTags.SAND_OIL).add(NTechBlocks.oilSand)
        tag(NTTags.GLOWING_MUSHROOM_GROW_BLOCK).add(NTechBlocks.deadGrass, NTechBlocks.glowingMycelium)
        tag(NTTags.GLOWING_MYCELIUM_SPREADABLE).addTag(BlockTags.DIRT).add(NTechBlocks.deadGrass.get(), Blocks.DIRT_PATH)
        tag(NTTags.ANVIL).add(NTechBlocks.ironAnvil, NTechBlocks.leadAnvil, NTechBlocks.steelAnvil, NTechBlocks.meteoriteAnvil, NTechBlocks.starmetalAnvil, NTechBlocks.ferrouraniumAnvil, NTechBlocks.bismuthAnvil, NTechBlocks.schrabidateAnvil, NTechBlocks.dineutroniumAnvil, NTechBlocks.murkyAnvil)
    }
}
