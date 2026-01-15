/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import com.mojang.serialization.Codec
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.particle.*
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechParticles : NTechRegistry<ParticleType<*>> {
    override val forgeRegistry: DeferredRegister<ParticleType<*>> = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID)

    val CONTRAIL = register("contrail") { createParticleType(false, ContrailParticleOptions.Companion.DESERIALIZER) { ContrailParticleOptions.Companion.CODEC }}
    val COOLING_TOWER_CLOUD = register("cooling_tower_cloud") { createParticleType(true, CoolingTowerCloudParticleOptions.Deserializer) { CoolingTowerCloudParticleOptions.Deserializer.CODEC }}
    val MINI_NUKE_CLOUD = register("mini_nuke_cloud") { SimpleParticleType(true) }
    val MINI_NUKE_CLOUD_BALEFIRE = register("mini_nuke_cloud_balefire") { SimpleParticleType(true) }
    val MINI_NUKE_FLASH = register("mini_nuke_flash") { SimpleParticleType(true) }
    val OIL_SPILL = register("oil_spill") { SimpleParticleType(false) }
    val RBMK_FIRE = register("rbmk_fire") { SimpleParticleType(false) }
    val RBMK_MUSH = register("rbmk_mush") { createParticleType(false, RBMKMushParticleOptions.Companion.DESERIALIZER) { RBMKMushParticleOptions.Companion.CODEC } }
    val ROCKET_FLAME = register("rocket_flame") { SimpleParticleType(true) }
    val RUBBLE = register("rubble") { createParticleType(false, RubbleParticleOptions.Companion.DESERIALIZER) { RubbleParticleOptions.Companion.CODEC }}
    val SHOCKWAVE = register("shockwave") { SimpleParticleType(true) }
    val SMOKE = register("smoke") { createParticleType(false, SmokeParticleOptions.Companion.DESERIALIZER) { SmokeParticleOptions.Companion.CODEC }}
    val VOLCANO_SMOKE = register("volcano_smoke") { SimpleParticleType(true) }

    @Suppress("DEPRECATION")
    private fun <T : ParticleOptions> createParticleType(overrideLimiter: Boolean, deserializer: ParticleOptions.Deserializer<T>, codec: (ParticleType<T>) -> Codec<T>): ParticleType<T> =
        object : ParticleType<T>(overrideLimiter, deserializer) {
            override fun codec() = codec(this)
        }
}
