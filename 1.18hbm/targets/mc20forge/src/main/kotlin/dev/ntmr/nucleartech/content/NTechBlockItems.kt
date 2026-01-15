/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.block.*
import dev.ntmr.nucleartech.content.block.entity.*
import dev.ntmr.nucleartech.content.block.entity.rbmk.*
import dev.ntmr.nucleartech.content.block.rbmk.RBMKConsoleBlock
import dev.ntmr.nucleartech.content.item.*
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object NTechBlockItems : NTechRegistry<Item> {
    override val forgeRegistry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val uraniumOre = register("uranium_ore") { BlockItem(NTechBlocks.uraniumOre.get(), Item.Properties()) }
    val scorchedUraniumOre = register("scorched_uranium_ore") { BlockItem(NTechBlocks.scorchedUraniumOre.get(), Item.Properties()) }
    val thoriumOre = register("thorium_ore") { BlockItem(NTechBlocks.thoriumOre.get(), Item.Properties()) }
    val titaniumOre = register("titanium_ore") { BlockItem(NTechBlocks.titaniumOre.get(), Item.Properties()) }
    val sulfurOre = register("sulfur_ore") { BlockItem(NTechBlocks.sulfurOre.get(), Item.Properties()) }
    val niterOre = register("niter_ore") { BlockItem(NTechBlocks.niterOre.get(), Item.Properties()) }
    val tungstenOre = register("tungsten_ore") { BlockItem(NTechBlocks.tungstenOre.get(), Item.Properties()) }
    val aluminiumOre = register("aluminium_ore") { BlockItem(NTechBlocks.aluminiumOre.get(), Item.Properties()) }
    val fluoriteOre = register("fluorite_ore") { BlockItem(NTechBlocks.fluoriteOre.get(), Item.Properties()) }
    val berylliumOre = register("beryllium_ore") { BlockItem(NTechBlocks.berylliumOre.get(), Item.Properties()) }
    val leadOre = register("lead_ore") { BlockItem(NTechBlocks.leadOre.get(), Item.Properties()) }
    val oilDeposit = register("oil_deposit") { TooltipBlockItem(NTechBlocks.oilDeposit.get(), Item.Properties()) }
    val emptyOilDeposit = register("empty_oil_deposit") { BlockItem(NTechBlocks.emptyOilDeposit.get(), Item.Properties()) }
    val oilSand = register("oil_sand") { BlockItem(NTechBlocks.oilSand.get(), Item.Properties()) }
    val ligniteOre = register("lignite_ore") { BlockItem(NTechBlocks.ligniteOre.get(), Item.Properties()) }
    val asbestosOre = register("asbestos_ore") { BlockItem(NTechBlocks.asbestosOre.get(), Item.Properties()) }
    val schrabidiumOre = register("schrabidium_ore") { BlockItem(NTechBlocks.schrabidiumOre.get(), Item.Properties().rarity(Rarity.RARE)) }
    val australianOre = register("australian_ore") { TooltipBlockItem(NTechBlocks.australianOre.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val weidite = register("weidite") { TooltipBlockItem(NTechBlocks.weidite.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val reiite = register("reiite") { TooltipBlockItem(NTechBlocks.reiite.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val brightblendeOre = register("brightblende_ore") { TooltipBlockItem(NTechBlocks.brightblendeOre.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val dellite = register("dellite") { TooltipBlockItem(NTechBlocks.dellite.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral = register("dollar_green_mineral") { TooltipBlockItem(NTechBlocks.dollarGreenMineral.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val rareEarthOre = register("rare_earth_ore") { BlockItem(NTechBlocks.rareEarthOre.get(), Item.Properties().rarity(Rarity.UNCOMMON)) }
    val cobaltOre = register("cobalt_ore") { BlockItem(NTechBlocks.cobaltOre.get(), Item.Properties()) }
    val deepslateUraniumOre = register("deepslate_uranium_ore") { BlockItem(NTechBlocks.deepslateUraniumOre.get(), Item.Properties()) }
    val scorchedDeepslateUraniumOre = register("scorched_deepslate_uranium_ore") { BlockItem(NTechBlocks.scorchedDeepslateUraniumOre.get(), Item.Properties()) }
    val deepslateThoriumOre = register("deepslate_thorium_ore") { BlockItem(NTechBlocks.deepslateThoriumOre.get(), Item.Properties()) }
    val deepslateTitaniumOre = register("deepslate_titanium_ore") { BlockItem(NTechBlocks.deepslateTitaniumOre.get(), Item.Properties()) }
    val deepslateSulfurOre = register("deepslate_sulfur_ore") { BlockItem(NTechBlocks.deepslateSulfurOre.get(), Item.Properties()) }
    val deepslateNiterOre = register("deepslate_niter_ore") { BlockItem(NTechBlocks.deepslateNiterOre.get(), Item.Properties()) }
    val deepslateTungstenOre = register("deepslate_tungsten_ore") { BlockItem(NTechBlocks.deepslateTungstenOre.get(), Item.Properties()) }
    val deepslateAluminiumOre = register("deepslate_aluminium_ore") { BlockItem(NTechBlocks.deepslateAluminiumOre.get(), Item.Properties()) }
    val deepslateFluoriteOre = register("deepslate_fluorite_ore") { BlockItem(NTechBlocks.deepslateFluoriteOre.get(), Item.Properties()) }
    val deepslateBerylliumOre = register("deepslate_beryllium_ore") { BlockItem(NTechBlocks.deepslateBerylliumOre.get(), Item.Properties()) }
    val deepslateLeadOre = register("deepslate_lead_ore") { BlockItem(NTechBlocks.deepslateLeadOre.get(), Item.Properties()) }
    val deepslateOilDeposit = register("deepslate_oil_deposit") { TooltipBlockItem(NTechBlocks.deepslateOilDeposit.get(), Item.Properties()) }
    val emptyDeepslateOilDeposit = register("empty_deepslate_oil_deposit") { BlockItem(NTechBlocks.emptyDeepslateOilDeposit.get(), Item.Properties()) }
    val deepslateAsbestosOre = register("deepslate_asbestos_ore") { BlockItem(NTechBlocks.deepslateAsbestosOre.get(), Item.Properties()) }
    val deepslateSchrabidiumOre = register("deepslate_schrabidium_ore") { BlockItem(NTechBlocks.deepslateSchrabidiumOre.get(), Item.Properties()) }
    val deepslateAustralianOre = register("deepslate_australian_ore") { TooltipBlockItem(NTechBlocks.deepslateAustralianOre.get(), Item.Properties()) }
    val deepslateRareEarthOre = register("deepslate_rare_earth_ore") { BlockItem(NTechBlocks.deepslateRareEarthOre.get(), Item.Properties()) }
    val deepslateCobaltOre = register("deepslate_cobalt_ore") { BlockItem(NTechBlocks.deepslateCobaltOre.get(), Item.Properties()) }
    val netherUraniumOre = register("nether_uranium_ore") { BlockItem(NTechBlocks.netherUraniumOre.get(), Item.Properties()) }
    val scorchedNetherUraniumOre = register("scorched_nether_uranium_ore") { BlockItem(NTechBlocks.scorchedNetherUraniumOre.get(), Item.Properties()) }
    val netherPlutoniumOre = register("nether_plutonium_ore") { BlockItem(NTechBlocks.netherPlutoniumOre.get(), Item.Properties()) }
    val netherTungstenOre = register("nether_tungsten_ore") { BlockItem(NTechBlocks.netherTungstenOre.get(), Item.Properties()) }
    val netherSulfurOre = register("nether_sulfur_ore") { BlockItem(NTechBlocks.netherSulfurOre.get(), Item.Properties()) }
    val netherPhosphorusOre = register("nether_phosphorus_ore") { BlockItem(NTechBlocks.netherPhosphorusOre.get(), Item.Properties()) }
    val netherSchrabidiumOre = register("nether_schrabidium_ore") { BlockItem(NTechBlocks.netherSchrabidiumOre.get(), Item.Properties().rarity(Rarity.RARE)) }
    val meteorUraniumOre = register("meteor_uranium_ore") { BlockItem(NTechBlocks.meteorUraniumOre.get(), Item.Properties()) }
    val meteorThoriumOre = register("meteor_thorium_ore") { BlockItem(NTechBlocks.meteorThoriumOre.get(), Item.Properties()) }
    val meteorTitaniumOre = register("meteor_titanium_ore") { BlockItem(NTechBlocks.meteorTitaniumOre.get(), Item.Properties()) }
    val meteorSulfurOre = register("meteor_sulfur_ore") { BlockItem(NTechBlocks.meteorSulfurOre.get(), Item.Properties()) }
    val meteorCopperOre = register("meteor_copper_ore") { BlockItem(NTechBlocks.meteorCopperOre.get(), Item.Properties()) }
    val meteorTungstenOre = register("meteor_tungsten_ore") { BlockItem(NTechBlocks.meteorTungstenOre.get(), Item.Properties()) }
    val meteorAluminiumOre = register("meteor_aluminium_ore") { BlockItem(NTechBlocks.meteorAluminiumOre.get(), Item.Properties()) }
    val meteorLeadOre = register("meteor_lead_ore") { BlockItem(NTechBlocks.meteorLeadOre.get(), Item.Properties()) }
    val meteorLithiumOre = register("meteor_lithium_ore") { BlockItem(NTechBlocks.meteorLithiumOre.get(), Item.Properties()) }
    val starmetalOre = register("starmetal_ore") { BlockItem(NTechBlocks.starmetalOre.get(), Item.Properties()) }
    val trixite = register("trixite") { BlockItem(NTechBlocks.trixite.get(), Item.Properties()) }
    val basaltSulfurOre = register("basalt_sulfur_ore") { BlockItem(NTechBlocks.basaltSulfurOre.get(), Item.Properties()) }
    val basaltFluoriteOre = register("basalt_fluorite_ore") { BlockItem(NTechBlocks.basaltFluoriteOre.get(), Item.Properties()) }
    val basaltAsbestosOre = register("basalt_asbestos_ore") { BlockItem(NTechBlocks.basaltAsbestosOre.get(), Item.Properties()) }
    val basaltVolcanicGemOre = register("basalt_volcanic_gem_ore") { BlockItem(NTechBlocks.basaltVolcanicGemOre.get(), Item.Properties()) }

    val uraniumBlock = register("uranium_block") { BlockItem(NTechBlocks.uraniumBlock.get(), Item.Properties()) }
    val u233Block = register("u233_block") { BlockItem(NTechBlocks.u233Block.get(), Item.Properties()) }
    val u235Block = register("u235_block") { BlockItem(NTechBlocks.u235Block.get(), Item.Properties()) }
    val u238Block = register("u238_block") { BlockItem(NTechBlocks.u238Block.get(), Item.Properties()) }
    val uraniumFuelBlock = register("uranium_fuel_block") { BlockItem(NTechBlocks.uraniumFuelBlock.get(), Item.Properties()) }
    val neptuniumBlock = register("neptunium_block") { BlockItem(NTechBlocks.neptuniumBlock.get(), Item.Properties()) }
    val moxFuelBlock = register("mox_fuel_block") { BlockItem(NTechBlocks.moxFuelBlock.get(), Item.Properties()) }
    val plutoniumBlock = register("plutonium_block") { BlockItem(NTechBlocks.plutoniumBlock.get(), Item.Properties()) }
    val pu238Block = register("pu238_block") { BlockItem(NTechBlocks.pu238Block.get(), Item.Properties()) }
    val pu239Block = register("pu239_block") { BlockItem(NTechBlocks.pu239Block.get(), Item.Properties()) }
    val pu240Block = register("pu240_block") { BlockItem(NTechBlocks.pu240Block.get(), Item.Properties()) }
    val plutoniumFuelBlock = register("plutonium_fuel_block") { BlockItem(NTechBlocks.plutoniumFuelBlock.get(), Item.Properties()) }
    val thoriumBlock = register("thorium_block") { BlockItem(NTechBlocks.thoriumBlock.get(), Item.Properties()) }
    val thoriumFuelBlock = register("thorium_fuel_block") { BlockItem(NTechBlocks.thoriumFuelBlock.get(), Item.Properties()) }
    val titaniumBlock = register("titanium_block") { BlockItem(NTechBlocks.titaniumBlock.get(), Item.Properties()) }
    val sulfurBlock = register("sulfur_block") { BlockItem(NTechBlocks.sulfurBlock.get(), Item.Properties()) }
    val niterBlock = register("niter_block") { BlockItem(NTechBlocks.niterBlock.get(), Item.Properties()) }
    val redCopperBlock = register("red_copper_block") { BlockItem(NTechBlocks.redCopperBlock.get(), Item.Properties()) }
    val advancedAlloyBlock = register("advanced_alloy_block") { BlockItem(NTechBlocks.advancedAlloyBlock.get(), Item.Properties()) }
    val tungstenBlock = register("tungsten_block") { BlockItem(NTechBlocks.tungstenBlock.get(), Item.Properties()) }
    val aluminiumBlock = register("aluminium_block") { BlockItem(NTechBlocks.aluminiumBlock.get(), Item.Properties()) }
    val fluoriteBlock = register("fluorite_block") { BlockItem(NTechBlocks.fluoriteBlock.get(), Item.Properties()) }
    val berylliumBlock = register("beryllium_block") { BlockItem(NTechBlocks.berylliumBlock.get(), Item.Properties()) }
    val cobaltBlock = register("cobalt_block") { BlockItem(NTechBlocks.cobaltBlock.get(), Item.Properties()) }
    val steelBlock = register("steel_block") { BlockItem(NTechBlocks.steelBlock.get(), Item.Properties()) }
    val leadBlock = register("lead_block") { BlockItem(NTechBlocks.leadBlock.get(), Item.Properties()) }
    val lithiumBlock = register("lithium_block") { BlockItem(NTechBlocks.lithiumBlock.get(), Item.Properties()) }
    val whitePhosphorusBlock = register("white_phosphorus_block") { BlockItem(NTechBlocks.whitePhosphorusBlock.get(), Item.Properties()) }
    val redPhosphorusBlock = register("red_phosphorus_block") { BlockItem(NTechBlocks.redPhosphorusBlock.get(), Item.Properties()) }
    val yellowcakeBlock = register("yellowcake_block") { BlockItem(NTechBlocks.yellowcakeBlock.get(), Item.Properties()) }
    val scrapBlock: RegistryObject<BlockItem> = register("scrap_block") { object : BlockItem(NTechBlocks.scrapBlock.get(), Properties()) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 4000
    }}
    val electricalScrapBlock = register("electrical_scrap_block") { BlockItem(NTechBlocks.electricalScrapBlock.get(), Item.Properties()) }
    val insulatorRoll = register("insulator_roll") { BlockItem(NTechBlocks.insulatorRoll.get(), Item.Properties()) }
    val fiberglassRoll = register("fiberglass_roll") { BlockItem(NTechBlocks.fiberglassRoll.get(), Item.Properties()) }
    val asbestosBlock = register("asbestos_block") { BlockItem(NTechBlocks.asbestosBlock.get(), Item.Properties()) }
    val trinititeBlock = register("trinitite_block") { BlockItem(NTechBlocks.trinititeBlock.get(), Item.Properties()) }
    val nuclearWasteBlock = register("nuclear_waste_block") { BlockItem(NTechBlocks.nuclearWasteBlock.get(), Item.Properties()) }
    val schrabidiumBlock = register("schrabidium_block") { BlockItem(NTechBlocks.schrabidiumBlock.get(), Item.Properties()) }
    val soliniumBlock = register("solinium_block") { BlockItem(NTechBlocks.soliniumBlock.get(), Item.Properties()) }
    val schrabidiumFuelBlock = register("schrabidium_fuel_block") { BlockItem(NTechBlocks.schrabidiumFuelBlock.get(), Item.Properties()) }
    val euphemiumBlock = register("euphemium_block") { BlockItem(NTechBlocks.euphemiumBlock.get(), Item.Properties()) }
    val schrabidiumCluster = register("schrabidium_cluster") { BlockItem(NTechBlocks.schrabidiumCluster.get(), Item.Properties()) }
    val euphemiumEtchedSchrabidiumCluster = register("euphemium_etched_schrabidium_cluster") { BlockItem(
        NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties()) }
    val magnetizedTungstenBlock = register("magnetized_tungsten_block") { BlockItem(NTechBlocks.magnetizedTungstenBlock.get(), Item.Properties()) }
    val combineSteelBlock = register("combine_steel_block") { BlockItem(NTechBlocks.combineSteelBlock.get(), Item.Properties()) }
    val deshReinforcedBlock = register("desh_reinforced_block") { BlockItem(NTechBlocks.deshReinforcedBlock.get(), Item.Properties()) }
    val starmetalBlock = register("starmetal_block") { BlockItem(NTechBlocks.starmetalBlock.get(), Item.Properties()) }
    val australiumBlock = register("australium_block") { BlockItem(NTechBlocks.australiumBlock.get(), Item.Properties()) }
    val weidaniumBlock = register("weidanium_block") { BlockItem(NTechBlocks.weidaniumBlock.get(), Item.Properties()) }
    val reiiumBlock = register("reiium_block") { BlockItem(NTechBlocks.reiiumBlock.get(), Item.Properties()) }
    val unobtainiumBlock = register("unobtainium_block") { BlockItem(NTechBlocks.unobtainiumBlock.get(), Item.Properties()) }
    val daffergonBlock = register("daffergon_block") { BlockItem(NTechBlocks.daffergonBlock.get(), Item.Properties()) }
    val verticiumBlock = register("verticium_block") { BlockItem(NTechBlocks.verticiumBlock.get(), Item.Properties()) }
    val titaniumDecoBlock = register("titanium_deco_block") { BlockItem(NTechBlocks.titaniumDecoBlock.get(), Item.Properties()) }
    val redCopperDecoBlock = register("red_copper_deco_block") { BlockItem(NTechBlocks.redCopperDecoBlock.get(), Item.Properties()) }
    val tungstenDecoBlock = register("tungsten_deco_block") { BlockItem(NTechBlocks.tungstenDecoBlock.get(), Item.Properties()) }
    val aluminiumDecoBlock = register("aluminium_deco_block") { BlockItem(NTechBlocks.aluminiumDecoBlock.get(), Item.Properties()) }
    val steelDecoBlock = register("steel_deco_block") { BlockItem(NTechBlocks.steelDecoBlock.get(), Item.Properties()) }
    val leadDecoBlock = register("lead_deco_block") { BlockItem(NTechBlocks.leadDecoBlock.get(), Item.Properties()) }
    val berylliumDecoBlock = register("beryllium_deco_block") { BlockItem(NTechBlocks.berylliumDecoBlock.get(), Item.Properties()) }
    val asbestosRoof = register("asbestos_roof") { BlockItem(NTechBlocks.asbestosRoof.get(), Item.Properties()) }
    val hazmatBlock = register("hazmat_block") { BlockItem(NTechBlocks.hazmatBlock.get(), Item.Properties()) }

    val meteorite = register("meteorite") { BlockItem(NTechBlocks.meteorite.get(), Item.Properties()) }
    val meteoriteCobblestone = register("meteorite_cobblestone") { BlockItem(NTechBlocks.meteoriteCobblestone.get(), Item.Properties()) }
    val brokenMeteorite = register("broken_meteorite") { BlockItem(NTechBlocks.brokenMeteorite.get(), Item.Properties()) }
    val hotMeteoriteCobblestone = register("hot_meteorite_cobblestone") { BlockItem(NTechBlocks.hotMeteoriteCobblestone.get(), Item.Properties()) }
    val meteoriteTreasure = register("meteorite_treasure") { BlockItem(NTechBlocks.meteoriteTreasure.get(), Item.Properties()) }

    val decoRbmkBlock = register("deco_rbmk") { BlockItem(NTechBlocks.decoRbmkBlock.get(), Item.Properties()) }
    val decoRbmkSmoothBlock = register("deco_rbmk_smooth") { BlockItem(NTechBlocks.decoRbmkSmoothBlock.get(), Item.Properties()) }

    val steelBeam = register("steel_beam") { BlockItem(NTechBlocks.steelBeam.get(), Item.Properties()) }
    val steelScaffold = register("steel_scaffold") { BlockItem(NTechBlocks.steelScaffold.get(), Item.Properties()) }
    val steelGrate = register("steel_grate") { BlockItem(NTechBlocks.steelGrate.get(), Item.Properties()) }

    val glowingMushroom = register("glowing_mushroom") { BlockItem(NTechBlocks.glowingMushroom.get(), Item.Properties()) }
    val glowingMushroomBlock = register("glowing_mushroom_block") { BlockItem(NTechBlocks.glowingMushroomBlock.get(), Item.Properties()) }
    val glowingMushroomStem = register("glowing_mushroom_stem") { BlockItem(NTechBlocks.glowingMushroomStem.get(), Item.Properties()) }
    val deadGrass = register("dead_grass") { BlockItem(NTechBlocks.deadGrass.get(), Item.Properties()) }
    val glowingMycelium = register("glowing_mycelium") { BlockItem(NTechBlocks.glowingMycelium.get(), Item.Properties()) }
    val trinititeOre = register("trinitite_ore") { BlockItem(NTechBlocks.trinitite.get(), Item.Properties()) }
    val redTrinititeOre = register("red_trinitite_ore") { BlockItem(NTechBlocks.redTrinitite.get(), Item.Properties()) }
    val charredLog: RegistryObject<BlockItem> = register("charred_log") { object : BlockItem(NTechBlocks.charredLog.get(), Properties()) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}
    val charredPlanks: RegistryObject<BlockItem> = register("charred_planks") { object : BlockItem(NTechBlocks.charredPlanks.get(), Properties()) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}

    val slakedSellafite = register("slaked_sellafite") { BlockItem(NTechBlocks.slakedSellafite.get(), Item.Properties()) }
    val sellafite = register("sellafite") { BlockItem(NTechBlocks.sellafite.get(), Item.Properties()) }
    val hotSellafite = register("hot_sellafite") { BlockItem(NTechBlocks.hotSellafite.get(), Item.Properties()) }
    val boilingSellafite = register("boiling_sellafite") { BlockItem(NTechBlocks.boilingSellafite.get(), Item.Properties()) }
    val blazingSellafite = register("blazing_sellafite") { BlockItem(NTechBlocks.blazingSellafite.get(), Item.Properties()) }
    val infernalSellafite = register("infernal_sellafite") { BlockItem(NTechBlocks.infernalSellafite.get(), Item.Properties()) }
    val sellafiteCorium = register("sellafite_corium") { BlockItem(NTechBlocks.sellafiteCorium.get(), Item.Properties()) }

    val corium = register("corium") { BlockItem(NTechBlocks.corium.get(), Item.Properties()) }
    val corebblestone = register("corebblestone") { BlockItem(NTechBlocks.corebblestone.get(), Item.Properties()) }

    val siren = register("siren") { BlockItem(NTechBlocks.siren.get(), Item.Properties()) }
    val safe = register("safe") { BlockItem(NTechBlocks.safe.get(), Item.Properties()) }
    val steamPress = register("steam_press") { BlockItem(NTechBlocks.steamPressBase.get(), Item.Properties()) }
    val blastFurnace = register("blast_furnace") { BlockItem(NTechBlocks.blastFurnace.get(), Item.Properties()) }
    val combustionGenerator = register("combustion_generator") { BlockItem(NTechBlocks.combustionGenerator.get(), Item.Properties()) }
    val dieselGenerator = register("diesel_generator") { BlockItem(NTechBlocks.dieselGenerator.get(), Item.Properties()) }
    val batteryBox = register("battery_box") { BlockItem(NTechBlocks.batteryBox.get(), Item.Properties()) }
    val electricFurnace = register("electric_furnace") { BlockItem(NTechBlocks.electricFurnace.get(), Item.Properties()) }
    val electricPress = register("electric_press") { BlockItem(NTechBlocks.electricPress.get(), Item.Properties()) }
    val fractionatingColumn = register("fractionating_column") { BlockItem(NTechBlocks.fractionatingColumn.get(), Item.Properties()) }
    val shredder = register("shredder") { BlockItem(NTechBlocks.shredder.get(), Item.Properties()) }
    val assemblerPlacer = register("assembler") { SpecialModelMultiBlockPlacerItem(NTechBlocks.assembler.get(), ::AssemblerBlockEntity, AssemblerBlock::placeMultiBlock, Item.Properties()) }
    val chemPlantPlacer = register("chem_plant") { SpecialModelMultiBlockPlacerItem(NTechBlocks.chemPlant.get(), ::ChemPlantBlockEntity, ChemPlantBlock::placeMultiBlock, Item.Properties()) }
    val turbine = register("turbine") { TooltipBlockItem(NTechBlocks.turbine.get(), Item.Properties()) }
    val smallCoolingTower = register("small_cooling_tower") { SpecialModelMultiBlockPlacerItem(NTechBlocks.smallCoolingTower.get(), ::SmallCoolingTowerBlockEntity, SmallCoolingTowerBlock::placeMultiBlock, Item.Properties(), 2F) }
    val largeCoolingTower = register("large_cooling_tower") { SpecialModelMultiBlockPlacerItem(NTechBlocks.largeCoolingTower.get(), ::LargeCoolingTowerBlockEntity, LargeCoolingTowerBlock::placeMultiBlock, Item.Properties(), 4F) }
    val oilDerrickPlacer = register("oil_derrick") { SpecialModelMultiBlockPlacerItem(NTechBlocks.oilDerrick.get(), ::OilDerrickBlockEntity, OilDerrickBlock::placeMultiBlock, Item.Properties()) }
    val pumpjackPlacer = register("pumpjack") { PumpjackPlacerItem(NTechBlocks.pumpjack.get(), ::PumpjackBlockEntity, PumpjackBlock::placeMultiBlock, Item.Properties()) }
    val centrifugePlacer = register("centrifuge") { SpecialModelMultiBlockPlacerItem(NTechBlocks.centrifuge.get(), ::CentrifugeBlockEntity, CentrifugeBlock::placeMultiBlock, Item.Properties()) }

    val ironAnvil = register("iron_anvil") { BlockItem(NTechBlocks.ironAnvil.get(), Item.Properties()) }
    val leadAnvil = register("lead_anvil") { BlockItem(NTechBlocks.leadAnvil.get(), Item.Properties()) }
    val steelAnvil = register("steel_anvil") { BlockItem(NTechBlocks.steelAnvil.get(), Item.Properties()) }
    val meteoriteAnvil = register("meteorite_anvil") { BlockItem(NTechBlocks.meteoriteAnvil.get(), Item.Properties()) }
    val starmetalAnvil = register("starmetal_anvil") { BlockItem(NTechBlocks.starmetalAnvil.get(), Item.Properties()) }
    val ferrouraniumAnvil = register("ferrouranium_anvil") { BlockItem(NTechBlocks.ferrouraniumAnvil.get(), Item.Properties()) }
    val bismuthAnvil = register("bismuth_anvil") { BlockItem(NTechBlocks.bismuthAnvil.get(), Item.Properties()) }
    val schrabidateAnvil = register("schrabidate_anvil") { BlockItem(NTechBlocks.schrabidateAnvil.get(), Item.Properties()) }
    val dineutroniumAnvil = register("dineutronium_anvil") { BlockItem(NTechBlocks.dineutroniumAnvil.get(), Item.Properties()) }
    val murkyAnvil = register("murky_anvil") { BlockItem(NTechBlocks.murkyAnvil.get(), Item.Properties()) }

    val rbmkRod = register("rbmk_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkRod.get(), ::RBMKRodBlockEntity, Item.Properties()) }
    val rbmkModeratedRod = register("rbmk_moderated_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedRod.get(), ::RBMKModeratedRodBlockEntity, Item.Properties()) }
    val rbmkReaSimRod = register("rbmk_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkReaSimRod.get(), ::RBMKReaSimRodBlockEntity, Item.Properties()) }
    val rbmkModeratedReaSimRod = register("rbmk_moderated_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedReaSimRod.get(), ::RBMKModeratedReaSimRodBlockEntity, Item.Properties()) }
    val rbmkReflector = register("rbmk_reflector") { RBMKColumnBlockItem(NTechBlocks.rbmkReflector.get(), ::RBMKReflectorBlockEntity, Item.Properties()) }
    val rbmkModerator = register("rbmk_moderator") { RBMKColumnBlockItem(NTechBlocks.rbmkModerator.get(), ::RBMKModeratorBlockEntity, Item.Properties()) }
    val rbmkAbsorber = register("rbmk_absorber") { RBMKColumnBlockItem(NTechBlocks.rbmkAbsorber.get(), ::RBMKAbsorberBlockEntity, Item.Properties()) }
    val rbmkBlank = register("rbmk_blank") { RBMKColumnBlockItem(NTechBlocks.rbmkBlank.get(), ::RBMKBlankBlockEntity, Item.Properties()) }
    val rbmkBoiler = register("rbmk_boiler") { RBMKBoilerColumnBlockItem(NTechBlocks.rbmkBoiler.get(), ::RBMKBoilerBlockEntity, Item.Properties()) }
    val rbmkManualControlRod = register("rbmk_manual_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkManualControlRod.get(), ::RBMKManualControlBlockEntity, Item.Properties()) }
    val rbmkModeratedControlRod = register("rbmk_moderated_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedControlRod.get(), ::RBMKModeratedControlBlockEntity, Item.Properties()) }
    val rbmkAutoControlRod = register("rbmk_auto_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkAutoControlRod.get(), ::RBMKAutoControlBlockEntity, Item.Properties()) }
    val rbmkSteamConnector = register("rbmk_steam_connector") { BlockItem(NTechBlocks.rbmkSteamConnector.get(), Item.Properties()) }
    val rbmkInlet = register("rbmk_inlet") { BlockItem(NTechBlocks.rbmkInlet.get(), Item.Properties()) }
    val rbmkOutlet = register("rbmk_outlet") { BlockItem(NTechBlocks.rbmkOutlet.get(), Item.Properties()) }
    val rbmkConsole = register("rbmk_console") { SpecialModelMultiBlockPlacerItem(NTechBlocks.rbmkConsole.get(), ::RBMKConsoleBlockEntity, RBMKConsoleBlock::placeMultiBlock, Item.Properties()) }
    val rbmkDebris = register("rbmk_debris") { BlockItem(NTechBlocks.rbmkDebris.get(), Item.Properties()) }
    val rbmkBurningDebris = register("rbmk_burning_debris") { BlockItem(NTechBlocks.rbmkBurningDebris.get(), Item.Properties()) }
    val rbmkRadioactiveDebris = register("rbmk_radioactive_debris") { BlockItem(NTechBlocks.rbmkRadioactiveDebris.get(), Item.Properties()) }

    val coatedCable = register("coated_red_copper_cable") { BlockItem(NTechBlocks.coatedCable.get(), Item.Properties()) }
    val coatedUniversalFluidDuct = register("coated_fluid_duct") { BlockItem(NTechBlocks.coatedUniversalFluidDuct.get(), Item.Properties()) }

    val littleBoy = register("little_boy") { SpecialModelBlockItem(NTechBlocks.littleBoy.get(), ::LittleBoyBlockEntity, Item.Properties()) }
    val fatMan = register("fat_man") { SpecialModelBlockItem(NTechBlocks.fatMan.get(), ::FatManBlockEntity, Item.Properties()) }
    val volcanoCore = register("volcano_core") { BlockItem(NTechBlocks.volcanoCore.get(), Item.Properties()) }

    val launchPadPlacer = register("launch_pad") { SpecialModelMultiBlockPlacerItem(NTechBlocks.launchPad.get(), ::LaunchPadBlockEntity, LaunchPadBlock::placeMultiBlock, Item.Properties()) }
    
    val forcefieldGenerator = register("forcefield_generator") { BlockItem(NTechBlocks.forcefieldGenerator.get(), Item.Properties()) }
    
    val fusionCore = register("fusion_core") { BlockItem(NTechBlocks.fusionCore.get(), Item.Properties()) }
    val fusionMagnet = register("fusion_magnet") { BlockItem(NTechBlocks.fusionMagnet.get(), Item.Properties()) }
    val fusionHeater = register("fusion_heater") { BlockItem(NTechBlocks.fusionHeater.get(), Item.Properties()) }

    val watzCore = register("watz_core") { BlockItem(NTechBlocks.watzCore.get(), Item.Properties()) }
    val watzStructure = register("watz_structure") { BlockItem(NTechBlocks.watzStructure.get(), Item.Properties()) }
    val watzFuelInject = register("watz_fuel_inject") { BlockItem(NTechBlocks.watzFuelInject.get(), Item.Properties()) }
    
    val oilRefinery = register("oil_refinery") { BlockItem(NTechBlocks.oilRefinery.get(), Item.Properties()) }
    val refineryStructure = register("refinery_structure") { BlockItem(NTechBlocks.refineryStructure.get(), Item.Properties()) }
    val landmine = register("landmine") { BlockItem(NTechBlocks.landmine.get(), Item.Properties()) }
}
