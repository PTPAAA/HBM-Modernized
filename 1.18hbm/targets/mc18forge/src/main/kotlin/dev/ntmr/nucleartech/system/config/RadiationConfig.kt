package dev.ntmr.nucleartech.system.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class RadiationConfig : ConfigBase {
    override val fileName = "radiation"
    override val configSpec: ForgeConfigSpec
    override val configType: ModConfig.Type = ModConfig.Type.SERVER

    val enableEntityIrradiation: ForgeConfigSpec.BooleanValue
    val netherRadiation: ForgeConfigSpec.DoubleValue
    val worldRadiationEffects: ForgeConfigSpec.BooleanValue
    val worldRadiationThreshold: ForgeConfigSpec.DoubleValue

    val enablePollution: ForgeConfigSpec.BooleanValue
    val pollutionMultiplier: ForgeConfigSpec.DoubleValue
    val worldPollutionEffects: ForgeConfigSpec.BooleanValue
    val worldPoisonThreshold: ForgeConfigSpec.DoubleValue
    val worldEffectCount: ForgeConfigSpec.IntValue
    val mobPollutionEffects: ForgeConfigSpec.BooleanValue
    val mobSootThreshold: ForgeConfigSpec.DoubleValue
    val mobHealthModifier: ForgeConfigSpec.DoubleValue
    val mobDamageModifier: ForgeConfigSpec.DoubleValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Radiation Config. Synced from server to client.").push(fileName)

            comment("Radiation system settings").push("radiation_system")
            enableEntityIrradiation = comment("Whether entities can get irradiated").define("enableEntityIrradiation", true)
            netherRadiation = comment("RAD/s in the nether (or other warm dimensions, if applicable)").defineInRange("netherRadiation", .1, 0.0, 1_000.0)
            worldRadiationEffects = comment("Whether high radiation levels should perform changes to the world").define("worldRadiationEffects", true)
            worldRadiationThreshold = comment("The least amount of RADs required in a chunk for block modifications to happen").defineInRange("worldRadiationThreshold", 20.0, .5, 1_000_000.0)
            pop()

            comment("Pollution system settings").push("pollution_system")
            enablePollution = comment("General toggle for the pollution system").define("enablePollution", true)
            pollutionMultiplier = comment("Multiplier to apply on pollution sources (not displayed in tooltips)").defineInRange("pollutionMultiplier", 1.0, .01, 1_000.0)
            worldPollutionEffects = comment("Whether high pollution levels should perform changes to the world").define("worldPollutionEffects", true)
            worldPoisonThreshold = comment("The least amount of poison required in a 4x4 chunk area for block modifications to happen").defineInRange("worldPoisonThreshold", 15.0, .1, 1_000_000.0)
            worldEffectCount = comment("How many blocks in a 4x4 area to apply affects on each tick").defineInRange("worldEffectCount", 5, 1, 100)
            mobPollutionEffects = comment("Whether high pollution levels should modify mob stats").define("mobPollutionEffects", true)
            mobSootThreshold = comment("The least amount of soot required in a 4x4 chunk area for the stats of newly-spawning mobs to be modified").defineInRange("mobSootThreshold", 15.0, .1, 1_000_000.0)
            mobHealthModifier = comment("How much of the mob's health to add *on top* of the mob's original health").defineInRange("mobHealthModifier", 1.0, .01, 100.0)
            mobDamageModifier = comment("How much of the mob's damage to add *on top* of the mob's original damage").defineInRange("mobDamageModifier", 1.5, .01, 100.0)
            pop()

            pop()
            configSpec = build()
        }
    }
}
