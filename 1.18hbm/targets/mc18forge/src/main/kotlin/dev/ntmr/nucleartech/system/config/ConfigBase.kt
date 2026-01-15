package dev.ntmr.nucleartech.system.config

import net.minecraftforge.fml.config.IConfigSpec
import net.minecraftforge.fml.config.ModConfig

interface ConfigBase {
    val fileName: String
    val configSpec: IConfigSpec<*>
    val configType: ModConfig.Type
}
