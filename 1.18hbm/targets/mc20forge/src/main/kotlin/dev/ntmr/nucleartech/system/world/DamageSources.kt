/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.world

import dev.ntmr.nucleartech.MODID
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import kotlin.random.Random

object DamageSources {
    val EXTRACT_BLOOD: ResourceKey<DamageType> = key("extract_blood")
    val METEORITE: ResourceKey<DamageType> = key("meteorite")
    val MURKY_ANVIL: ResourceKey<DamageType> = key("murky_anvil")
    val NUCLEAR_BLAST: ResourceKey<DamageType> = key("nuclear_blast")
    val RADIATION: ResourceKey<DamageType> = key("radiation")
    val SHRAPNEL: ResourceKey<DamageType> = key("shrapnel")

    fun extractBlood(level: Level) = source(level, EXTRACT_BLOOD)
    fun meteorite(level: Level) = source(level, METEORITE)
    fun murkyAnvil(level: Level): DamageSource = MurkyAnvilDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(MURKY_ANVIL))
    fun nuclearBlast(level: Level) = source(level, NUCLEAR_BLAST)
    fun radiation(level: Level) = source(level, RADIATION)
    fun shrapnel(level: Level) = source(level, SHRAPNEL)

    private fun key(name: String) = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation(MODID, name))
    private fun source(level: Level, key: ResourceKey<DamageType>) = DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key))
}

private class MurkyAnvilDamageSource(type: net.minecraft.core.Holder<DamageType>) : DamageSource(type) {
    override fun getLocalizedDeathMessage(entity: LivingEntity): Component {
        val message = super.getLocalizedDeathMessage(entity).string
        val obfuscatedMessage = StringBuilder()
        var currentlyObfuscating = false
        for (i in message.indices) {
            if (!currentlyObfuscating && Random.nextInt(3) == 0) {
                obfuscatedMessage.append("§k")
                currentlyObfuscating = true
            }
            obfuscatedMessage.append(message[i])
            if (currentlyObfuscating && Random.nextInt(2) == 0) {
                obfuscatedMessage.append("§r")
                currentlyObfuscating = false
            }
        }
        obfuscatedMessage.append("§r")
        return Component.literal(obfuscatedMessage.toString())
    }
}
