/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.block.*
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPart
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPort
import dev.ntmr.nucleartech.content.block.rbmk.*
import dev.ntmr.nucleartech.content.NTechRegistry
import dev.ntmr.nucleartech.system.hazard.HazardRegistry
import net.minecraft.core.BlockPos
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.MapColor.*
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import kotlin.random.Random

@Suppress("unused")
object NTechBlocks : NTechRegistry<Block> {
    override val forgeRegistry: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)

    val uraniumOre = register("uranium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val scorchedUraniumOre = register("scorched_uranium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val thoriumOre = register("thorium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val titaniumOre = register("titanium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val sulfurOre = register("sulfur_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val niterOre = register("niter_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val tungstenOre = register("tungsten_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F, 10F).requiresCorrectToolForDrops()) }
    val aluminiumOre = register("aluminium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(4F, 8F).requiresCorrectToolForDrops()) }
    val fluoriteOre = register("fluorite_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(2, 3)) }
    val berylliumOre = register("beryllium_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 15F).requiresCorrectToolForDrops()) }
    val leadOre = register("lead_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val oilDeposit = register("oil_deposit") { Block(Properties.of().mapColor(MapColor.STONE).strength(1F, 2F).requiresCorrectToolForDrops()) }
    val emptyOilDeposit = register("empty_oil_deposit") { Block(Properties.of().mapColor(MapColor.STONE).strength(1F, 1F).requiresCorrectToolForDrops()) }
    val oilSand = register("oil_sand") { Block(Properties.of().mapColor(MapColor.SAND).strength(1F).sound(SoundType.SAND)) }
    val ligniteOre = register("lignite_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F).requiresCorrectToolForDrops(), UniformInt.of(0, 1)) }
    val asbestosOre = register("asbestos_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F).requiresCorrectToolForDrops()) }
    val schrabidiumOre = register("schrabidium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(20F, 50F).lightLevel { 3 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val australianOre = register("australian_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val weidite = register("weidite") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val reiite = register("reiite") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val brightblendeOre = register("brightblende_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dellite = register("dellite") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dollarGreenMineral = register("dollar_green_mineral") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(6F).requiresCorrectToolForDrops()) }
    val rareEarthOre = register("rare_earth_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(4F, 3F).requiresCorrectToolForDrops(), UniformInt.of(3, 6)) }
    val cobaltOre = register("cobalt_ore") { DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).strength(3F).requiresCorrectToolForDrops()) }
    val deepslateUraniumOre = register("deepslate_uranium_ore") { DropExperienceBlock(Properties.copy(uraniumOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val scorchedDeepslateUraniumOre = register("scorched_deepslate_uranium_ore") { DropExperienceBlock(Properties.copy(scorchedUraniumOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateThoriumOre = register("deepslate_thorium_ore") { DropExperienceBlock(Properties.copy(thoriumOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateTitaniumOre = register("deepslate_titanium_ore") { DropExperienceBlock(Properties.copy(titaniumOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateSulfurOre = register("deepslate_sulfur_ore") { DropExperienceBlock(Properties.copy(sulfurOre.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateNiterOre = register("deepslate_niter_ore") { DropExperienceBlock(Properties.copy(niterOre.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateTungstenOre = register("deepslate_tungsten_ore") { DropExperienceBlock(Properties.copy(tungstenOre.get()).mapColor(MapColor.DEEPSLATE).strength(6F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateAluminiumOre = register("deepslate_aluminium_ore") { DropExperienceBlock(Properties.copy(aluminiumOre.get()).mapColor(MapColor.DEEPSLATE).strength(6F, 8F).sound(SoundType.DEEPSLATE)) }
    val deepslateFluoriteOre = register("deepslate_fluorite_ore") { DropExperienceBlock(Properties.copy(fluoriteOre.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 3)) }
    val deepslateBerylliumOre = register("deepslate_beryllium_ore") { DropExperienceBlock(Properties.copy(berylliumOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 15F).sound(SoundType.DEEPSLATE)) }
    val deepslateLeadOre = register("deepslate_lead_ore") { DropExperienceBlock(Properties.copy(leadOre.get()).mapColor(MapColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateOilDeposit = register("deepslate_oil_deposit") { Block(Properties.copy(oilDeposit.get()).mapColor(MapColor.DEEPSLATE).strength(2F).sound(SoundType.DEEPSLATE)) }
    val emptyDeepslateOilDeposit = register("empty_deepslate_oil_deposit") { Block(Properties.copy(emptyOilDeposit.get()).mapColor(MapColor.DEEPSLATE).strength(2F, 1F).sound(SoundType.DEEPSLATE)) }
    val deepslateAsbestosOre = register("deepslate_asbestos_ore") { DropExperienceBlock(Properties.copy(asbestosOre.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val deepslateSchrabidiumOre = register("deepslate_schrabidium_ore") { Block(Properties.copy(schrabidiumOre.get()).mapColor(MapColor.DEEPSLATE).strength(30F, 50F).sound(SoundType.DEEPSLATE)) }
    val deepslateAustralianOre = register("deepslate_australian_ore") { DropExperienceBlock(Properties.copy(australianOre.get()).mapColor(MapColor.DEEPSLATE).strength(9F, 6F).sound(SoundType.DEEPSLATE)) }
    val deepslateRareEarthOre = register("deepslate_rare_earth_ore") { DropExperienceBlock(Properties.copy(rareEarthOre.get()).mapColor(MapColor.DEEPSLATE).strength(6F, 3F).sound(SoundType.DEEPSLATE), UniformInt.of(3, 6)) }
    val deepslateCobaltOre = register("deepslate_cobalt_ore") { DropExperienceBlock(Properties.copy(cobaltOre.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val netherUraniumOre = register("nether_uranium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(3f).requiresCorrectToolForDrops()) }
    val scorchedNetherUraniumOre = register("scorched_nether_uranium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(3F).requiresCorrectToolForDrops()) }
    val netherPlutoniumOre = register("nether_plutonium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(3f).requiresCorrectToolForDrops()) }
    val netherTungstenOre = register("nether_tungsten_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(3f, 4f).requiresCorrectToolForDrops()) }
    val netherSulfurOre: RegistryObject<Block> = register("nether_sulfur_ore") { object : Block(Properties.of().mapColor(MapColor.STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState, world: LevelReader, randomSource: net.minecraft.util.RandomSource, pos: BlockPos, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 4)
    }}
    val netherPhosphorusOre: RegistryObject<Block> = register("nether_phosphorus_ore") { object : Block(Properties.of().mapColor(MapColor.STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState, world: LevelReader, randomSource: net.minecraft.util.RandomSource, pos: BlockPos, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 5)
    }}
    val netherSchrabidiumOre = register("nether_schrabidium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(20f, 50f).requiresCorrectToolForDrops().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val meteorUraniumOre = register("meteor_uranium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorThoriumOre = register("meteor_thorium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorTitaniumOre = register("meteor_titanium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorSulfurOre: RegistryObject<Block> = register("meteor_sulfur_ore") { object : Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState, world: LevelReader, randomSource: net.minecraft.util.RandomSource, pos: BlockPos, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(5, 9)
    }}
    val meteorCopperOre = register("meteor_copper_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorTungstenOre = register("meteor_tungsten_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorAluminiumOre = register("meteor_aluminium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorLeadOre = register("meteor_lead_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorLithiumOre = register("meteor_lithium_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val starmetalOre = register("starmetal_ore") { Block(Properties.of().mapColor(MapColor.STONE).strength(10F, 100F).requiresCorrectToolForDrops()) }
    val trixite = register("trixite") { Block(Properties.of().mapColor(MapColor.STONE).strength(4f, 9f).requiresCorrectToolForDrops()) }
    val basaltSulfurOre = register("basalt_sulfur_ore") { RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) }
    val basaltFluoriteOre = register("basalt_fluorite_ore") { RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) }
    val basaltAsbestosOre = register("basalt_asbestos_ore") { RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) } // TODO asbestos particles
    val basaltVolcanicGemOre = register("basalt_volcanic_gem_ore") { VolcanicGemDropExperienceBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(2F, 5F).sound(SoundType.BASALT)) }

    val uraniumBlock = register("uranium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U) }
    val u233Block = register("u233_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U233) }
    val u235Block = register("u235_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U235) }
    val u238Block = register("u238_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U238) }
    val uraniumFuelBlock = register("uranium_fuel_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.UF) }
    val neptuniumBlock = register("neptunium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Np237) }
    val moxFuelBlock = register("mox_fuel_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.MOX) }
    val plutoniumBlock = register("plutonium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu) }
    val pu238Block = register("pu238_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu238) }
    val pu239Block = register("pu239_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu239) }
    val pu240Block = register("pu240_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu240) }
    val plutoniumFuelBlock = register("plutonium_fuel_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.PuF) }
    val thoriumBlock = register("thorium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Th232) }
    val thoriumFuelBlock = register("thorium_fuel_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.ThF) }
    val titaniumBlock = register("titanium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val sulfurBlock = register("sulfur_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val niterBlock = register("niter_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperBlock = register("red_copper_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val advancedAlloyBlock = register("advanced_alloy_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenBlock = register("tungsten_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumBlock = register("aluminium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val fluoriteBlock = register("fluorite_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumBlock = register("beryllium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val cobaltBlock = register("cobalt_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelBlock = register("steel_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadBlock = register("lead_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val lithiumBlock = register("lithium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val whitePhosphorusBlock = register("white_phosphorus_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redPhosphorusBlock = register("red_phosphorus_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val yellowcakeBlock = register("yellowcake_block") { Block(Properties.of().mapColor(MapColor.CLAY).strength(6f).sound(SoundType.SAND)) }
    val scrapBlock = register("scrap_block") { Block(Properties.of().mapColor(MapColor.CLAY).strength(1f).sound(SoundType.GRAVEL)) }
    val electricalScrapBlock = register("electrical_scrap_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val insulatorRoll = register("insulator_roll") { RotatedPillarBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).sound(SoundType.METAL)) }
    val fiberglassRoll = register("fiberglass_roll") { RotatedPillarBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).sound(SoundType.METAL)) }
    val asbestosBlock = register("asbestos_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val trinititeBlock = register("trinitite_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Trinitite) }
    val nuclearWasteBlock = register("nuclear_waste_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Waste) }
    val schrabidiumBlock = register("schrabidium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Sa326) }
    val soliniumBlock = register("solinium_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Sa327) }
    val schrabidiumFuelBlock = register("schrabidium_fuel_block") { HazardBlock(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.SaF) }
    val euphemiumBlock = register("euphemium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumCluster = register("schrabidium_cluster") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val euphemiumEtchedSchrabidiumCluster = register("euphemium_etched_schrabidium_cluster") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val magnetizedTungstenBlock = register("magnetized_tungsten_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val combineSteelBlock = register("combine_steel_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val deshReinforcedBlock = register("desh_reinforced_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val starmetalBlock = register("starmetal_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val australiumBlock = register("australium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val weidaniumBlock = register("weidanium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val reiiumBlock = register("reiium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val unobtainiumBlock = register("unobtainium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val daffergonBlock = register("daffergon_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val verticiumBlock = register("verticium_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val titaniumDecoBlock = register("titanium_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperDecoBlock = register("red_copper_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenDecoBlock = register("tungsten_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumDecoBlock = register("aluminium_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelDecoBlock = register("steel_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadDecoBlock = register("lead_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumDecoBlock = register("beryllium_deco_block") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val asbestosRoof = register("asbestos_roof") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val hazmatBlock = register("hazmat_block") { Block(Properties.of().mapColor(MapColor.WOOL).strength(6f).sound(SoundType.WOOL)) }

    val meteorite = register("meteorite") { Block(Properties.of().mapColor(MapColor.STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val meteoriteCobblestone = register("meteorite_cobblestone") { Block(Properties.of().mapColor(MapColor.STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val brokenMeteorite = register("broken_meteorite") { Block(Properties.of().mapColor(MapColor.STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val hotMeteoriteCobblestone = register("hot_meteorite_cobblestone") { MoltenMeteorBlock(Properties.of().mapColor(MapColor.STONE).strength(15F, 900F).randomTicks().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.STONE)) }
    val meteoriteTreasure = register("meteorite_treasure") { Block(Properties.of().mapColor(MapColor.STONE).strength(15F, 900F).sound(SoundType.STONE)) }

    val steelBeam = register("steel_beam") { SteelBeamBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 15F).sound(SoundType.METAL)) }
    val steelScaffold = register("steel_scaffold") { SteelScaffoldBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 15F).sound(SoundType.METAL)) }
    val steelGrate = register("steel_grate") { SteelGrate(Properties.of().mapColor(MapColor.METAL).strength(5F, 15F).sound(SoundType.METAL).noOcclusion()) }

    val glowingMushroom = register("glowing_mushroom") { GlowingMushroomBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().randomTicks().instabreak().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.GRASS)) }
    val glowingMushroomBlock = register("glowing_mushroom_block") { HugeMushroomBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(.2F).lightLevel { 15 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val glowingMushroomStem = register("glowing_mushroom_stem") { HugeMushroomBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(.2F).lightLevel { 12 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val deadGrass = register("dead_grass") { DeadGrassBlock(Properties.of().mapColor(MapColor.COLOR_BROWN).strength(.6F).randomTicks().sound(SoundType.GRASS)) }
    val glowingMycelium = register("glowing_mycelium") { GlowingMyceliumBlock(Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(.6F).randomTicks().lightLevel { 15 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.GRASS)) }
    val trinitite = register("trinitite_ore") { TrinititeBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val redTrinitite = register("red_trinitite_ore") { TrinititeBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val charredLog = register("charred_log") { RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(5F, 2.5F).sound(SoundType.WOOD)) }
    val charredPlanks = register("charred_planks") { Block(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(.5F, 2.5F).sound(SoundType.WOOD)) }

    val slakedSellafite = register("slaked_sellafite") { Block(Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)) }
    val sellafite = register("sellafite") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.05F) }
    val hotSellafite = register("hot_sellafite") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.1F) }
    val boilingSellafite = register("boiling_sellafite") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.25F) }
    val blazingSellafite = register("blazing_sellafite") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.4F) }
    val infernalSellafite = register("infernal_sellafite") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.6F) }
    val sellafiteCorium = register("sellafite_corium") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(10F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(1F) }

    val corium = register("corium") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(100F, 6000F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(10F) }
    val corebblestone = register("corebblestone") { HazardBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(100F, 6000F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(8F) } // TODO emit radon

    // Machines

    val siren = register("siren") { SirenBlock(Properties.of().mapColor(MapColor.METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val safe = register("safe") { SafeBlock(Properties.of().mapColor(MapColor.METAL).strength(25f, 1200f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val steamPressBase = register("steam_press_base") { SteamPressBaseBlock(Properties.of().mapColor(MapColor.METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressFrame = register("steam_press_frame") { SteamPressFrameBlock(Properties.of().mapColor(MapColor.METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressTop = register("steam_press_top") { SteamPressTopBlock(Properties.of().mapColor(MapColor.METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val blastFurnace = register("blast_furnace") { BlastFurnaceBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val combustionGenerator = register("combustion_generator") { CombustionGeneratorBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val electricFurnace = register("electric_furnace") { ElectricFurnaceBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val shredder = register("shredder") { ShredderBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val assembler = register("assembler") { AssemblerBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val chemPlant = register("chem_plant") { ChemPlantBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val turbine = register("turbine") { TurbineBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 10F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val smallCoolingTower = register("small_cooling_tower") { SmallCoolingTowerBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 10F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val largeCoolingTower = register("large_cooling_tower") { LargeCoolingTowerBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 10F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val oilDerrick = register("oil_derrick") { OilDerrickBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val pumpjack = register("pumpjack") { PumpjackBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val centrifuge = register("centrifuge") { CentrifugeBlock(Properties.of().mapColor(MapColor.METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }

    val ironAnvil = register("iron_anvil") { AnvilBlock(1, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val leadAnvil = register("lead_anvil") { AnvilBlock(1, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val steelAnvil = register("steel_anvil") { AnvilBlock(2, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val meteoriteAnvil = register("meteorite_anvil") { AnvilBlock(3, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val starmetalAnvil = register("starmetal_anvil") { AnvilBlock(3, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val ferrouraniumAnvil = register("ferrouranium_anvil") { AnvilBlock(4, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val bismuthAnvil = register("bismuth_anvil") { AnvilBlock(5, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val schrabidateAnvil = register("schrabidate_anvil") { AnvilBlock(6, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val dineutroniumAnvil = register("dineutronium_anvil") { AnvilBlock(7, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val murkyAnvil = register("murky_anvil") { AnvilBlock(1916169, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }

    val rbmkColumn = register("rbmk_column") { RBMKColumnBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkRod = register("rbmk_rod") { RBMKRodBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedRod = register("rbmk_moderated_rod") { RBMKModeratedRodBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkReaSimRod = register("rbmk_reasim_rod") { RBMKReaSimRodBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedReaSimRod = register("rbmk_moderated_reasim_rod") { RBMKModeratedReaSimRodBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkReflector = register("rbmk_reflector") { RBMKReflectorBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModerator = register("rbmk_moderator") { RBMKModeratorBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkAbsorber = register("rbmk_absorber") { RBMKAbsorberBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBoilerColumn = register("rbmk_boiler_column") { RBMKBoilerColumnBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBoiler = register("rbmk_boiler") { RBMKBoilerBlock(rbmkBoilerColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBlank = register("rbmk_blank") { RBMKBlankBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkManualControlRod = register("rbmk_manual_control_rod") { RBMKManualControlBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedControlRod = register("rbmk_moderated_control_rod") { RBMKModeratedControlBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkAutoControlRod = register("rbmk_auto_control_rod") { RBMKAutoControlBlock(rbmkColumn, Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkSteamConnector = register("rbmk_steam_connector") { RBMKSteamConnectorBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkInlet = register("rbmk_inlet") { RBMKInletBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkOutlet = register("rbmk_outlet") { RBMKOutletBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkConsole = register("rbmk_console") { RBMKConsoleBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkDebris = register("rbmk_debris") { RBMKDebrisBlock(Properties.of().mapColor(MapColor.METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBurningDebris = register("rbmk_burning_debris") { RBMKBurningDebrisBlock(Properties.of().mapColor(MapColor.METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkRadioactiveDebris = register("rbmk_radioactive_debris") { RBMKRadioactiveDebrisBlock(Properties.of().mapColor(MapColor.METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().lightLevel { 15 }) }

    val coatedCable = register("coated_red_copper_cable") { CoatedCableBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val coatedUniversalFluidDuct = register("coated_fluid_duct") { CoatedUniversalFluidPipeBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    // Bombs

    val littleBoy = register("little_boy") { LittleBoyBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val fatMan = register("fat_man") { FatManBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val volcanoCore = register("volcano_core") { VolcanoBlock(Properties.of().mapColor(MapColor.STONE).strength(-1F, 10000F).sound(SoundType.STONE).noLootTable()) }

    // Missiles

    val launchPad = register("launch_pad") { LaunchPadBlock(Properties.of().mapColor(MapColor.METAL).strength(5F, 1000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val launchPadPart = register("launch_pad_part", LaunchPadBlock::LaunchPadPartBlock)

    // Other

    val genericMultiBlockPart = register("generic_multi_block_part") { MultiBlockPart() }
    val genericMultiBlockPort = register("generic_multi_block_port") { MultiBlockPort() }
    val oilPipe = register("oil_pipe") { Block(Properties.of().mapColor(MapColor.METAL).strength(5F, 10F).sound(SoundType.METAL)) }

    // Decoration
    val decoRbmkBlock = register("deco_rbmk") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val decoRbmkSmoothBlock = register("deco_rbmk_smooth") { Block(Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    private fun getLightLevelLit13(state: BlockState) = if (state.getValue(BlockStateProperties.LIT)) 13 else 0
}

