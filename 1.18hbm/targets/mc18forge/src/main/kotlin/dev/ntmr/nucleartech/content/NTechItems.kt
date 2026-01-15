/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.content.block.FatManBlock
import dev.ntmr.nucleartech.content.block.LittleBoyBlock
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKFluxReceiver
import dev.ntmr.nucleartech.content.entity.missile.*
import dev.ntmr.nucleartech.content.item.*
import dev.ntmr.nucleartech.content.item.upgrades.*
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Rarity
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

@Suppress("unused")
object NTechItems : NTechRegistry<Item> {
    override val forgeRegistry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    private val PARTS_COMMON = Properties().tab(CreativeTabs.Parts)
    private val PARTS_UNCOMMON = Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)
    private val PARTS_RARE = Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE)
    private val PARTS_EPIC = Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)
    private val ITEMS_COMMON = Properties().tab(CreativeTabs.Items)
    private val ITEMS_UNCOMMON = Properties().tab(CreativeTabs.Items).rarity(Rarity.UNCOMMON)
    private val ITEMS_RARE = Properties().tab(CreativeTabs.Items).rarity(Rarity.RARE)
    private val ITEMS_EPIC = Properties().tab(CreativeTabs.Items).rarity(Rarity.EPIC)

    val rawUranium = register("raw_uranium", partsItem())
    val rawThorium = register("raw_thorium", partsItem())
    val rawPlutonium = register("raw_plutonium", partsItem())
    val rawTitanium = register("raw_titanium", partsItem())
    val rawTungsten = register("raw_tungsten", partsItem())
    val rawAluminium = register("raw_aluminium", partsItem())
    val rawBeryllium = register("raw_beryllium", partsItem())
    val rawLead = register("raw_lead", partsItem())
    val rawSchrabidium = register("raw_schrabidium", partsItem(PARTS_RARE))
    val rawAustralium = register("raw_australium", partsItem())
    val rawRareEarth = register("raw_rare_earth", partsItem())
    val rawCobalt = register("raw_cobalt", partsItem())
    val rawLithium = register("raw_lithium", partsItem())
    val rawStarmetal = register("raw_starmetal", partsItem())
    val rawTrixite = register("raw_trixite", partsItem())
    val uraniumIngot = register("uranium_ingot", partsItem())
    val u233Ingot = register("uranium233_ingot", partsItem())
    val u235Ingot = register("uranium235_ingot", partsItem())
    val u238Ingot = register("uranium238_ingot", partsItem())
    val th232Ingot = register("thorium232_ingot", partsItem())
    val plutoniumIngot = register("plutonium_ingot", partsItem())
    val pu238Ingot = register("plutonium238_ingot", partsItem())
    val pu239Ingot = register("plutonium239_ingot", partsItem())
    val pu240Ingot = register("plutonium240_ingot", partsItem())
    val pu241Ingot = register("plutonium241_ingot", partsItem())
    val reactorGradePlutoniumIngot = register("reactor_grade_plutonium_ingot", partsItem())
    val americium241Ingot = register("americium241_ingot", partsItem())
    val americium242Ingot = register("americium242_ingot", partsItem())
    val reactorGradeAmericiumIngot = register("reactor_grade_americium_ingot", partsItem())
    val neptuniumIngot = register("neptunium_ingot", partsItem(tooltip = true))
    val polonium210Ingot = register("polonium210_ingot", partsItem())
    val technetium99Ingot = register("technetium99_ingot", partsItem())
    val cobalt60Ingot = register("cobalt60_ingot", partsItem())
    val strontium90Ingot = register("strontium90_ingot", partsItem())
    val gold198Ingot = register("gold198_ingot", partsItem())
    val lead209Ingot = register("lead209_ingot", partsItem())
    val radium226Ingot = register("radium226_ingot", partsItem())
    val titaniumIngot = register("titanium_ingot", partsItem())
    val redCopperIngot = register("red_copper_ingot", partsItem())
    val advancedAlloyIngot = register("advanced_alloy_ingot", partsItem())
    val tungstenIngot = register("tungsten_ingot", partsItem())
    val aluminiumIngot = register("aluminium_ingot", partsItem())
    val steelIngot = register("steel_ingot", partsItem())
    val technetiumSteelIngot = register("technetium_steel_ingot", partsItem())
    val leadIngot = register("lead_ingot", partsItem())
    val bismuthIngot = register("bismuth_ingot", partsItem())
    val arsenicIngot = register("arsenic_ingot", partsItem())
    val tantaliumIngot = register("tantalium_ingot", partsItem(tooltip = true))
    val niobiumIngot = register("niobium_ingot", partsItem())
    val berylliumIngot = register("beryllium_ingot", partsItem())
    val cobaltIngot = register("cobalt_ingot", partsItem())
    val boronIngot = register("boron_ingot", partsItem())
    val graphiteIngot = register("graphite_ingot", partsItem())
    val highSpeedSteelIngot = register("high_speed_steel_ingot", partsItem())
    val polymerIngot = register("polymer_ingot", partsItem())
    val bakeliteIngot = register("bakelite_ingot", partsItem())
    val rubberIngot = register("rubber_ingot", partsItem())
    val schraraniumIngot = register("schraranium_ingot", partsItem())
    val schrabidiumIngot = register("schrabidium_ingot", partsItem(PARTS_RARE))
    val ferricSchrabidateIngot = register("ferric_schrabidate_ingot", partsItem(PARTS_RARE))
    val magnetizedTungstenIngot = register("magnetized_tungsten_ingot", partsItem())
    val combineSteelIngot = register("combine_steel_ingot", partsItem(tooltip = true))
    val soliniumIngot = register("solinium_ingot", partsItem())
    val ghiorsium336Ingot = register("ghiorsium336_ingot", partsItem(PARTS_EPIC, tooltip = true))
    val uraniumFuelIngot = register("uranium_fuel_ingot", partsItem())
    val thoriumFuelIngot = register("thorium_fuel_ingot", partsItem())
    val plutoniumFuelIngot = register("plutonium_fuel_ingot", partsItem())
    val neptuniumFuelIngot = register("neptunium_fuel_ingot", partsItem())
    val moxFuelIngot = register("mox_fuel_ingot", partsItem())
    val americiumFuelIngot = register("americium_fuel_ingot", partsItem())
    val schrabidiumFuelIngot = register("schrabidium_fuel_ingot", partsItem())
    val highEnrichedSchrabidiumFuelIngot = register("high_enriched_schrabidium_fuel_ingot", partsItem())
    val lowEnrichedSchrabidiumFuelIngot = register("low_enriched_schrabidium_fuel_ingot", partsItem())
    val australiumIngot = register("australium_ingot", partsItem(PARTS_UNCOMMON))
    val weidaniumIngot = register("weidanium_ingot", partsItem(PARTS_UNCOMMON))
    val reiiumIngot = register("reiium_ingot", partsItem(PARTS_UNCOMMON))
    val unobtainiumIngot = register("unobtainium_ingot", partsItem(PARTS_UNCOMMON))
    val daffergonIngot = register("daffergon_ingot", partsItem(PARTS_UNCOMMON))
    val verticiumIngot = register("verticium_ingot", partsItem(PARTS_UNCOMMON))
    val lanthanumIngot = register("lanthanum_ingot", partsItem())
    val actiniumIngot = register("actinium227_ingot", partsItem())
    val deshIngot = register("desh_ingot", partsItem())
    val starmetalIngot = register("starmetal_ingot", partsItem())
    val saturniteIngot = register("saturnite_ingot", partsItem(PARTS_RARE))
    val euphemiumIngot = register("euphemium_ingot", partsItem(PARTS_EPIC, tooltip = true))
    val dineutroniumIngot = register("dineutronium_ingot", partsItem())
    val electroniumIngot = register("electronium_ingot", partsItem())
    val osmiridiumIngot = register("osmiridium_ingot", partsItem(PARTS_RARE))
    val whitePhosphorusIngot = register("white_phosphorus_ingot", partsItem())
    val semtexBar = register("semtex_bar", defaultItem(Properties().tab(CreativeTabs.Parts).food(FoodProperties.Builder().nutrition(4).saturationMod(0.5F).build()), tooltip = true))
    val lithiumCube = register("lithium_cube", partsItem())
    val solidFuelCube = register("solid_fuel_cube", partsItem())
    val solidRocketFuelCube = register("solid_rocket_fuel_cube", partsItem())
    val fiberglassSheet = register("fiberglass_sheet", partsItem(tooltip = true))
    val asbestosSheet = register("asbestos_sheet", partsItem(tooltip = true))
    val uraniumBillet = register("uranium_billet", partsItem())
    val u233Billet = register("uranium233_billet", partsItem())
    val u235Billet = register("uranium235_billet", partsItem())
    val u238Billet = register("uranium238_billet", partsItem())
    val th232Billet = register("thorium232_billet", partsItem())
    val plutoniumBillet = register("plutonium_billet", partsItem())
    val pu238Billet = register("plutonium238_billet", partsItem())
    val pu239Billet = register("plutonium239_billet", partsItem())
    val pu240Billet = register("plutonium240_billet", partsItem())
    val pu241Billet = register("plutonium241_billet", partsItem())
    val reactorGradePlutoniumBillet = register("reactor_grade_plutonium_billet", partsItem())
    val americium241Billet = register("americium241_billet", partsItem())
    val americium242Billet = register("americium242_billet", partsItem())
    val reactorGradeAmericiumBillet = register("reactor_grade_americium_billet", partsItem())
    val neptuniumBillet = register("neptunium_billet", partsItem())
    val poloniumBillet = register("polonium_billet", partsItem())
    val technetium99Billet = register("technetium99_billet", partsItem())
    val cobaltBillet = register("cobalt_billet", partsItem())
    val cobalt60Billet = register("cobalt60_billet", partsItem())
    val strontium90Billet = register("strontium90_billet", partsItem())
    val gold198Billet = register("gold198_billet", partsItem())
    val lead209Billet = register("lead209_billet", partsItem())
    val radium226Billet = register("radium226_billet", partsItem())
    val actinium227Billet = register("actinium227_billet", partsItem())
    val schrabidiumBillet = register("schrabidium_billet", partsItem(PARTS_RARE))
    val soliniumBillet = register("solinium_billet", partsItem())
    val ghiorsium336Billet = register("ghiorsium336_billet", partsItem())
    val australiumBillet = register("australium_billet", partsItem(PARTS_UNCOMMON))
    val lesserAustraliumBillet = register("lesser_australium_billet", partsItem(PARTS_UNCOMMON))
    val greaterAustraliumBillet = register("greater_australium_billet", partsItem(PARTS_UNCOMMON))
    val uraniumFuelBillet = register("uranium_fuel_billet", partsItem())
    val thoriumFuelBillet = register("thorium_fuel_billet", partsItem())
    val plutoniumFuelBillet = register("plutonium_fuel_billet", partsItem())
    val neptuniumFuelBillet = register("neptunium_fuel_billet", partsItem())
    val moxFuelBillet = register("mox_fuel_billet", partsItem())
    val americiumFuelBillet = register("americium_fuel_billet", partsItem())
    val lowEnrichedSchrabidiumFuelBillet = register("low_enriched_schrabidium_fuel_billet", partsItem())
    val schrabidiumFuelBillet = register("schrabidium_fuel_billet", partsItem())
    val highEnrichedSchrabidiumFuelBillet = register("high_enriched_schrabidium_fuel_billet", partsItem())
    val po210BeBillet = register("polonium210_beryllium_billet", partsItem())
    val ra226BeBillet = register("radium226_beryllium_billet", partsItem())
    val pu238BeBillet = register("plutonium238_beryllium_billet", partsItem())
    val berylliumBillet = register("beryllium_billet", partsItem())
    val bismuthBillet = register("bismuth_billet", partsItem())
    val zirconiumBillet = register("zirconium_billet", partsItem())
    val bismuthZfbBillet = register("bismuth_zfb_billet", partsItem())
    val pu241ZfbBillet = register("plutonium241_zfb_billet", partsItem())
    val reactorGradeAmericiumZfbBillet = register("reactor_grade_americium_zfb_billet", partsItem())
    val yharoniteBillet = register("yharonite_billet", partsItem())
    val flashgoldBillet = register("flashgold_billet", partsItem(PARTS_UNCOMMON))
    val flashleadBillet = register("flashlead_billet", partsItem(PARTS_UNCOMMON, tooltip = true))
    val nuclearWasteBillet = register("nuclear_waste_billet", partsItem())
    val mercuryDroplet = register("mercury_droplet", partsItem())
    val mercuryBottle = register("mercury_bottle", partsItem())
    val coke = register("coke") { FuelItem(3200, PARTS_COMMON) }
    val lignite = register("lignite") { FuelItem(1200, PARTS_COMMON) }
    val ligniteBriquette = register("lignite_briquette") { FuelItem(1600, PARTS_COMMON) }
    val sulfur = register("sulfur", partsItem())
    val niter = register("niter", partsItem())
    val fluorite = register("fluorite", partsItem())
    val coalPowder = register("coal_powder", partsItem())
    val ironPowder = register("iron_powder", partsItem())
    val goldPowder = register("gold_powder", partsItem())
    val lapisLazuliPowder = register("lapis_lazuli_powder", partsItem())
    val quartzPowder = register("quartz_powder", partsItem())
    val diamondPowder = register("diamond_powder", partsItem())
    val emeraldPowder = register("emerald_powder", partsItem())
    val uraniumPowder = register("uranium_powder", partsItem())
    val thoriumPowder = register("thorium_powder", partsItem(PARTS_EPIC))
    val plutoniumPowder = register("plutonium_powder", partsItem())
    val neptuniumPowder = register("neptunium_powder", partsItem(PARTS_EPIC))
    val poloniumPowder = register("polonium_powder", partsItem())
    val titaniumPowder = register("titanium_powder", partsItem())
    val copperPowder = register("copper_powder", partsItem())
    val redCopperPowder = register("red_copper_powder", partsItem())
    val advancedAlloyPowder = register("advanced_alloy_powder", partsItem())
    val tungstenPowder = register("tungsten_powder", partsItem())
    val aluminiumPowder = register("aluminium_powder", partsItem())
    val steelPowder = register("steel_powder", partsItem())
    val leadPowder = register("lead_powder", partsItem())
    val yellowcake = register("yellowcake", partsItem())
    val berylliumPowder = register("beryllium_powder", partsItem())
    val highSpeedSteelPowder = register("high_speed_steel_powder", partsItem())
    val polymerPowder = register("polymer_powder", partsItem())
    val schrabidiumPowder = register("schrabidium_powder", partsItem(PARTS_RARE))
    val magnetizedTungstenPowder = register("magnetized_tungsten_powder", partsItem())
    val chlorophytePowder = register("chlorophyte_powder", partsItem())
    val combineSteelPowder = register("combine_steel_powder", partsItem())
    val lithiumPowder = register("lithium_powder", partsItem())
    val lignitePowder = register("lignite_powder", partsItem())
    val neodymiumPowder = register("neodymium_powder", partsItem(PARTS_EPIC))
    val australiumPowder = register("australium_powder", partsItem(PARTS_UNCOMMON))
    val weidaniumPowder = register("weidanium_powder", partsItem(PARTS_UNCOMMON))
    val reiiumPowder = register("reiium_powder", partsItem(PARTS_UNCOMMON))
    val unobtainiumPowder = register("unobtainium_powder", partsItem(PARTS_UNCOMMON))
    val daffergonPowder = register("daffergon_powder", partsItem(PARTS_UNCOMMON))
    val verticiumPowder = register("verticium_powder", partsItem(PARTS_UNCOMMON))
    val cobaltPowder = register("cobalt_powder", partsItem(PARTS_EPIC))
    val niobiumPowder = register("niobium_powder", partsItem(PARTS_EPIC))
    val ceriumPowder = register("cerium_powder", partsItem(PARTS_EPIC))
    val lanthanumPowder = register("lanthanum_powder", partsItem())
    val actiniumPowder = register("actinium227_powder", partsItem())
    val asbestosPowder = register("asbestos_powder", partsItem(tooltip = true))
    val enchantmentPowder = register("enchantment_powder", partsItem())
    val cloudResidue = register("cloud_residue", partsItem())
    val thermonuclearAshes = register("thermonuclear_ashes", partsItem())
    val semtexMix = register("semtex_mix", partsItem())
    val deshMix = register("desh_mix", partsItem())
    val deshReadyMix = register("desh_ready_mix", partsItem())
    val deshPowder = register("desh_powder", partsItem())
    val nitaniumMix = register("nitanium_mix", partsItem())
    val sparkMix = register("spark_mix", partsItem())
    val meteoritePowder = register("meteorite_powder", partsItem())
    val euphemiumPowder = register("euphemium_powder", partsItem(PARTS_EPIC, tooltip = true))
    val dineutroniumPowder = register("dineutronium_powder", partsItem())
    val desaturatedRedstone = register("desaturated_redstone", partsItem())
    val dust = register("dust") { FuelItem(400, PARTS_COMMON) }
    val tinyLithiumPowder = register("tiny_lithium_powder", partsItem())
    val tinyNeodymiumPowder = register("tiny_neodymium_powder", partsItem())
    val tinyCobaltPowder = register("tiny_cobalt_powder", partsItem())
    val tinyNiobiumPowder = register("tiny_niobium_powder", partsItem())
    val tinyCeriumPowder = register("tiny_cerium_powder", partsItem())
    val tinyLanthanumPowder = register("tiny_lanthanum_powder", partsItem())
    val tinyActiniumPowder = register("tiny_actinium_powder", partsItem())
    val tinyMeteoritePowder = register("tiny_meteorite_powder", partsItem())
    val redPhosphorus = register("red_phosphorus", partsItem(tooltip = true))
    val cryoPowder = register("cryo_powder", partsItem())
    val poisonPowder = register("poison_powder", partsItem(tooltip = true))
    val thermite = register("thermite", partsItem())
    val energyPowder = register("energy_powder", partsItem(PARTS_UNCOMMON))
    val cordite = register("cordite", partsItem())
    val ballistite = register("ballistite", partsItem())
    val coalCrystals = register("coal_crystals", partsItem())
    val ironCrystals = register("iron_crystals", partsItem())
    val goldCrystals = register("gold_crystals", partsItem())
    val redstoneCrystals = register("redstone_crystals", partsItem())
    val lapisCrystals = register("lapis_crystals", partsItem())
    val diamondCrystals = register("diamond_crystals", partsItem())
    val uraniumCrystals = register("uranium_crystals", partsItem())
    val thoriumCrystals = register("thorium_crystals", partsItem())
    val plutoniumCrystals = register("plutonium_crystals", partsItem())
    val titaniumCrystals = register("titanium_crystals", partsItem())
    val sulfurCrystals = register("sulfur_crystals", partsItem())
    val niterCrystals = register("niter_crystals", partsItem())
    val copperCrystals = register("copper_crystals", partsItem())
    val tungstenCrystals = register("tungsten_crystals", partsItem())
    val aluminiumCrystals = register("aluminium_crystals", partsItem())
    val fluoriteCrystals = register("fluorite_crystals", partsItem())
    val berylliumCrystals = register("beryllium_crystals", partsItem())
    val leadCrystals = register("lead_crystals", partsItem())
    val schraraniumCrystals = register("schraranium_crystals", partsItem())
    val schrabidiumCrystals = register("schrabidium_crystals", partsItem())
    val rareEarthCrystals = register("rare_earth_crystals", partsItem())
    val redPhosphorusCrystals = register("red_phosphorus_crystals", partsItem())
    val lithiumCrystals = register("lithium_crystals", partsItem())
    val cobaltCrystals = register("cobalt_crystals", partsItem())
    val starmetalCrystals = register("starmetal_crystals", partsItem())
    val trixiteCrystals = register("trixite_crystals", partsItem())
    val osmiridiumCrystals = register("osmiridium_crystals", partsItem(PARTS_RARE))
    val volcanicGem = register("volcanic_gem", partsItem(PARTS_UNCOMMON))
    val neodymiumFragment = register("neodymium_fragment", partsItem())
    val cobaltFragment = register("cobalt_fragment", partsItem())
    val niobiumFragment = register("niobium_fragment", partsItem())
    val ceriumFragment = register("cerium_fragment", partsItem())
    val lanthanumFragment = register("lanthanum_fragment", partsItem())
    val actiniumFragment = register("actinium227_fragment", partsItem())
    val meteoriteFragment = register("meteorite_fragment", partsItem())
    val biomass = register("biomass", partsItem())
    val compressedBiomass = register("compressed_biomass", partsItem())
    val uraniumNugget = register("uranium_nugget", partsItem())
    val u233Nugget = register("uranium233_nugget", partsItem())
    val u235Nugget = register("uranium235_nugget", partsItem())
    val u238Nugget = register("uranium238_nugget", partsItem())
    val th232Nugget = register("thorium232_nugget", partsItem())
    val plutoniumNugget = register("plutonium_nugget", partsItem())
    val pu238Nugget = register("plutonium238_nugget", partsItem())
    val pu239Nugget = register("plutonium239_nugget", partsItem())
    val pu240Nugget = register("plutonium240_nugget", partsItem())
    val pu241Nugget = register("plutonium241_nugget", partsItem())
    val reactorGradePlutoniumNugget = register("reactor_grade_plutonium_nugget", partsItem())
    val americium241Nugget = register("americium241_nugget", partsItem())
    val americium242Nugget = register("americium242_nugget", partsItem())
    val reactorGradeAmericiumNugget = register("reactor_grade_americium_nugget", partsItem())
    val neptuniumNugget = register("neptunium_nugget", partsItem())
    val poloniumNugget = register("polonium210_nugget", partsItem())
    val cobaltNugget = register("cobalt_nugget", partsItem())
    val cobalt60Nugget = register("cobalt60_nugget", partsItem())
    val strontium90Nugget = register("strontium90_nugget", partsItem())
    val technetium99Nugget = register("technetium99_nugget", partsItem())
    val gold198Nugget = register("gold198_nugget", partsItem())
    val lead209Nugget = register("lead209_nugget", partsItem())
    val radium226Nugget = register("radium226_nugget", partsItem())
    val actinium227Nugget = register("actinium227_nugget", partsItem())
    val leadNugget = register("lead_nugget", partsItem())
    val bismuthNugget = register("bismuth_nugget", partsItem())
    val arsenicNugget = register("arsenic_nugget", partsItem())
    val tantaliumNugget = register("tantalium_nugget", partsItem(tooltip = true))
    val berylliumNugget = register("beryllium_nugget", partsItem())
    val schrabidiumNugget = register("schrabidium_nugget", partsItem(PARTS_RARE))
    val soliniumNugget = register("solinium_nugget", partsItem())
    val ghiorsium336Nugget = register("ghiorsium336_nugget", partsItem(PARTS_EPIC, tooltip = true))
    val uraniumFuelNugget = register("uranium_fuel_nugget", partsItem())
    val thoriumFuelNugget = register("thorium_fuel_nugget", partsItem())
    val plutoniumFuelNugget = register("plutonium_fuel_nugget", partsItem())
    val neptuniumFuelNugget = register("neptunium_fuel_nugget", partsItem())
    val moxFuelNugget = register("mox_fuel_nugget", partsItem())
    val americiumFuelNugget = register("americium_fuel_nugget", partsItem())
    val schrabidiumFuelNugget = register("schrabidium_fuel_nugget", partsItem())
    val highEnrichedSchrabidiumFuelNugget = register("high_enriched_schrabidium_fuel_nugget", partsItem())
    val lowEnrichedSchrabidiumFuelNugget = register("low_enriched_schrabidium_fuel_nugget", partsItem())
    val australiumNugget = register("australium_nugget", partsItem(PARTS_UNCOMMON))
    val weidaniumNugget = register("weidanium_nugget", partsItem(PARTS_UNCOMMON))
    val reiiumNugget = register("reiium_nugget", partsItem(PARTS_UNCOMMON))
    val unobtainiumNugget = register("unobtainium_nugget", partsItem(PARTS_UNCOMMON))
    val daffergonNugget = register("daffergon_nugget", partsItem(PARTS_UNCOMMON))
    val verticiumNugget = register("verticium_nugget", partsItem(PARTS_UNCOMMON))
    val deshNugget = register("desh_nugget", partsItem())
    val euphemiumNugget = register("euphemium_nugget", partsItem(PARTS_EPIC, tooltip = true))
    val dineutroniumNugget = register("dineutronium_nugget", partsItem())
    val osmiridiumNugget = register("osmiridium_nugget", partsItem(PARTS_RARE))
    val ironPlate = register("iron_plate", partsItem())
    val goldPlate = register("gold_plate", partsItem())
    val titaniumPlate = register("titanium_plate", partsItem())
    val aluminiumPlate = register("aluminium_plate", partsItem())
    val steelPlate = register("steel_plate", partsItem())
    val leadPlate = register("lead_plate", partsItem())
    val copperPlate = register("copper_plate", partsItem())
    val advancedAlloyPlate = register("advanced_alloy_plate", partsItem())
    val neutronReflector = register("neutron_reflector", partsItem())
    val schrabidiumPlate = register("schrabidium_plate", partsItem())
    val combineSteelPlate = register("combine_steel_plate", partsItem())
    val mixedPlate = register("mixed_plate", partsItem())
    val saturnitePlate = register("saturnite_plate", partsItem())
    val paAAlloyPlate = register("paa_alloy_plate", partsItem())
    val insulator = register("insulator", partsItem())
    val kevlarCeramicCompound = register("kevlar_ceramic_compound", partsItem())
    val dalekaniumPlate = register("angry_metal", partsItem())
    val deshCompoundPlate = register("desh_compound_plate", partsItem())
    val euphemiumCompoundPlate = register("euphemium_compound_plate", partsItem())
    val dineutroniumCompoundPlate = register("dineutronium_compound_plate", partsItem())
    val copperPanel = register("copper_panel", partsItem())
    val highSpeedSteelBolt = register("high_speed_steel_bolt", partsItem())
    val tungstenBolt = register("tungsten_bolt", partsItem())
    val reinforcedTurbineShaft = register("reinforced_turbine_shaft", partsItem())
    val hazmatCloth = register("hazmat_cloth", partsItem())
    val advancedHazmatCloth = register("advanced_hazmat_cloth", partsItem())
    val leadReinforcedHazmatCloth = register("lead_reinforced_hazmat_cloth", partsItem())
    val fireProximityCloth = register("fire_proximity_cloth", partsItem())
    val activatedCarbonFilter = register("activated_carbon_filter", partsItem())
    val aluminiumWire = register("aluminium_wire", partsItem())
    val copperWire = register("copper_wire", partsItem())
    val tungstenWire = register("tungsten_wire", partsItem())
    val redCopperWire = register("red_copper_wire", partsItem())
    val superConductor = register("super_conductor", partsItem())
    val goldWire = register("gold_wire", partsItem())
    val schrabidiumWire = register("schrabidium_wire", partsItem())
    val highTemperatureSuperConductor = register("high_temperature_super_conductor", partsItem())
    val copperCoil = register("copper_coil", partsItem())
    val ringCoil = register("ring_coil", partsItem())
    val superConductingCoil = register("super_conducting_coil", partsItem())
    val superConductingRingCoil = register("super_conducting_ring_coil", partsItem())
    val goldCoil = register("gold_coil", partsItem())
    val goldRingCoil = register("gold_ring_coil", partsItem())
    val heatingCoil = register("heating_coil", partsItem())
    val highTemperatureSuperConductingCoil = register("high_temperature_super_conducting_coil", partsItem())
    val steelTank = register("steel_tank", partsItem())
    val motor = register("motor", partsItem())
    val centrifugeElement = register("centrifuge_element", partsItem())
    val centrifugeTower = register("centrifuge_tower", partsItem())
    val deeMagnets = register("dee_magnets", partsItem())
    val flatMagnet = register("flat_magnet", partsItem())
    val cyclotronTower = register("cyclotron_tower", partsItem())
    val breedingReactorCore = register("breeding_reactor_core", partsItem())
    val rtgUnit = register("rtg_unit", partsItem())
    val thermalDistributionUnit = register("thermal_distribution_unit", partsItem())
    val endothermicDistributionUnit = register("endothermic_distribution_unit", partsItem())
    val exothermicDistributionUnit = register("exothermic_distribution_unit", partsItem())
    val gravityManipulator = register("gravity_manipulator", partsItem())
    val steelPipes = register("steel_pipes", partsItem())
    val titaniumDrill = register("titanium_drill", partsItem())
    val photovoltaicPanel = register("photovoltaic_panel", partsItem())
    val chlorinePinwheel = register("chlorine_pinwheel", partsItem())
    val telepad = register("telepad", partsItem())
    val entanglementKit = register("entanglement_kit", partsItem(tooltip = true))
    val stabilizerComponent = register("stabilizer_component", partsItem())
    val emitterComponent = register("emitter_component", partsItem())
    val aluminiumCap = register("aluminium_cap", partsItem())
    val smallSteelShell = register("small_steel_shell", partsItem())
    val smallAluminiumShell = register("small_aluminium_shell", partsItem())
    val bigSteelShell = register("big_steel_shell", partsItem())
    val bigAluminiumShell = register("big_aluminium_shell", partsItem())
    val bigTitaniumShell = register("big_titanium_shell", partsItem())
    val flatSteelCasing = register("flat_steel_casing", partsItem())
    val smallSteelGridFins = register("small_steel_grid_fins", partsItem())
    val bigSteelGridFins = register("big_steel_grid_fins", partsItem())
    val largeSteelFins = register("large_steel_fins", partsItem())
    val smallTitaniumFins = register("small_titanium_fins", partsItem())
    val steelSphere = register("steel_sphere", partsItem())
    val steelPedestal = register("steel_pedestal", partsItem())
    val dysfunctionalNuclearReactor = register("dysfunctional_nuclear_reactor", partsItem())
    val largeSteelRotor = register("large_steel_rotor", partsItem())
    val generatorBody = register("generator_body", partsItem())
    val titaniumBlade = register("titanium_blade", partsItem())
    val tungstenReinforcedBlade = register("tungsten_reinforced_blade", partsItem())
    val titaniumSteamTurbine = register("titanium_steam_turbine", partsItem())
    val reinforcedTurbofanBlades = register("reinforced_turbofan_blades", partsItem())
    val generatorFront = register("generator_front", partsItem())
    val toothpicks = register("toothpicks", partsItem())
    val ductTape = register("duct_tape", partsItem())
    val clayCatalyst = register("clay_catalyst", partsItem())
    val smallMissileAssembly = register("small_missile_assembly", partsItem())
    val smallWarhead = register("small_warhead", partsItem())
    val mediumWarhead = register("medium_warhead", partsItem())
    val largeWarhead = register("large_warhead", partsItem())
    val smallIncendiaryWarhead = register("small_incendiary_warhead", partsItem())
    val mediumIncendiaryWarhead = register("medium_incendiary_warhead", partsItem())
    val largeIncendiaryWarhead = register("large_incendiary_warhead", partsItem())
    val smallClusterWarhead = register("small_cluster_warhead", partsItem())
    val mediumClusterWarhead = register("medium_cluster_warhead", partsItem())
    val largeClusterWarhead = register("large_cluster_warhead", partsItem())
    val smallBunkerBusterWarhead = register("small_bunker_busting_warhead", partsItem())
    val mediumBunkerBusterWarhead = register("medium_bunker_busting_warhead", partsItem())
    val largeBunkerBusterWarhead = register("large_bunker_busting_warhead", partsItem())
    val nuclearWarhead = register("nuclear_warhead", partsItem())
    val thermonuclearWarhead = register("thermonuclear_warhead", partsItem())
    val endothermicWarhead = register("endothermic_warhead", partsItem())
    val exothermicWarhead = register("exothermic_warhead", partsItem())
    val smallFuelTank = register("small_fuel_tank", partsItem())
    val mediumFuelTank = register("medium_fuel_tank", partsItem())
    val largeFuelTank = register("large_fuel_tank", partsItem())
    val smallThruster = register("small_thruster", partsItem())
    val mediumThruster = register("medium_thruster", partsItem())
    val largeThruster = register("large_thruster", partsItem())
    val lvnNuclearRocketEngine = register("lv_n_nuclear_rocket_engine", partsItem())
    val satelliteBase = register("satellite_base", partsItem())
    val highGainOpticalCamera = register("high_gain_optical_camera", partsItem())
    val m700SurveyScanner = register("m700_survey_scanner", partsItem())
    val radarDish = register("radar_dish", partsItem())
    val deathRay = register("death_ray", partsItem())
    val xeniumResonator = register("xenium_resonator", partsItem())
    val size10Connector = register("size_10_connector", partsItem())
    val size15Connector = register("size_15_connector", partsItem())
    val size20Connector = register("size_20_connector", partsItem())
    val hunterChopperCockpit = register("hunter_chopper_cockpit", partsItem())
    val emplacementGun = register("emplacement_gun", partsItem())
    val hunterChopperBody = register("hunter_chopper_body", partsItem())
    val hunterChopperTail = register("hunter_chopper_tail", partsItem())
    val hunterChopperWing = register("hunter_chopper_wing", partsItem())
    val hunterChopperRotorBlades = register("hunter_chopper_rotor_blades", partsItem())
    val combineScrapMetal = register("combine_scrap_metal", partsItem())
    val heavyHammerHead = register("heavy_hammer_head", partsItem())
    val heavyAxeHead = register("heavy_axe_head", partsItem())
    val reinforcedPolymerHandle = register("reinforced_polymer_handle", partsItem())
    val basicCircuitAssembly = register("basic_circuit_assembly", partsItem())
    val basicCircuit = register("basic_circuit", partsItem())
    val enhancedCircuit = register("enhanced_circuit", partsItem())
    val advancedCircuit = register("advanced_circuit", partsItem())
    val overclockedCircuit = register("overclocked_circuit", partsItem())
    val highPerformanceCircuit = register("high_performance_circuit", partsItem(PARTS_RARE))
    val militaryGradeCircuitBoardTier1 = register("military_grade_circuit_board_tier_1", partsItem())
    val militaryGradeCircuitBoardTier2 = register("military_grade_circuit_board_tier_2", partsItem())
    val militaryGradeCircuitBoardTier3 = register("military_grade_circuit_board_tier_3", partsItem())
    val militaryGradeCircuitBoardTier4 = register("military_grade_circuit_board_tier_4", partsItem())
    val militaryGradeCircuitBoardTier5 = register("military_grade_circuit_board_tier_5", partsItem())
    val militaryGradeCircuitBoardTier6 = register("military_grade_circuit_board_tier_6", partsItem())
    val revolverMechanism = register("revolver_mechanism", partsItem())
    val advancedRevolverMechanism = register("advanced_revolver_mechanism", partsItem())
    val rifleMechanism = register("rifle_mechanism", partsItem())
    val advancedRifleMechanism = register("advanced_rifle_mechanism", partsItem())
    val launcherMechanism = register("launcher_mechanism", partsItem())
    val advancedLauncherMechanism = register("advanced_launcher_mechanism", partsItem())
    val highTechWeaponMechanism = register("high_tech_weapon_mechanism", partsItem())
    val point357MagnumPrimer = register("point_357_magnum_primer", partsItem())
    val point44MagnumPrimer = register("point_44_magnum_primer", partsItem())
    val smallCaliberPrimer = register("small_caliber_primer", partsItem())
    val largeCaliberPrimer = register("large_caliber_primer", partsItem())
    val buckshotPrimer = register("buckshot_primer", partsItem())
    val point357MagnumCasing = register("point_357_magnum_casing", partsItem())
    val point44MagnumCasing = register("point_44_magnum_casing", partsItem())
    val smallCaliberCasing = register("small_caliber_casing", partsItem())
    val largeCaliberCasing = register("large_caliber_casing", partsItem())
    val buckshotCasing = register("buckshot_casing", partsItem())
    val ironBulletAssembly = register("iron_bullet_assembly", partsItem())
    val leadBulletAssembly = register("lead_bullet_assembly", partsItem())
    val glassBulletAssembly = register("glass_bullet_assembly", partsItem())
    val goldBulletAssembly = register("gold_bullet_assembly", partsItem())
    val schrabidiumBulletAssembly = register("schrabidium_bullet_assembly", partsItem())
    val nightmareBulletAssembly = register("nightmare_bullet_assembly", partsItem())
    val deshBulletAssembly = register("desh_bullet_assembly", partsItem())
    val point44MagnumAssembly = register("point_44_magnum_assembly", partsItem())
    val nineMmAssembly = register("9_mm_assembly", partsItem())
    val fivePoint56mmAssembly = register("5_point_56_mm_assembly", partsItem())
    val point22LRAssembly = register("point_22_lr_assembly", partsItem())
    val point5mmAssembly = register("point_5_mm_assembly", partsItem())
    val point50AEAssembly = register("point_50_ae_assembly", partsItem())
    val point50BMGAssembly = register("point_50_bmg_assembly", partsItem())
    val silverBulletCasing = register("silver_bullet_casing", partsItem())
    val twelvePoint8cmStarmetalHighEnergyShell = register("12_point_8_cm_starmetal_high_energy_shell", partsItem())
    val twelvePoint8cmNuclearShell = register("12_point_8_cm_nuclear_shell", partsItem())
    val twelvePoint8cmDUShell = register("12_point_8_cm_du_shell", partsItem())
    val cableDrum = register("cable_drum", partsItem(tooltip = true))
    val paintingOfACartoonPony = register("painting_of_a_cartoon_pony", partsItem(tooltip = true))
    val conspiracyTheory = register("conspiracy_theory", partsItem(tooltip = true))
    val politicalTopic = register("political_topic", partsItem(tooltip = true))
    val ownOpinion = register("own_opinion", partsItem(tooltip = true))
    val explosivePellets = register("explosive_pellets", partsItem(tooltip = true))
    val leadPellets = register("lead_pellets", partsItem())
    val flechettes = register("flechettes", partsItem())
    val poisonGasCartridge = register("poison_gas_cartridge", partsItem(tooltip = true))
    val magnetron = register("magnetron", partsItem())
    val denseCoalCluster = register("dense_coal_cluster", partsItem())
    val burntBark = register("burnt_bark", partsItem(tooltip = true))
    val machineUpgradeTemplate = register("machine_upgrade_template") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val blankRune = register("blank_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val isaRune = register("isa_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val dagazRune = register("dagaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val hagalazRune = register("hagalaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val jeraRune = register("jera_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val thurisazRune = register("thurisaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val burnedOutQuadSchrabidiumFuelRod = register("burned_out_quad_schrabidium_rod", defaultItem(Properties().tab(CreativeTabs.Parts).stacksTo(1).rarity(Rarity.EPIC), tooltip = true))
    val scrap = register("scrap") { FuelItem(800, PARTS_COMMON)}
    val hotDepletedUraniumFuel = register("hot_depleted_uranium_fuel", partsItem())
    val hotDepletedThoriumFuel = register("hot_depleted_thorium_fuel", partsItem())
    val hotDepletedPlutoniumFuel = register("hot_depleted_plutonium_fuel", partsItem())
    val hotDepletedMOXFuel = register("hot_depleted_mox_fuel", partsItem())
    val hotDepletedSchrabidiumFuel = register("hot_depleted_schrabidium_fuel", partsItem())
    val depletedUraniumFuel = register("depleted_uranium_fuel", partsItem())
    val depletedThoriumFuel = register("depleted_thorium_fuel", partsItem())
    val depletedPlutoniumFuel = register("depleted_plutonium_fuel", partsItem())
    val depletedMOXFuel = register("depleted_mox_fuel", partsItem())
    val depletedSchrabidiumFuel = register("depleted_schrabidium_fuel", partsItem())
    val trinitite = register("trinitite", partsItem())
    val nuclearWaste = register("nuclear_waste", partsItem())
    val tinyNuclearWaste = register("tiny_nuclear_waste", partsItem())
    val crystalHorn = register("crystal_horn", partsItem(tooltip = true))
    val charredCrystal = register("charred_crystal", partsItem(tooltip = true))

    // Items and Fuel

    val battery = register("battery") { BatteryItem(20_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val redstonePowerCell = register("redstone_power_cell") { BatteryItem(60_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val sixfoldRedstonePowerCell = register("sixfold_redstone_power_cell") { BatteryItem(360_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val twentyFourFoldRedstonePowerCell = register("twenty_four_fold_redstone_power_cell") { BatteryItem(1_440_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val advancedBattery = register("advanced_battery") { BatteryItem(80_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val advancedPowerCell = register("advanced_power_cell") { BatteryItem(240_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val quadrupleAdvancedPowerCell = register("quadruple_advanced_power_cell") { BatteryItem(960_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val twelveFoldAdvancedPowerCell = register("twelvefold_advanced_power_cell") { BatteryItem(2_880_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val lithiumBattery = register("lithium_battery") { BatteryItem(1_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val lithiumPowerCell = register("lithium_power_cell") { BatteryItem(3_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val tripleLithiumPowerCell = register("triple_lithium_power_cell") { BatteryItem(9_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val sixfoldLithiumPowerCell = register("sixfold_lithium_power_cell") { BatteryItem(18_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val schrabidiumBattery = register("schrabidium_battery") { BatteryItem(4_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val schrabidiumPowerCell = register("schrabidium_power_cell") { BatteryItem(12_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val doubleSchrabidiumPowerCell = register("double_schrabidium_power_cell") { BatteryItem(24_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val quadrupleSchrabidiumPowerCell = register("quadruple_schrabidium_power_cell") { BatteryItem(48_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val sparkBattery = register("spark_battery") { BatteryItem(400_000_000, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val offBrandSparkBattery = register("off_brand_spark_battery") { BatteryItem(20_000_000, 160_000, 800_000, Properties().tab(CreativeTabs.Items)) }
    val sparkPowerCell = register("spark_power_cell") { BatteryItem(2_400_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneCarBattery = register("spark_arcane_car_battery") { BatteryItem(10_000_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneEnergyStorageArray = register("spark_arcane_energy_storage_array") { BatteryItem(40_000_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneMassEnergyVoid = register("spark_arcane_mass_energy_void") { BatteryItem(400_000_000_000L, 80_000_000, 80_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneDiracSea = register("spark_arcane_dirac_sea") { BatteryItem(1_000_000_000_000L, 80_000_000, 80_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkSolidSpaceTimeCrystal = register("spark_solid_space_time_crystal") { BatteryItem(4_000_000_000_000L, 800_000_000, 800_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkLudicrousPhysicsDefyingEnergyStorageUnit = register("spark_ludicrous_physics_defying_energy_storage_unit") { BatteryItem(400_000_000_000_000L, 800_000_000, 800_000_000, Properties().tab(CreativeTabs.Items)) }
    // TODO electronium cube
    val infiniteBattery = register("infinite_battery") { BatteryOfInfinityItem(Properties().tab(CreativeTabs.Items)) }
    val singleUseBattery = register("single_use_battery") { BatteryItem(6_000, 0, 400, Properties().tab(CreativeTabs.Items)) }
    val largeSingleUseBattery = register("large_single_use_battery") { BatteryItem(14_000, 0, 400, Properties().tab(CreativeTabs.Items)) }
    val potatoBattery = register("potato_battery") { BatteryItem(400, 0, 400, Properties().tab(CreativeTabs.Items)) }
    // TODO PotatOS
    val steamPoweredEnergyStorageTank = register("steam_powered_energy_storage_tank") { BatteryItem(240_000, 1_200, 24_000, Properties().tab(CreativeTabs.Items)) }
    val largeSteamPoweredEnergyStorageTank = register("large_steam_powered_energy_storage_tank") { BatteryItem(400_000, 2_000, 40_000, Properties().tab(CreativeTabs.Items)) }

    val stoneFlatStamp = register("stone_flat_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stonePlateStamp = register("stone_plate_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stoneWireStamp = register("stone_wire_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stoneCircuitStamp = register("stone_circuit_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val ironFlatStamp = register("iron_flat_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironPlateStamp = register("iron_plate_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironWireStamp = register("iron_wire_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironCircuitStamp = register("iron_circuit_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val steelFlatStamp = register("steel_flat_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelPlateStamp = register("steel_plate_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelWireStamp = register("steel_wire_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelCircuitStamp = register("steel_circuit_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val titaniumFlatStamp = register("titanium_flat_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumPlateStamp = register("titanium_plate_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumWireStamp = register("titanium_wire_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumCircuitStamp = register("titanium_circuit_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val obsidianFlatStamp = register("obsidian_flat_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianPlateStamp = register("obsidian_plate_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianWireStamp = register("obsidian_wire_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianCircuitStamp = register("obsidian_circuit_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val schrabidiumFlatStamp = register("schrabidium_flat_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumPlateStamp = register("schrabidium_plate_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumWireStamp = register("schrabidium_wire_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumCircuitStamp = register("schrabidium_circuit_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }

    val screwdriver = register("screwdriver") { ScrewdriverItem(Properties().durability(100).tab(CreativeTabs.Items)) }
    val deshScrewdriver = register("desh_screwdriver") { ScrewdriverItem(Properties().stacksTo(1).tab(CreativeTabs.Items)) }

    val speedUpgradeMk1 = register("speed_upgrade_mk1") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), SpeedUpgrade(1)) }
    val speedUpgradeMk2 = register("speed_upgrade_mk2") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), SpeedUpgrade(2)) }
    val speedUpgradeMk3 = register("speed_upgrade_mk3") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), SpeedUpgrade(3)) }
    val powerSavingUpgradeMk1 = register("power_saving_upgrade_mk1") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), PowerSavingUpgrade(1)) }
    val powerSavingUpgradeMk2 = register("power_saving_upgrade_mk2") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), PowerSavingUpgrade(2)) }
    val powerSavingUpgradeMk3 = register("power_saving_upgrade_mk3") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), PowerSavingUpgrade(3)) }
    val overdriveUpgradeMk1 = register("overdrive_upgrade_mk1") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), OverdriveUpgrade(1)) }
    val overdriveUpgradeMk2 = register("overdrive_upgrade_mk2") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), OverdriveUpgrade(2)) }
    val overdriveUpgradeMk3 = register("overdrive_upgrade_mk3") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), OverdriveUpgrade(3)) }
    val afterBurnUpgradeMk1 = register("after_burner_upgrade_mk1") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), AfterBurnUpgrade(1)) }
    val afterBurnUpgradeMk2 = register("after_burner_upgrade_mk2") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), AfterBurnUpgrade(2)) }
    val afterBurnUpgradeMk3 = register("after_burner_upgrade_mk3") { MachineUpgradeItem(Properties().tab(CreativeTabs.Items), AfterBurnUpgrade(3)) }
    val aluminiumShredderBlade = register("aluminium_shredder_blade") { ShredderBladeItem(Properties().durability(20).tab(CreativeTabs.Items)) }
    val goldShredderBlade = register("gold_shredder_blade") { ShredderBladeItem(Properties().durability(30).tab(CreativeTabs.Items)) }
    val ironShredderBlade = register("iron_shredder_blade") { ShredderBladeItem(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelShredderBlade = register("steel_shredder_blade") { ShredderBladeItem(Properties().durability(200).tab(CreativeTabs.Items)) }
    val titaniumShredderBlade = register("titanium_shredder_blade") { ShredderBladeItem(Properties().durability(350).tab(CreativeTabs.Items)) }
    val advancedAlloyShredderBlade = register("advanced_alloy_shredder_blade") { ShredderBladeItem(Properties().durability(700).tab(CreativeTabs.Items)) }
    val combineSteelShredderBlade = register("combine_steel_shredder_blade") { ShredderBladeItem(Properties().durability(1500).tab(CreativeTabs.Items)) }
    val schrabidiumShredderBlade = register("schrabidium_shredder_blade") { ShredderBladeItem(Properties().durability(2000).tab(CreativeTabs.Items)) }
    val deshShredderBlade = register("desh_shredder_blade") { ShredderBladeItem(Properties().stacksTo(1).tab(CreativeTabs.Items)) }

    val rbmkLid = register("rbmk_lid") { RBMKLidItem(Properties().tab(CreativeTabs.Items)) }
    val rbmkGlassLid = register("rbmk_glass_lid") { RBMKLidItem(Properties().tab(CreativeTabs.Items)) }

    val rbmkPelletUeu = register("rbmk_pellet_ueu") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_UEU) }
    val rbmkPelletMeu = register("rbmk_pellet_meu") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MEU) }
    val rbmkPelletHeu233 = register("rbmk_pellet_heu233") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEU233) }
    val rbmkPelletHeu235 = register("rbmk_pellet_heu235") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEU235) }
    val rbmkPelletThMeu = register("rbmk_pellet_thmeu") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_THMEU) }
    val rbmkPelletLep = register("rbmk_pellet_lep") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_LEP) }
    val rbmkPelletMep = register("rbmk_pellet_mep") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MEP) }
    val rbmkPelletHep239 = register("rbmk_pellet_hep239") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEP239) }
    val rbmkPelletHep241 = register("rbmk_pellet_hep241") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEP241) }
    val rbmkPelletLea = register("rbmk_pellet_lea") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_LEA) }
    val rbmkPelletMea = register("rbmk_pellet_mea") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MEA) }
    val rbmkPelletHea241 = register("rbmk_pellet_hea241") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEA241) }
    val rbmkPelletHea242 = register("rbmk_pellet_hea242") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEA242) }
    val rbmkPelletMen = register("rbmk_pellet_men") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MEN) }
    val rbmkPelletHen = register("rbmk_pellet_hen") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEN) }
    val rbmkPelletMox = register("rbmk_pellet_mox") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MOX) }
    val rbmkPelletLes = register("rbmk_pellet_les") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_LES) }
    val rbmkPelletMes = register("rbmk_pellet_mes") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_MES) }
    val rbmkPelletHes = register("rbmk_pellet_hes") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HES) }
    val rbmkPelletLeaus = register("rbmk_pellet_leaus") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_LEAUS) }
    val rbmkPelletHeaus = register("rbmk_pellet_heaus") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_HEAUS) }
    val rbmkPelletPo210Be = register("rbmk_pellet_po210be") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_PO210BE, false) }
    val rbmkPelletRa226Be = register("rbmk_pellet_ra226be") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_RA226BE, false) }
    val rbmkPelletPu238Be = register("rbmk_pellet_pu238be") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_PU238BE) }
    val rbmkPelletBalefireGold = register("rbmk_pellet_balefire_gold") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_BALEFIRE_GOLD, false) }
    val rbmkPelletFlashlead = register("rbmk_pellet_flashlead") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_FLASHLEAD, false) }
    val rbmkPelletBalefire = register("rbmk_pellet_balefire") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_BALEFIRE, false) }
    val rbmkPelletZfbBismuth = register("rbmk_pellet_zfb_bismuth") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_ZFB_BISMUTH) }
    val rbmkPelletZfbPu241 = register("rbmk_pellet_zfb_pu241") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_ZFB_PU241) }
    val rbmkPelletZfbAmMix = register("rbmk_pellet_zfb_am_mix") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_ZFB_AM_MIX) }
    val rbmkPelletDrx = register("rbmk_pellet_drx") { RBMKPelletItem(Properties().tab(CreativeTabs.Items), LangKeys.RBMK_NAME_PELLET_DRX) }

    val emptyRBMKRod = register("empty_rbmk_rod") { Item(Properties().tab(CreativeTabs.Items)) }
    val rbmkRodUeu = register("rbmk_rod_ueu") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletUeu, yield = 100_000_000.0, reactivity = 15.0, burnFunc = RBMKRodItem.BurnFunction.LOGARITHMIC, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 0.65, meltingPoint = 2865.0) }
    val rbmkRodMeu = register("rbmk_rod_meu") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMeu, yield = 100_000_000.0, reactivity = 20.0, burnFunc = RBMKRodItem.BurnFunction.LOGARITHMIC, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 0.65, meltingPoint = 2865.0) }
    val rbmkRodHeu233 = register("rbmk_rod_heu233") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHeu233, yield = 100_000_000.0, reactivity = 27.5, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.25, meltingPoint = 2865.0) }
    val rbmkRodHeu235 = register("rbmk_rod_heu235") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHeu235, yield = 100_000_000.0, reactivity = 50.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.0, meltingPoint = 2865.0) }
    val rbmkRodThMeu = register("rbmk_rod_thmeu") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletThMeu, yield = 100_000_000.0, reactivity = 20.0, burnFunc = RBMKRodItem.BurnFunction.PLATEAU, depletionFunc = RBMKRodItem.DepleteFunction.BOOSTED_SLOPE, heat = 0.65, meltingPoint = 3350.0) }
    val rbmkRodLep = register("rbmk_rod_lep") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletLep, yield = 100_000_000.0, reactivity = 35.0, burnFunc = RBMKRodItem.BurnFunction.LOGARITHMIC, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 0.75, meltingPoint = 2744.0) }
    val rbmkRodMep = register("rbmk_rod_mep") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMep, yield = 100_000_000.0, reactivity = 35.0, selfRate = 20.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.0, meltingPoint = 2744.0) }
    val rbmkRodHep239 = register("rbmk_rod_hep239") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHep239, yield = 100_000_000.0, reactivity = 30.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.25, meltingPoint = 2744.0) }
    val rbmkRodHep241 = register("rbmk_rod_hep241") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHep241, yield = 100_000_000.0, reactivity = 40.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.75, meltingPoint = 2744.0) }
    val rbmkRodLea = register("rbmk_rod_lea") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletLea, yield = 100_000_000.0, reactivity = 60.0, selfRate = 10.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 1.5, meltingPoint = 2386.0) }
    val rbmkRodMea = register("rbmk_rod_mea") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMea, yield = 100_000_000.0, reactivity = 35.0, selfRate = 20.0, burnFunc = RBMKRodItem.BurnFunction.ARCH, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.75, meltingPoint = 2386.0) }
    val rbmkRodHea241 = register("rbmk_rod_hea241") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHea241, yield = 100_000_000.0, reactivity = 65.0, selfRate = 15.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.85, meltingPoint = 2386.0, neutronType = RBMKFluxReceiver.NeutronType.FAST) }
    val rbmkRodHea242 = register("rbmk_rod_hea242") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHea242, yield = 100_000_000.0, reactivity = 45.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 2.5, meltingPoint = 2386.0) }
    val rbmkRodMen = register("rbmk_rod_men") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMen, yield = 100_000_000.0, reactivity = 30.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 0.75, meltingPoint = 2800.0, neutronType = RBMKFluxReceiver.NeutronType.ANY) }
    val rbmkRodHen = register("rbmk_rod_hen") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHen, yield = 100_000_000.0, reactivity = 40.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.0, meltingPoint = 2800.0, neutronType = RBMKFluxReceiver.NeutronType.FAST) }
    val rbmkRodMox = register("rbmk_rod_mox") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMox, yield = 100_000_000.0, reactivity = 40.0, burnFunc = RBMKRodItem.BurnFunction.LOGARITHMIC, depletionFunc = RBMKRodItem.DepleteFunction.RAISING_SLOPE, heat = 1.0, meltingPoint = 2815.0) }
    val rbmkRodLes = register("rbmk_rod_les") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletLes, yield = 100_000_000.0, reactivity = 50.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.25, meltingPoint = 2500.0, releaseType = RBMKFluxReceiver.NeutronType.SLOW) }
    val rbmkRodMes = register("rbmk_rod_mes") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletMes, yield = 100_000_000.0, reactivity = 75.0, burnFunc = RBMKRodItem.BurnFunction.ARCH, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.5, meltingPoint = 2750.0) }
    val rbmkRodHes = register("rbmk_rod_hes") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHes, yield = 100_000_000.0, reactivity = 90.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 1.75, meltingPoint = 3000.0) }
    val rbmkRodLeaus = register("rbmk_rod_leaus") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletLeaus, yield = 100_000_000.0, reactivity = 30.0, burnFunc = RBMKRodItem.BurnFunction.SIGMOID, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 1.5, meltingPoint = 7029.0, xenonGen = 0.05) }
    val rbmkRodHeaus = register("rbmk_rod_heaus") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletHeaus, yield = 100_000_000.0, reactivity = 35.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 2.0, meltingPoint = 5211.0, xenonGen = 0.05) }
    val rbmkRodPo210Be = register("rbmk_rod_po210be") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletPo210Be, yield = 25_000_000.0, reactivity = 0.0, selfRate = 50.0, burnFunc = RBMKRodItem.BurnFunction.PASSIVE, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 0.1, meltingPoint = 1287.0, xenonGen = 0.0, diffusion = 0.05, releaseType = RBMKFluxReceiver.NeutronType.SLOW) }
    val rbmkRodRa226Be = register("rbmk_rod_ra226be") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletRa226Be, yield = 100_000_000.0, reactivity = 0.0, selfRate = 20.0, burnFunc = RBMKRodItem.BurnFunction.PASSIVE, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 0.035, meltingPoint = 700.0, xenonGen = 0.0, diffusion = 0.5, releaseType = RBMKFluxReceiver.NeutronType.SLOW) }
    val rbmkRodPu238Be = register("rbmk_rod_pu238be") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletPu238Be, yield = 50_000_000.0, reactivity = 40.0, selfRate = 40.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 0.1, meltingPoint = 1287.0, diffusion = 0.05, releaseType = RBMKFluxReceiver.NeutronType.SLOW) }
    val rbmkRodBalefireGold = register("rbmk_rod_balefire_gold") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletBalefireGold, yield = 100_000_000.0, reactivity = 50.0, selfRate = 10.0, burnFunc = RBMKRodItem.BurnFunction.ARCH, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 1.0, meltingPoint = 2000.0, xenonGen = 0.0) }
    val rbmkRodFlashlead = register("rbmk_rod_flashlead") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletFlashlead, yield = 25_000_000.0, reactivity = 40.0, selfRate = 50.0, burnFunc = RBMKRodItem.BurnFunction.ARCH, depletionFunc = RBMKRodItem.DepleteFunction.LINEAR, heat = 1.0, meltingPoint = 2050.0, xenonGen = 0.0) }
    val rbmkRodBalefire = register("rbmk_rod_balefire") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletBalefire, yield = 100_000_000.0, reactivity = 100.0, selfRate = 35.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 3.0, meltingPoint = 3652.0, xenonGen = 0.0) }
    val rbmkRodZfbBismuth = register("rbmk_rod_zfb_bismuth") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletZfbBismuth, yield = 50_000_000.0, reactivity = 20.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.75, meltingPoint = 2744.0) }
    val rbmkRodZfbPu241 = register("rbmk_rod_zfb_pu241") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletZfbPu241, yield = 50_000_000.0, reactivity = 20.0, burnFunc = RBMKRodItem.BurnFunction.SQUARE_ROOT, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.0, meltingPoint = 2865.0) }
    val rbmkRodZfbAmMix = register("rbmk_rod_zfb_am_mix") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletZfbAmMix, yield = 50_000_000.0, reactivity = 20.0, burnFunc = RBMKRodItem.BurnFunction.LINEAR, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 1.75, meltingPoint = 2744.0) }
    val rbmkRodDrx = register("rbmk_rod_drx") { RBMKRodItem(Properties().tab(CreativeTabs.Items), rbmkPelletDrx, yield = 1_000_000.0, reactivity = 1000.0, selfRate = 10.0, burnFunc = RBMKRodItem.BurnFunction.QUADRATIC, depletionFunc = RBMKRodItem.DepleteFunction.GENTLE_SLOPE, heat = 0.1, meltingPoint = 100_000.0) }

    val meltdownTool = register("dyatlov") { MeltdownToolItem(Properties().tab(CreativeTabs.Items).stacksTo(1)) }

    val graphiteDebris = register("graphite_debris") { Item(Properties().tab(CreativeTabs.Items)) }
    val metalDebris = register("metal_debris") { Item(Properties().tab(CreativeTabs.Items)) }
    val fuelDebris = register("fuel_debris") { Item(Properties().tab(CreativeTabs.Items)) }

    // Templates

    val machineTemplateFolder = register("machine_template_folder") { TemplateFolderItem() }

    // Siren Tracks
    val sirenTrackHatchSiren = register("siren_track_hatch_siren") { SirenTrackItem(NTechSounds.sirenTrackHatchSiren, 250, true, 0x334077) }
    val sirenTrackAutopilotDisconnected = register("siren_track_autopilot_disconnected") { SirenTrackItem(NTechSounds.sirenTrackAutopilotDisconnected, 50, true, 0xB5B5B5) }
    val sirenTrackAMSSiren = register("siren_track_ams_siren") { SirenTrackItem(NTechSounds.sirenTrackAMSSiren, 100, true, 0xE5BB52) }
    val sirenTrackBlastDoorAlarm = register("siren_track_blast_door_alarm") { SirenTrackItem(NTechSounds.sirenTrackBlastDoorAlarm, 50, true, 0xB20000) }
    val sirenTrackAPCSiren = register("siren_track_apc_siren") { SirenTrackItem(NTechSounds.sirenTrackAPCSiren, 100, true, 0x3666A0) }
    val sirenTrackKlaxon = register("siren_track_klaxon") { SirenTrackItem(NTechSounds.sirenTrackKlaxon, 50, true, 0x808080) }
    val sirenTrackVaultDoorAlarm = register("siren_track_vault_door_alarm") { SirenTrackItem(NTechSounds.sirenTrackVaultDoorAlarm, 50, true, 0x8C810B) }
    val sirenTrackSecurityAlert = register("siren_track_security_alert") { SirenTrackItem(NTechSounds.sirenTrackSecurityAlert, 50, true, 0x76818E) }
    val sirenTrackStandardSiren = register("siren_track_standard_siren") { SirenTrackItem(NTechSounds.sirenTrackStandardSiren, 250, true, 0x660000) }
    val sirenTrackClassicSiren = register("siren_track_classic_siren") { SirenTrackItem(NTechSounds.sirenTrackClassicSiren, 250, true, 0xC0CFE8) }
    val sirenTrackBankAlarm = register("siren_track_bank_alarm") { SirenTrackItem(NTechSounds.sirenTrackBankAlarm, 100, true, 0x3684E2) }
    val sirenTrackBeepSiren = register("siren_track_beep_siren") { SirenTrackItem(NTechSounds.sirenTrackBeepSiren, 100, true, 0xD3D3D3) }
    val sirenTrackContainerAlarm = register("siren_track_container_alarm") { SirenTrackItem(NTechSounds.sirenTrackContainerAlarm, 100, true, 0xE0BA9F) }
    val sirenTrackSweepSiren = register("siren_track_sweep_siren") { SirenTrackItem(NTechSounds.sirenTrackSweepSiren, 500, true, 0xEDEA5A) }
    val sirenTrackMissileSiloSiren = register("siren_track_missile_silo_siren") { SirenTrackItem(NTechSounds.sirenTrackMissileSiloSiren, 500, true, 0xABAB9A) }
    val sirenTrackAirRaidSiren = register("siren_track_air_raid_siren") { SirenTrackItem(NTechSounds.sirenTrackAirRaidSiren, 1000, false, 0xDF3795) }
    val sirenTrackNostromoSelfDestruct = register("siren_track_nostromo_self_destruct") { SirenTrackItem(NTechSounds.sirenTrackNostromoSelfDestruct, 100, true, 0x5DD800) }
    val sirenTrackEASAlarmScreech = register("siren_track_eas_alarm_screech") { SirenTrackItem(NTechSounds.sirenTrackEASAlarmScreech, 50, true, 0xB3A8C1) }
    val sirenTrackAPCPass = register("siren_track_apc_pass") { SirenTrackItem(NTechSounds.sirenTrackAPCPass, 50, false, 0x3437D3) }
    val sirenTrackRazortrainHorn = register("siren_track_razortrain_horn") { SirenTrackItem(NTechSounds.sirenTrackRazortrainHorn, 250, false, 0x7750ED) }

    val fluidIdentifier = register("fluid_identifier") { FluidIdentifierItem(Properties().tab(CreativeTabs.Templates)) }

    val assemblyTemplate = register("assembly_template") { AssemblyTemplateItem(Properties().tab(CreativeTabs.Templates)) }
    val chemTemplate = register("chem_template") { ChemPlantTemplateItem(Properties().tab(CreativeTabs.Templates)) }

    // Bomb Items

    val neutronShieldingLittleBoy = register("neutron_shielding_little_boy") { TooltipItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val subcriticalUraniumTarget = register("subcritical_uranium235_target") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val uraniumProjectile = register("uranium235_projectile") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val propellantLittleBoy = register("propellant_little_boy") { TooltipItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterLittleBoy = register("bomb_igniter_little_boy") { TooltipItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bundleOfImplosionPropellant = register("bundle_of_implosion_propellant") { TooltipItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterFatMan = register("bomb_igniter_fat_man") { TooltipItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val plutoniumCore = register("plutonium_core") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val detonator = register("detonator") { DetonatorItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val littleBoyKit = register("little_boy_kit") { BombKitItem(mapOf(NTechBlockItems.littleBoy to 1) + LittleBoyBlock.requiredComponents, 0, 0x0026FF, Properties().tab(CreativeTabs.Bombs)) }
    val fatManKit = register("fat_man_kit") { BombKitItem(mapOf(NTechBlockItems.fatMan to 1) + FatManBlock.requiredComponents, 0, 0xFFD800, Properties().tab(CreativeTabs.Bombs)) }

    // Rocketry: Missiles and Satellites

    val designator = register("designator") { DesignatorItem(Properties().tab(CreativeTabs.Rocketry).stacksTo(1)) }
    val heMissile = register("he_missile") { MissileItem(::HEMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val incendiaryMissile = register("incendiary_missile") { MissileItem(::IncendiaryMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val clusterMissile = register("cluster_missile") { MissileItem(::ClusterMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val bunkerBusterMissile = register("bunker_buster_missile") { MissileItem(::BunkerBusterMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val strongMissile = register("strong_missile") { MissileItem(::StrongHEMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongIncendiaryMissile = register("strong_incendiary_missile") { MissileItem(::StrongIncendiaryMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongClusterMissile = register("strong_cluster_missile") { MissileItem(::StrongClusterMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongBunkerBusterMissile = register("strong_bunker_buster_missile") { MissileItem(::StrongBunkerBusterMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val burstMissile = register("burst_missile") { MissileItem(::BurstMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val infernoMissile = register("inferno_missile") { MissileItem(::InfernoMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val rainMissile = register("rain_missile") { MissileItem(::RainMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val drillMissile = register("drill_missile") { MissileItem(::DrillMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val nuclearMissile = register("nuclear_missile") { MissileItem(::NuclearMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_NUCLEAR, renderScale = 1.5F) }
    val tectonicMissile = register("tectonic_missile") { MissileItem(::TectonicMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_NUCLEAR, renderScale = 1.5F, hasTooltip = true) }

    // Consumables and Gear

    val meteorRemote = register("meteor_remote") { MeteorRemoteItem(Properties().durability(2).setNoRepair().tab(CreativeTabs.Consumables)) }
    val oilDetector = register("oil_detector") { OilDetectorItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val pollutionDetector = register("pollution_detector") { PollutionDetectorItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val rbmkLinker = register("rbmk_linker") { RBMKLinkerItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val geigerCounter = register("handheld_geiger_counter") { GeigerCounterItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val ivBag = register("iv_bag") { EmptyIVBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val bloodBag = register("blood_bag") { BloodBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val emptyExperienceBag = register("empty_experience_bag") { EmptyExperienceBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val experienceBag = register("experience_bag") { ExperienceBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val radAway = register("radaway") { RadAwayItem(140F, 5 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val strongRadAway = register("strong_radaway") { RadAwayItem(350F, 4 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val eliteRadAway = register("elite_radaway") { RadAwayItem(1000F, 3 * 20, Properties().tab(CreativeTabs.Consumables)) }

    val hazmatHelmet = register("hazmat_helmet") { HazmatMaskItem(NTechArmorMaterials.hazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatChestplate = register("hazmat_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.hazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatLeggings = register("hazmat_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.hazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatBoots = register("hazmat_boots") { FullSetBonusArmorItem(NTechArmorMaterials.hazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatHelmet = register("advanced_hazmat_helmet") { HazmatMaskItem(NTechArmorMaterials.advancedHazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatChestplate = register("advanced_hazmat_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.advancedHazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatLeggings = register("advanced_hazmat_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.advancedHazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatBoots = register("advanced_hazmat_boots") { FullSetBonusArmorItem(NTechArmorMaterials.advancedHazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatHelmet = register("reinforced_hazmat_helmet") { HazmatMaskItem(NTechArmorMaterials.reinforcedHazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(fireproof = true), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatChestplate = register("reinforced_hazmat_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.reinforcedHazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatLeggings = register("reinforced_hazmat_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.reinforcedHazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatBoots = register("reinforced_hazmat_boots") { FullSetBonusArmorItem(NTechArmorMaterials.reinforcedHazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumHelmet = register("titanium_helmet") { FullSetBonusArmorItem(NTechArmorMaterials.titanium, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .85F), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumChestplate = register("titanium_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.titanium, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumLeggings = register("titanium_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.titanium, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumBoots = register("titanium_boots") { FullSetBonusArmorItem(NTechArmorMaterials.titanium, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelHelmet = register("steel_helmet") { FullSetBonusArmorItem(NTechArmorMaterials.steel, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .9F), Properties().tab(CreativeTabs.Consumables)) }
    val steelChestplate = register("steel_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.steel, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelLeggings = register("steel_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.steel, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelBoots = register("steel_boots") { FullSetBonusArmorItem(NTechArmorMaterials.steel, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyHelmet = register("advanced_alloy_helmet") { FullSetBonusArmorItem(NTechArmorMaterials.advancedAlloy, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .75F), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyChestplate = register("advanced_alloy_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.advancedAlloy, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyLeggings = register("advanced_alloy_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.advancedAlloy, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyBoots = register("advanced_alloy_boots") { FullSetBonusArmorItem(NTechArmorMaterials.advancedAlloy, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelHelmet = register("combine_steel_helmet") {
        FullSetBonusArmorItem(NTechArmorMaterials.combineSteel, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(
                damageCap = 2F, damageMod = .05F, damageThreshold = 2F, fireproof = true, effects = listOf(
                    MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false, false),
                    MobEffectInstance(MobEffects.DIG_SPEED, 20, 2, false, false, false),
                    MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 4, false, false, false),
                )
            ), Properties().tab(CreativeTabs.Consumables))
    }
    val combineSteelChestplate = register("combine_steel_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.combineSteel, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelLeggings = register("combine_steel_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.combineSteel, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelBoots = register("combine_steel_boots") { FullSetBonusArmorItem(NTechArmorMaterials.combineSteel, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyChestplate = register("paa_alloy_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.paAAlloy, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus(damageCap = 6F, damageMod = .3F, noHelmet = true, effects = listOf(MobEffectInstance(MobEffects.DIG_SPEED, 20, 0, false, false, false))), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyLeggings = register("paa_alloy_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.paAAlloy, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(paAAlloyChestplate.get()), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyBoots = register("paa_alloy_boots") { FullSetBonusArmorItem(NTechArmorMaterials.paAAlloy, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(paAAlloyChestplate.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumHelmet = register("schrabidium_helmet") {
        FullSetBonusArmorItem(
            NTechArmorMaterials.schrabidium, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(
                damageCap = 4F, damageMod = .1F, fireproof = true, effects = listOf(
                    MobEffectInstance(MobEffects.DIG_SPEED, 20, 2, false, false, false),
                    MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2, false, false, false),
                    MobEffectInstance(MobEffects.JUMP, 20, 1, false, false, false),
                    MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false, false),
                )
            ), Properties().tab(CreativeTabs.Consumables)
        )
    }
    val schrabidiumChestplate = register("schrabidium_chestplate") { FullSetBonusArmorItem(NTechArmorMaterials.schrabidium, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumLeggings = register("schrabidium_leggings") { FullSetBonusArmorItem(NTechArmorMaterials.schrabidium, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumBoots = register("schrabidium_boots") { FullSetBonusArmorItem(NTechArmorMaterials.schrabidium, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }

    val polaroid = register("polaroid") { PolaroidItem(Properties().tab(CreativeTabs.Consumables)) }

    // Miscellaneous

    val nuclearCreeperSpawnEgg = register("nuclear_creeper_spawn_egg") { ForgeSpawnEggItem(NTechEntities.nuclearCreeper, 0x1E3E2E, 0x66B300, Properties().tab(CreativeTabs.Miscellaneous)) }

    // Hidden

    val creativeNuclearExplosionSpawner = register("creative_nuclear_explosion_spawner") { CreativeNuclearExplosionSpawnerItem(Properties().stacksTo(1)) }
    val creativeDebugWand = register("creative_debug_wand") { DebugWandItem(Properties().stacksTo(1).rarity(Rarity.EPIC)) }

    private fun partsItem(properties: Properties = PARTS_COMMON, tooltip: Boolean = false) = defaultItem(properties, tooltip)
    private fun defaultItem(properties: Properties, tooltip: Boolean = false): () -> Item = if (tooltip) { -> TooltipItem(properties) } else { -> Item(properties) }

    private fun Properties.tab(tab: CreativeTabs): Properties = tab(tab.tab)
}
