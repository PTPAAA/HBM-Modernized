/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.packets

import dev.ntmr.nucleartech.client.rendering.TheFogIsComing
import dev.ntmr.nucleartech.system.world.ChunkPollution
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerLevel
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.PacketDistributor
import java.util.function.Supplier

class FogIsComingMessage(val soot: Float) : NetworkMessage<FogIsComingMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeFloat(soot)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            TheFogIsComing.theFogIsComing(soot)
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic fun decode(packetBuffer: FriendlyByteBuf) = FogIsComingMessage(packetBuffer.readFloat())

        fun theFogWillConsumeAll(level: ServerLevel) {
            for (player in level.players()) {
                NuclearPacketHandler.send(PacketDistributor.PLAYER.with { player }, FogIsComingMessage(
                    soot = ChunkPollution.getPollution(level, player.blockPosition(), ChunkPollution.PollutionType.Soot)
                ))
            }
        }
    }
}
