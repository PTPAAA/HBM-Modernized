/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.packets

import dev.ntmr.nucleartech.Agnostics
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class PushNotificationMessage(val message: Component, val duration: Int, val id: Int) : NetworkMessage<PushNotificationMessage> {
    enum class Common {
        Duck,
        Filter,
        Compass,
        Cable,
        Drone,
        Jetpack,
        Magnet,
        HUD,
        Detonator,
        FluidID,
        FanMode,
        Toolability,
        GasHazard,
        PollutionDetector,
        ;

        val id: Int get() = ordinal
    }

    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeComponent(message)
        packetBuffer.writeInt(duration)
        packetBuffer.writeInt(id)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            val player = Minecraft.getInstance().player ?: return@enqueueWork
            Agnostics.displayNotification(player, message, duration, id)
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic fun decode(packetBuffer: FriendlyByteBuf) = PushNotificationMessage(packetBuffer.readComponent(), packetBuffer.readInt(), packetBuffer.readInt())
    }
}
