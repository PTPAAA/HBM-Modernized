package dev.ntmr.nucleartech.datagen.provider

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.NTechFluidTraits
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.fluid.trait.CombustibleFluidTrait
import dev.ntmr.nucleartech.content.fluid.trait.CoolableFluidTrait
import dev.ntmr.nucleartech.content.fluid.trait.HeatableFluidTrait
import dev.ntmr.nucleartech.extensions.appendToPath
import dev.ntmr.nucleartech.system.world.ChunkPollution
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.save
import net.minecraft.data.HashCache
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.RegistryObject
import java.nio.file.Path

class FluidTraitProvider(private val generator: DataGenerator) : DataProvider {
    override fun getName() = "Nuclear Tech Mod Fluid Traits"

    private fun attachFluidTraits() {
        val steamBoilerEfficiency = 1F
        val steamHeatExEfficiency = 0.25F
        val steamTurbineEfficiency = 1F
        val steamCoolEfficiency = 0.5F

        add(FluidTags.WATER, NTechFluidTraits.liquid)
        heatable(FluidTags.WATER, NTechFluids.steam.source, 200, 1, 100, steamBoilerEfficiency, steamHeatExEfficiency)

        add(FluidTags.LAVA, NTechFluidTraits.liquid)

        add(Tags.Fluids.MILK, NTechFluidTraits.liquid)

        add(NTechTags.Fluids.SPENT_STEAM, NTechFluidTraits.gaseous)

        add(NTechTags.Fluids.STEAM, NTechFluidTraits.gaseous)
        heatable(NTechTags.Fluids.STEAM, NTechFluids.steamHot.source, 2, 10, 1, steamBoilerEfficiency, steamHeatExEfficiency)
        coolable(NTechTags.Fluids.STEAM, NTechFluids.spentSteam.source, 200, 100, 1, steamTurbineEfficiency, steamCoolEfficiency)

        add(NTechTags.Fluids.HOT_STEAM, NTechFluidTraits.gaseous)
        heatable(NTechTags.Fluids.HOT_STEAM, NTechFluids.steamSuperHot.source, 18, 10, 1, steamBoilerEfficiency, steamHeatExEfficiency)
        coolable(NTechTags.Fluids.HOT_STEAM, NTechFluids.steam.source, 2, 1, 10, steamTurbineEfficiency, steamCoolEfficiency)

        add(NTechTags.Fluids.SUPER_HOT_STEAM, NTechFluidTraits.gaseous)
        heatable(NTechTags.Fluids.SUPER_HOT_STEAM, NTechFluids.steamUltraHot.source, 120, 10, 1, steamBoilerEfficiency, steamHeatExEfficiency)
        coolable(NTechTags.Fluids.SUPER_HOT_STEAM, NTechFluids.steamHot.source, 18, 1, 10, steamTurbineEfficiency, steamCoolEfficiency)

        add(NTechTags.Fluids.ULTRA_HOT_STEAM, NTechFluidTraits.gaseous)
        coolable(NTechTags.Fluids.ULTRA_HOT_STEAM, NTechFluids.steamSuperHot.source, 120, 1, 10, steamTurbineEfficiency, steamCoolEfficiency)

        val demandVeryLow = 0.5F
        val demandLow = 1.0F
        val demandMedium = 1.5F
        val demandHigh = 2.0F
        val complexityRefinery = 1.1F
        val complexityFraction = 1.05F
        val complexityCracking = 1.25F
        val complexityChemPlant = 1.1F
        val complexityLubed = 1.15F
        val complexityLeaded = 1.5F
        val flammabilityLow = 0.25F
        val flammabilityNormal = 1.0F
        val flammabilityHigh = 2.0F

        val sootUnrefinedOil = ChunkPollution.SOOT_PER_SECOND * 0.1F // Burns 4x dirtier thn regular fuel
        val sootRefinedOil = ChunkPollution.SOOT_PER_SECOND * 0.025F // Baseline, used for most fuels
        val sootGas = ChunkPollution.SOOT_PER_SECOND * 0.005F // Gasses burn very cleanly
        val heavyMetalLeadFuel = ChunkPollution.HEAVY_METAL_PER_SECOND * 0.025F // Baseline for leaded fuels
        val poisonOil = ChunkPollution.POISON_PER_SECOND * 0.0025F // Poison stat for most petrochemicals
        val poisonExtreme = ChunkPollution.POISON_PER_SECOND * 0.025F // Poison stat for horrible chemicals like red mud or phosgene
        val poisonMinor = ChunkPollution.POISON_PER_SECOND * 0.001F // Poison stat for mostly inert things like carbon dioxide

        add(NTechTags.Fluids.OIL, NTechFluidTraits.liquid)
        add(NTechTags.Fluids.OIL, NTechFluidTraits.viscous)
        flammableAndCombustible(NTechTags.Fluids.OIL, flammabilityLow * demandLow)
        polluting(NTechTags.Fluids.OIL, burnData = pollutionData(soot = sootUnrefinedOil), spillData = pollutionData(poison = poisonOil))

        add(NTechTags.Fluids.GAS, NTechFluidTraits.gaseous)
        flammableAndCombustible(NTechTags.Fluids.GAS, flammabilityNormal * demandVeryLow, 1.25F, CombustibleFluidTrait.FuelGrade.Gaseous)
        polluting(NTechTags.Fluids.GAS, burnData = pollutionData(soot = sootGas))

        add(NTechTags.Fluids.URANIUM_HEXAFLUORIDE, NTechFluidTraits.gaseous)
        corrosive(NTechTags.Fluids.URANIUM_HEXAFLUORIDE, 15)
        radioactive(NTechTags.Fluids.URANIUM_HEXAFLUORIDE, 0.2F)
        polluting(NTechTags.Fluids.URANIUM_HEXAFLUORIDE, burnData = pollutionData(poison = poisonExtreme), spillData = pollutionData(poison = poisonExtreme))
    }

