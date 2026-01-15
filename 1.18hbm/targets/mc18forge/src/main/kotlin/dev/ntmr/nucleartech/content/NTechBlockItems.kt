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

    val uraniumOre = register("uranium_ore") { BlockItem(NTechBlocks.uraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedUraniumOre = register("scorched_uranium_ore") { BlockItem(NTechBlocks.scorchedUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumOre = register("thorium_ore") { BlockItem(NTechBlocks.thoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumOre = register("titanium_ore") { BlockItem(NTechBlocks.titaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurOre = register("sulfur_ore") { BlockItem(NTechBlocks.sulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterOre = register("niter_ore") { BlockItem(NTechBlocks.niterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenOre = register("tungsten_ore") { BlockItem(NTechBlocks.tungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumOre = register("aluminium_ore") { BlockItem(NTechBlocks.aluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteOre = register("fluorite_ore") { BlockItem(NTechBlocks.fluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumOre = register("beryllium_ore") { BlockItem(NTechBlocks.berylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadOre = register("lead_ore") { BlockItem(NTechBlocks.leadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilDeposit = register("oil_deposit") { TooltipBlockItem(NTechBlocks.oilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyOilDeposit = register("empty_oil_deposit") { BlockItem(NTechBlocks.emptyOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilSand = register("oil_sand") { BlockItem(NTechBlocks.oilSand.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val ligniteOre = register("lignite_ore") { BlockItem(NTechBlocks.ligniteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosOre = register("asbestos_ore") { BlockItem(NTechBlocks.asbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumOre = register("schrabidium_ore") { BlockItem(NTechBlocks.schrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val australianOre = register("australian_ore") { TooltipBlockItem(NTechBlocks.australianOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val weidite = register("weidite") { TooltipBlockItem(NTechBlocks.weidite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val reiite = register("reiite") { TooltipBlockItem(NTechBlocks.reiite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre = register("brightblende_ore") { TooltipBlockItem(NTechBlocks.brightblendeOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dellite = register("dellite") { TooltipBlockItem(NTechBlocks.dellite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral = register("dollar_green_mineral") { TooltipBlockItem(NTechBlocks.dollarGreenMineral.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre = register("rare_earth_ore") { BlockItem(NTechBlocks.rareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val cobaltOre = register("cobalt_ore") { BlockItem(NTechBlocks.cobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateUraniumOre = register("deepslate_uranium_ore") { BlockItem(NTechBlocks.deepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedDeepslateUraniumOre = register("scorched_deepslate_uranium_ore") { BlockItem(NTechBlocks.scorchedDeepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateThoriumOre = register("deepslate_thorium_ore") { BlockItem(NTechBlocks.deepslateThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTitaniumOre = register("deepslate_titanium_ore") { BlockItem(NTechBlocks.deepslateTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSulfurOre = register("deepslate_sulfur_ore") { BlockItem(NTechBlocks.deepslateSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateNiterOre = register("deepslate_niter_ore") { BlockItem(NTechBlocks.deepslateNiterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTungstenOre = register("deepslate_tungsten_ore") { BlockItem(NTechBlocks.deepslateTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAluminiumOre = register("deepslate_aluminium_ore") { BlockItem(NTechBlocks.deepslateAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateFluoriteOre = register("deepslate_fluorite_ore") { BlockItem(NTechBlocks.deepslateFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateBerylliumOre = register("deepslate_beryllium_ore") { BlockItem(NTechBlocks.deepslateBerylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateLeadOre = register("deepslate_lead_ore") { BlockItem(NTechBlocks.deepslateLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateOilDeposit = register("deepslate_oil_deposit") { TooltipBlockItem(NTechBlocks.deepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyDeepslateOilDeposit = register("empty_deepslate_oil_deposit") { BlockItem(NTechBlocks.emptyDeepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAsbestosOre = register("deepslate_asbestos_ore") { BlockItem(NTechBlocks.deepslateAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSchrabidiumOre = register("deepslate_schrabidium_ore") { BlockItem(NTechBlocks.deepslateSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAustralianOre = register("deepslate_australian_ore") { TooltipBlockItem(NTechBlocks.deepslateAustralianOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateRareEarthOre = register("deepslate_rare_earth_ore") { BlockItem(NTechBlocks.deepslateRareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateCobaltOre = register("deepslate_cobalt_ore") { BlockItem(NTechBlocks.deepslateCobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherUraniumOre = register("nether_uranium_ore") { BlockItem(NTechBlocks.netherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedNetherUraniumOre = register("scorched_nether_uranium_ore") { BlockItem(NTechBlocks.scorchedNetherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPlutoniumOre = register("nether_plutonium_ore") { BlockItem(NTechBlocks.netherPlutoniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherTungstenOre = register("nether_tungsten_ore") { BlockItem(NTechBlocks.netherTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSulfurOre = register("nether_sulfur_ore") { BlockItem(NTechBlocks.netherSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPhosphorusOre = register("nether_phosphorus_ore") { BlockItem(NTechBlocks.netherPhosphorusOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSchrabidiumOre = register("nether_schrabidium_ore") { BlockItem(NTechBlocks.netherSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val meteorUraniumOre = register("meteor_uranium_ore") { BlockItem(NTechBlocks.meteorUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorThoriumOre = register("meteor_thorium_ore") { BlockItem(NTechBlocks.meteorThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTitaniumOre = register("meteor_titanium_ore") { BlockItem(NTechBlocks.meteorTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorSulfurOre = register("meteor_sulfur_ore") { BlockItem(NTechBlocks.meteorSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorCopperOre = register("meteor_copper_ore") { BlockItem(NTechBlocks.meteorCopperOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTungstenOre = register("meteor_tungsten_ore") { BlockItem(NTechBlocks.meteorTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorAluminiumOre = register("meteor_aluminium_ore") { BlockItem(NTechBlocks.meteorAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLeadOre = register("meteor_lead_ore") { BlockItem(NTechBlocks.meteorLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLithiumOre = register("meteor_lithium_ore") { BlockItem(NTechBlocks.meteorLithiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalOre = register("starmetal_ore") { BlockItem(NTechBlocks.starmetalOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trixite = register("trixite") { BlockItem(NTechBlocks.trixite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltSulfurOre = register("basalt_sulfur_ore") { BlockItem(NTechBlocks.basaltSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltFluoriteOre = register("basalt_fluorite_ore") { BlockItem(NTechBlocks.basaltFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltAsbestosOre = register("basalt_asbestos_ore") { BlockItem(NTechBlocks.basaltAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltVolcanicGemOre = register("basalt_volcanic_gem_ore") { BlockItem(NTechBlocks.basaltVolcanicGemOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val uraniumBlock = register("uranium_block") { BlockItem(NTechBlocks.uraniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u233Block = register("u233_block") { BlockItem(NTechBlocks.u233Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u235Block = register("u235_block") { BlockItem(NTechBlocks.u235Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u238Block = register("u238_block") { BlockItem(NTechBlocks.u238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val uraniumFuelBlock = register("uranium_fuel_block") { BlockItem(NTechBlocks.uraniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val neptuniumBlock = register("neptunium_block") { BlockItem(NTechBlocks.neptuniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val moxFuelBlock = register("mox_fuel_block") { BlockItem(NTechBlocks.moxFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumBlock = register("plutonium_block") { BlockItem(NTechBlocks.plutoniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu238Block = register("pu238_block") { BlockItem(NTechBlocks.pu238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu239Block = register("pu239_block") { BlockItem(NTechBlocks.pu239Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu240Block = register("pu240_block") { BlockItem(NTechBlocks.pu240Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumFuelBlock = register("plutonium_fuel_block") { BlockItem(NTechBlocks.plutoniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumBlock = register("thorium_block") { BlockItem(NTechBlocks.thoriumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumFuelBlock = register("thorium_fuel_block") { BlockItem(NTechBlocks.thoriumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumBlock = register("titanium_block") { BlockItem(NTechBlocks.titaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurBlock = register("sulfur_block") { BlockItem(NTechBlocks.sulfurBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterBlock = register("niter_block") { BlockItem(NTechBlocks.niterBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperBlock = register("red_copper_block") { BlockItem(NTechBlocks.redCopperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val advancedAlloyBlock = register("advanced_alloy_block") { BlockItem(NTechBlocks.advancedAlloyBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenBlock = register("tungsten_block") { BlockItem(NTechBlocks.tungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumBlock = register("aluminium_block") { BlockItem(NTechBlocks.aluminiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteBlock = register("fluorite_block") { BlockItem(NTechBlocks.fluoriteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumBlock = register("beryllium_block") { BlockItem(NTechBlocks.berylliumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val cobaltBlock = register("cobalt_block") { BlockItem(NTechBlocks.cobaltBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelBlock = register("steel_block") { BlockItem(NTechBlocks.steelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadBlock = register("lead_block") { BlockItem(NTechBlocks.leadBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val lithiumBlock = register("lithium_block") { BlockItem(NTechBlocks.lithiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val whitePhosphorusBlock = register("white_phosphorus_block") { BlockItem(NTechBlocks.whitePhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redPhosphorusBlock = register("red_phosphorus_block") { BlockItem(NTechBlocks.redPhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val yellowcakeBlock = register("yellowcake_block") { BlockItem(NTechBlocks.yellowcakeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scrapBlock: RegistryObject<BlockItem> = register("scrap_block") { object : BlockItem(NTechBlocks.scrapBlock.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 4000
    }}
    val electricalScrapBlock = register("electrical_scrap_block") { BlockItem(NTechBlocks.electricalScrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val insulatorRoll = register("insulator_roll") { BlockItem(NTechBlocks.insulatorRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fiberglassRoll = register("fiberglass_roll") { BlockItem(NTechBlocks.fiberglassRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosBlock = register("asbestos_block") { BlockItem(NTechBlocks.asbestosBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeBlock = register("trinitite_block") { BlockItem(NTechBlocks.trinititeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val nuclearWasteBlock = register("nuclear_waste_block") { BlockItem(NTechBlocks.nuclearWasteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumBlock = register("schrabidium_block") { BlockItem(NTechBlocks.schrabidiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val soliniumBlock = register("solinium_block") { BlockItem(NTechBlocks.soliniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumFuelBlock = register("schrabidium_fuel_block") { BlockItem(NTechBlocks.schrabidiumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumBlock = register("euphemium_block") { BlockItem(NTechBlocks.euphemiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumCluster = register("schrabidium_cluster") { BlockItem(NTechBlocks.schrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumEtchedSchrabidiumCluster = register("euphemium_etched_schrabidium_cluster") { BlockItem(
        NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val magnetizedTungstenBlock = register("magnetized_tungsten_block") { BlockItem(NTechBlocks.magnetizedTungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val combineSteelBlock = register("combine_steel_block") { BlockItem(NTechBlocks.combineSteelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deshReinforcedBlock = register("desh_reinforced_block") { BlockItem(NTechBlocks.deshReinforcedBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalBlock = register("starmetal_block") { BlockItem(NTechBlocks.starmetalBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val australiumBlock = register("australium_block") { BlockItem(NTechBlocks.australiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val weidaniumBlock = register("weidanium_block") { BlockItem(NTechBlocks.weidaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val reiiumBlock = register("reiium_block") { BlockItem(NTechBlocks.reiiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val unobtainiumBlock = register("unobtainium_block") { BlockItem(NTechBlocks.unobtainiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val daffergonBlock = register("daffergon_block") { BlockItem(NTechBlocks.daffergonBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val verticiumBlock = register("verticium_block") { BlockItem(NTechBlocks.verticiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumDecoBlock = register("titanium_deco_block") { BlockItem(NTechBlocks.titaniumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperDecoBlock = register("red_copper_deco_block") { BlockItem(NTechBlocks.redCopperDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenDecoBlock = register("tungsten_deco_block") { BlockItem(NTechBlocks.tungstenDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumDecoBlock = register("aluminium_deco_block") { BlockItem(NTechBlocks.aluminiumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelDecoBlock = register("steel_deco_block") { BlockItem(NTechBlocks.steelDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadDecoBlock = register("lead_deco_block") { BlockItem(NTechBlocks.leadDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumDecoBlock = register("beryllium_deco_block") { BlockItem(NTechBlocks.berylliumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosRoof = register("asbestos_roof") { BlockItem(NTechBlocks.asbestosRoof.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hazmatBlock = register("hazmat_block") { BlockItem(NTechBlocks.hazmatBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val meteorite = register("meteorite") { BlockItem(NTechBlocks.meteorite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteoriteCobblestone = register("meteorite_cobblestone") { BlockItem(NTechBlocks.meteoriteCobblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val brokenMeteorite = register("broken_meteorite") { BlockItem(NTechBlocks.brokenMeteorite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotMeteoriteCobblestone = register("hot_meteorite_cobblestone") { BlockItem(NTechBlocks.hotMeteoriteCobblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteoriteTreasure = register("meteorite_treasure") { BlockItem(NTechBlocks.meteoriteTreasure.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val decoRbmkBlock = register("deco_rbmk") { BlockItem(NTechBlocks.decoRbmkBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val decoRbmkSmoothBlock = register("deco_rbmk_smooth") { BlockItem(NTechBlocks.decoRbmkSmoothBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val steelBeam = register("steel_beam") { BlockItem(NTechBlocks.steelBeam.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelScaffold = register("steel_scaffold") { BlockItem(NTechBlocks.steelScaffold.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelGrate = register("steel_grate") { BlockItem(NTechBlocks.steelGrate.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val glowingMushroom = register("glowing_mushroom") { BlockItem(NTechBlocks.glowingMushroom.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomBlock = register("glowing_mushroom_block") { BlockItem(NTechBlocks.glowingMushroomBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomStem = register("glowing_mushroom_stem") { BlockItem(NTechBlocks.glowingMushroomStem.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deadGrass = register("dead_grass") { BlockItem(NTechBlocks.deadGrass.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMycelium = register("glowing_mycelium") { BlockItem(NTechBlocks.glowingMycelium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeOre = register("trinitite_ore") { BlockItem(NTechBlocks.trinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redTrinititeOre = register("red_trinitite_ore") { BlockItem(NTechBlocks.redTrinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val charredLog: RegistryObject<BlockItem> = register("charred_log") { object : BlockItem(NTechBlocks.charredLog.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}
    val charredPlanks: RegistryObject<BlockItem> = register("charred_planks") { object : BlockItem(NTechBlocks.charredPlanks.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}

    val slakedSellafite = register("slaked_sellafite") { BlockItem(NTechBlocks.slakedSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafite = register("sellafite") { BlockItem(NTechBlocks.sellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotSellafite = register("hot_sellafite") { BlockItem(NTechBlocks.hotSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val boilingSellafite = register("boiling_sellafite") { BlockItem(NTechBlocks.boilingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val blazingSellafite = register("blazing_sellafite") { BlockItem(NTechBlocks.blazingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val infernalSellafite = register("infernal_sellafite") { BlockItem(NTechBlocks.infernalSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafiteCorium = register("sellafite_corium") { BlockItem(NTechBlocks.sellafiteCorium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val corium = register("corium") { BlockItem(NTechBlocks.corium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val corebblestone = register("corebblestone") { BlockItem(NTechBlocks.corebblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val siren = register("siren") { BlockItem(NTechBlocks.siren.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val safe = register("safe") { BlockItem(NTechBlocks.safe.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steamPress = register("steam_press") { BlockItem(NTechBlocks.steamPressBase.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val blastFurnace = register("blast_furnace") { BlockItem(NTechBlocks.blastFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val combustionGenerator = register("combustion_generator") { BlockItem(NTechBlocks.combustionGenerator.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val electricFurnace = register("electric_furnace") { BlockItem(NTechBlocks.electricFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val shredder = register("shredder") { BlockItem(NTechBlocks.shredder.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val assemblerPlacer = register("assembler") { SpecialModelMultiBlockPlacerItem(NTechBlocks.assembler.get(), ::AssemblerBlockEntity, AssemblerBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val chemPlantPlacer = register("chem_plant") { SpecialModelMultiBlockPlacerItem(NTechBlocks.chemPlant.get(), ::ChemPlantBlockEntity, ChemPlantBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val turbine = register("turbine") { TooltipBlockItem(NTechBlocks.turbine.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val smallCoolingTower = register("small_cooling_tower") { SpecialModelMultiBlockPlacerItem(NTechBlocks.smallCoolingTower.get(), ::SmallCoolingTowerBlockEntity, SmallCoolingTowerBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines), 2F) }
    val largeCoolingTower = register("large_cooling_tower") { SpecialModelMultiBlockPlacerItem(NTechBlocks.largeCoolingTower.get(), ::LargeCoolingTowerBlockEntity, LargeCoolingTowerBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines), 4F) }
    val oilDerrickPlacer = register("oil_derrick") { SpecialModelMultiBlockPlacerItem(NTechBlocks.oilDerrick.get(), ::OilDerrickBlockEntity, OilDerrickBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val pumpjackPlacer = register("pumpjack") { PumpjackPlacerItem(NTechBlocks.pumpjack.get(), ::PumpjackBlockEntity, PumpjackBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val centrifugePlacer = register("centrifuge") { SpecialModelMultiBlockPlacerItem(NTechBlocks.centrifuge.get(), ::CentrifugeBlockEntity, CentrifugeBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }

    val ironAnvil = register("iron_anvil") { BlockItem(NTechBlocks.ironAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val leadAnvil = register("lead_anvil") { BlockItem(NTechBlocks.leadAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steelAnvil = register("steel_anvil") { BlockItem(NTechBlocks.steelAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val meteoriteAnvil = register("meteorite_anvil") { BlockItem(NTechBlocks.meteoriteAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val starmetalAnvil = register("starmetal_anvil") { BlockItem(NTechBlocks.starmetalAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val ferrouraniumAnvil = register("ferrouranium_anvil") { BlockItem(NTechBlocks.ferrouraniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val bismuthAnvil = register("bismuth_anvil") { BlockItem(NTechBlocks.bismuthAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val schrabidateAnvil = register("schrabidate_anvil") { BlockItem(NTechBlocks.schrabidateAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val dineutroniumAnvil = register("dineutronium_anvil") { BlockItem(NTechBlocks.dineutroniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val murkyAnvil = register("murky_anvil") { BlockItem(NTechBlocks.murkyAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val rbmkRod = register("rbmk_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkRod.get(), ::RBMKRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedRod = register("rbmk_moderated_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedRod.get(), ::RBMKModeratedRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkReaSimRod = register("rbmk_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkReaSimRod.get(), ::RBMKReaSimRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedReaSimRod = register("rbmk_moderated_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedReaSimRod.get(), ::RBMKModeratedReaSimRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkReflector = register("rbmk_reflector") { RBMKColumnBlockItem(NTechBlocks.rbmkReflector.get(), ::RBMKReflectorBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModerator = register("rbmk_moderator") { RBMKColumnBlockItem(NTechBlocks.rbmkModerator.get(), ::RBMKModeratorBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkAbsorber = register("rbmk_absorber") { RBMKColumnBlockItem(NTechBlocks.rbmkAbsorber.get(), ::RBMKAbsorberBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBlank = register("rbmk_blank") { RBMKColumnBlockItem(NTechBlocks.rbmkBlank.get(), ::RBMKBlankBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBoiler = register("rbmk_boiler") { RBMKBoilerColumnBlockItem(NTechBlocks.rbmkBoiler.get(), ::RBMKBoilerBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkManualControlRod = register("rbmk_manual_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkManualControlRod.get(), ::RBMKManualControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedControlRod = register("rbmk_moderated_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedControlRod.get(), ::RBMKModeratedControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkAutoControlRod = register("rbmk_auto_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkAutoControlRod.get(), ::RBMKAutoControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkSteamConnector = register("rbmk_steam_connector") { BlockItem(NTechBlocks.rbmkSteamConnector.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkInlet = register("rbmk_inlet") { BlockItem(NTechBlocks.rbmkInlet.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkOutlet = register("rbmk_outlet") { BlockItem(NTechBlocks.rbmkOutlet.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkConsole = register("rbmk_console") { SpecialModelMultiBlockPlacerItem(NTechBlocks.rbmkConsole.get(), ::RBMKConsoleBlockEntity, RBMKConsoleBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkDebris = register("rbmk_debris") { BlockItem(NTechBlocks.rbmkDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBurningDebris = register("rbmk_burning_debris") { BlockItem(NTechBlocks.rbmkBurningDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkRadioactiveDebris = register("rbmk_radioactive_debris") { BlockItem(NTechBlocks.rbmkRadioactiveDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val coatedCable = register("coated_red_copper_cable") { BlockItem(NTechBlocks.coatedCable.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val coatedUniversalFluidDuct = register("coated_fluid_duct") { BlockItem(NTechBlocks.coatedUniversalFluidDuct.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val littleBoy = register("little_boy") { SpecialModelBlockItem(NTechBlocks.littleBoy.get(), ::LittleBoyBlockEntity, Item.Properties().tab(CreativeTabs.Bombs)) }
    val fatMan = register("fat_man") { SpecialModelBlockItem(NTechBlocks.fatMan.get(), ::FatManBlockEntity, Item.Properties().tab(CreativeTabs.Bombs)) }
    val volcanoCore = register("volcano_core") { BlockItem(NTechBlocks.volcanoCore.get(), Item.Properties().tab(CreativeTabs.Bombs)) }

    val launchPadPlacer = register("launch_pad") { SpecialModelMultiBlockPlacerItem(NTechBlocks.launchPad.get(), ::LaunchPadBlockEntity, LaunchPadBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Rocketry)) }

    private fun Item.Properties.tab(tab: CreativeTabs): Item.Properties = tab(tab.tab)
}
