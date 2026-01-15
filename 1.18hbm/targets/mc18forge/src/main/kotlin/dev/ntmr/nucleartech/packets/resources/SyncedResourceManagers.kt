package dev.ntmr.nucleartech.packets.resources

import dev.ntmr.nucleartech.content.fluid.trait.FluidTraitManager
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import dev.ntmr.nucleartech.packets.SyncServerResourcesMessage
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.PacketDistributor

object SyncedResourceManagers {
    private val managers = mutableMapOf<String, ClientSyncedResourceManager<*>>()

    init {
        register(FluidTraitManager)
    }

    fun syncAll(player: ServerPlayer?) {
        for (manager in managers.values) {
            val target = if (player != null)
                PacketDistributor.PLAYER.with { player }
            else
                PacketDistributor.ALL.noArg()

            NuclearPacketHandler.INSTANCE.send(target, SyncServerResourcesMessage(manager, manager.getServerSyncedData()))
        }
    }

    fun getManager(name: String): ClientSyncedResourceManager<*>? =
        managers[name]

    private fun register(manager: ClientSyncedResourceManager<*>)  {
        managers[manager.syncManagerName] = manager
    }
}
