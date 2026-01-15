/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.particle

import com.mojang.brigadier.StringReader
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.NTechParticles
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

class RBMKMushParticleOptions(val scale: Float) : ParticleOptions {
    override fun getType() = NTechParticles.RBMK_MUSH.get()

    override fun writeToNetwork(packet: FriendlyByteBuf) {
        packet.writeFloat(scale)
    }

    override fun writeToString() = "%s %.2f".format(Locale.ROOT, ForgeRegistries.PARTICLE_TYPES.getKey(this.type), scale)

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<RBMKMushParticleOptions> {
            override fun fromCommand(type: ParticleType<RBMKMushParticleOptions>, reader: StringReader): RBMKMushParticleOptions {
                reader.expect(' ')
                return RBMKMushParticleOptions(reader.readFloat())
            }

            override fun fromNetwork(type: ParticleType<RBMKMushParticleOptions>, packet: FriendlyByteBuf) = RBMKMushParticleOptions(packet.readFloat())
        }

        val CODEC: Codec<RBMKMushParticleOptions> = RecordCodecBuilder.create {
            it.group(Codec.FLOAT.fieldOf("scale").forGetter(RBMKMushParticleOptions::scale)).apply(it, ::RBMKMushParticleOptions)
        }
    }
}
