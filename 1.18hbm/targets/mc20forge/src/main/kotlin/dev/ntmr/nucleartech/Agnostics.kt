/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech

import dev.ntmr.nucleartech.client.rendering.Overlays
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import dev.ntmr.nucleartech.packets.PushNotificationMessage
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraftforge.common.util.LogicalSidedProvider
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.network.PacketDistributor

val Agnostics = DistExecutor.safeRunForDist({ DistExecutor.SafeSupplier(::ClientFunctions) }, { DistExecutor.SafeSupplier(::ServerFunctions) })

sealed interface AgnosticFunctions {
    fun getRecipeManager(): RecipeManager?
    fun displayNotification(player: Player, message: Component, duration: Int = 3000, id: Int = -1)
}

private class ServerFunctions : AgnosticFunctions {
    override fun getRecipeManager(): RecipeManager? {
        val server = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER) as? MinecraftServer ?: return null
        return server.serverResources.managers.recipeManager
    }

    override fun displayNotification(player: Player, message: Component, duration: Int, id: Int) {
        player as? ServerPlayer ?: throw IllegalStateException("Player wasn't an instance of ServerPlayer on the server")
        NuclearPacketHandler.send(PacketDistributor.PLAYER.with { player }, PushNotificationMessage(message, duration, id))
    }
}

private class ClientFunctions : AgnosticFunctions {
    override fun getRecipeManager(): RecipeManager? {
        return Minecraft.getInstance().level?.recipeManager
    }

    override fun displayNotification(player: Player, message: Component, duration: Int, id: Int) {
        Overlays.PushNotificationsOverlay.push(message, duration = duration, id = id)
    }
}
