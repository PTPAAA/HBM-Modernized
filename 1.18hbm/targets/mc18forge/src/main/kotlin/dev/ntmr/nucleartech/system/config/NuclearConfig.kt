/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.config

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NuclearTech
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.loading.FMLPaths

object NuclearConfig {
    val configDirectory = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(MODID), MODID)
    val general = GeneralConfig()
    val client = ClientConfig()
    val explosions = ExplosionsConfig()
    val fallout = FalloutConfig()
    val radiation = RadiationConfig()
    val rbmk = RBMKConfig()
    val world = WorldConfig()

    fun registerConfigs(container: ModContainer) {
        container.apply {
            addConfig(NuclearModConfig(this, general))
            addConfig(NuclearModConfig(this, client))
            addConfig(NuclearModConfig(this, explosions))
            addConfig(NuclearModConfig(this, fallout))
            addConfig(NuclearModConfig(this, radiation))
            addConfig(NuclearModConfig(this, rbmk))
            addConfig(NuclearModConfig(this, world))
        }

        // TODO let players override configs for specific worlds
    }
}