    private val idAttachedTraits = mutableMapOf<ResourceLocation, MutableMap<RegistryObject<out FluidTrait>, JsonObject>>()
    private val tagAttachedTraits = mutableMapOf<ResourceLocation, MutableMap<RegistryObject<out FluidTrait>, JsonObject>>()

    private fun add(fluid: Fluid, trait: RegistryObject<out FluidTrait>, data: JsonObject = JsonObject()) {
        if (idAttachedTraits.getOrPut(fluid.registryName!!, ::mutableMapOf).put(trait, data) != null) duplicateEntry(fluid.registryName!!)
    }

    private fun add(tag: TagKey<Fluid>, trait: RegistryObject<out FluidTrait>, data: JsonObject = JsonObject()) {
        if (tagAttachedTraits.getOrPut(tag.location, ::mutableMapOf).put(trait, data) != null) duplicateEntry(tag.location)
    }

    private fun coolable(tag: TagKey<Fluid>, coolsTo: RegistryObject<out Fluid>, heat: Int, requires: Int, produces: Int, turbineEfficiency: Float = 0F, heatExchangerEfficiency: Float = 0F) {
        add(tag, NTechFluidTraits.coolable, JsonObject().apply {
            addProperty("cools_to", coolsTo.id.toString())
            addProperty("requires", requires)
            addProperty("produces", produces)
            addProperty("heat_energy", heat)
            if (turbineEfficiency == 0F && heatExchangerEfficiency == 0F) throw IllegalArgumentException("At least one efficiency needs to be specified")
            if (turbineEfficiency > 0F) addProperty(CoolableFluidTrait.CoolingType.Turbine.serializedName, turbineEfficiency)
            if (heatExchangerEfficiency > 0F) addProperty(CoolableFluidTrait.CoolingType.HeatExchanger.serializedName, heatExchangerEfficiency)
        })
    }

    private fun corrosive(tag: TagKey<Fluid>, level: Int) {
        add(tag, NTechFluidTraits.corrosive, JsonObject().apply { addProperty("corrosion", level) })
    }

