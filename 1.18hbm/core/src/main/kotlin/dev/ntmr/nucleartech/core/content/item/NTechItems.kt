/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content.item

import dev.ntmr.nucleartech.core.MODID
import dev.ntmr.nucleartech.core.content.CreativeTabs
import dev.ntmr.nucleartech.core.registries.NTechRegistry
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item.Properties
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Rarity
import dev.ntmr.sorcerer.generated.spells.main.net.minecraftforge.registries.DeferredRegister
import dev.ntmr.sorcerer.generated.spells.main.net.minecraftforge.registries.ForgeRegistries

@Suppress("unused")
object NTechItems : NTechRegistry<Item> {
    private val PARTS_COMMON = Properties().tab(CreativeTabs.Parts)
    private val PARTS_UNCOMMON = Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)
    private val PARTS_RARE = Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE)
    private val PARTS_EPIC = Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)

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

    override val forgeRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    private fun partsItem(properties: Properties = PARTS_COMMON, tooltip: Boolean = false) = defaultItem(properties, tooltip)
    private fun defaultItem(properties: Properties, tooltip: Boolean): () -> Item = if (tooltip) { -> TooltipItem(properties) } else { -> Item(properties) }

    private fun Properties.tab(tab: CreativeTabs): Properties = tab(tab.tab)
}
