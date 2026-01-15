/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.particle

import com.mojang.brigadier.StringReader
import org.joml.Vector3f
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.NTechParticles
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf

class ContrailParticleOptions(val color: Vector3f, val scale: Float) : ParticleOptions {
    override fun getType() = NTechParticles.CONTRAIL.get()

    override fun writeToNetwork(buffer: FriendlyByteBuf) {
        buffer.writeFloat(this.color.x())
        buffer.writeFloat(this.color.y())
        buffer.writeFloat(this.color.z())
        buffer.writeFloat(this.scale)
    }

    override fun writeToString(): String {
        return String.format(java.util.Locale.ROOT, "%s %.2f %.2f %.2f %.2f", this.getType(), this.color.x(), this.color.y(), this.color.z(), this.scale)
    }

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<ContrailParticleOptions> {
            override fun fromCommand(type: ParticleType<ContrailParticleOptions>, reader: StringReader): ContrailParticleOptions {
                val vec = reader.readVector3f()
                reader.expect(' ')
                val scale = reader.readFloat()
                return ContrailParticleOptions(vec, scale)
            }

            override fun fromNetwork(type: ParticleType<ContrailParticleOptions>, packet: FriendlyByteBuf): ContrailParticleOptions {
                val r = packet.readFloat()
                val g = packet.readFloat()
                val b = packet.readFloat()
                val scale = packet.readFloat()
                return ContrailParticleOptions(Vector3f(r, g, b), scale)
            }
            
            private fun StringReader.readVector3f(): Vector3f {
                val r = readFloat()
                expect(' ')
                val g = readFloat()
                expect(' ')
                val b = readFloat()
                return Vector3f(r, g, b)
            }
        }

        val CODEC: Codec<ContrailParticleOptions> = RecordCodecBuilder.create { builder ->
            builder.group(
                Codec.FLOAT.listOf().xmap<Vector3f>(
                    { l -> Vector3f(l[0], l[1], l[2]) },
                    { v -> listOf(v.x(), v.y(), v.z()) }
                ).fieldOf("color").forGetter { it.color },
                Codec.FLOAT.fieldOf("scale").forGetter { it.scale }
            ).apply(builder, ::ContrailParticleOptions)
        }
    }
}