    private fun flammableAndCombustible(tag: TagKey<Fluid>, energyModifier: Float, combustionModifier: Float = 0F, fuelGrade: CombustibleFluidTrait.FuelGrade? = null) {
        add(tag, NTechFluidTraits.flammable, JsonObject().apply { addProperty("heat_energy", round(100_000 * energyModifier)) })

        if (combustionModifier > 0F) {
            if (fuelGrade == null) throw IllegalArgumentException("No fuel grade specified")
            add(tag, NTechFluidTraits.combustible, JsonObject().apply {
                addProperty("energy", round(100_000 * energyModifier * combustionModifier))
                addProperty("grade", fuelGrade.serializedName)
            })
        }
    }

    private fun polluting(tag: TagKey<Fluid>, burnData: ChunkPollution.PollutionData = ChunkPollution.PollutionData(), spillData: ChunkPollution.PollutionData = ChunkPollution.PollutionData()) {
        fun pollutionDataToJson(data: ChunkPollution.PollutionData) = JsonObject().apply {
            addProperty("soot", data.soot)
            addProperty("poison", data.poison)
            addProperty("heavy_metal", data.heavyMetal)
        }

        add(tag, NTechFluidTraits.polluting, JsonObject().apply {
            if (!burnData.isZeroed) add("burned", pollutionDataToJson(burnData))
            if (!spillData.isZeroed) add("spilled", pollutionDataToJson(spillData))
        })
    }

    private fun pollutionData(soot: Float = 0F, poison: Float = 0F, heavyMetal: Float = 0F) =
        ChunkPollution.PollutionData(soot = soot, poison = poison, heavyMetal = heavyMetal)

    private fun round(f: Float): Long = round(f.toLong())

    private fun round(l: Long): Long {
        if (l > 10000000L) return l - l % 100000L
        if (l > 1000000L) return l - l % 10000L
        if (l > 100000L) return l - l % 1000L
        if (l > 10000L) return l - l % 100L
        return if (l > 1000L) l - l % 10L else l
    }

    private fun heatable(tag: TagKey<Fluid>, heatsTo: RegistryObject<out Fluid>, heat: Int, requires: Int, produces: Int, boilerEfficiency: Float = 0F, heatExchangerEfficiency: Float = 0F) {
        add(tag, NTechFluidTraits.heatable, JsonObject().apply {
            addProperty("heats_to", heatsTo.id.toString())
            addProperty("requires", requires)
            addProperty("produces", produces)
            addProperty("heat_energy", heat)
            if (boilerEfficiency == 0F && heatExchangerEfficiency == 0F) throw IllegalArgumentException("At least one efficiency needs to be specified")
            if (boilerEfficiency > 0F) addProperty(HeatableFluidTrait.HeatingType.Boiler.serializedName, boilerEfficiency)
            if (heatExchangerEfficiency > 0F) addProperty(HeatableFluidTrait.HeatingType.HeatExchanger.serializedName, heatExchangerEfficiency)
        })
    }

    private fun radioactive(tag: TagKey<Fluid>, radiation: Float) {
        add(tag, NTechFluidTraits.radioactive, JsonObject().apply { addProperty("radiation", radiation) })
    }

    override fun run(cache: HashCache) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val path = generator.outputFolder
        attachFluidTraits()
        for ((location, traits) in idAttachedTraits) for ((trait, data) in traits) {
            val json = JsonObject().apply {
                addProperty("trait", trait.id.toString())
                addProperty("fluid", location.toString())
                if (data.size() > 0) add("data", data)
            }
            save(gson, cache, json, resolvePath(path, location.appendToPath("_${trait.id.path}")))
        }
        for ((location, traits) in tagAttachedTraits) for ((trait, data) in traits) {
            val json = JsonObject().apply {
                addProperty("trait", trait.id.toString())
                addProperty("tag", "#$location")
                if (data.size() > 0) add("data", data)
            }
            save(gson, cache, json, resolvePath(path, location.appendToPath("_${trait.id.path}")))
        }
    }

    private fun duplicateEntry(id: ResourceLocation): Nothing = throw IllegalStateException("Duplicate fluid trait attachment $id")
    private fun resolvePath(basePath: Path, id: ResourceLocation) = basePath.resolve("data/${MODID}/${MODID}_fluid_traits/${id.path}.json")
}
