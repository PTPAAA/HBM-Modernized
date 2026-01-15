package dev.ntmr.nucleartech.packets

import dev.ntmr.nucleartech.packets.resources.ClientSyncedResourceManager
import dev.ntmr.nucleartech.packets.resources.SyncedResourceManagers
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class SyncServerResourcesMessage(val manager: ClientSyncedResourceManager<*>, val data: ClientSyncedResourceManager.SyncedData) : NetworkMessage<SyncServerResourcesMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeUtf(manager.syncManagerName)
        data.serializeToNetwork(packetBuffer)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            manager.handleUnsafe(data)
        }
        context.get().packetHandled = true
    }

    companion object {
        fun decode(packetBuffer: FriendlyByteBuf): SyncServerResourcesMessage {
            val managerID = packetBuffer.readUtf()
            val manager = SyncedResourceManagers.getManager(managerID) ?: throw IllegalArgumentException("Unknown synced data manager ID $managerID")
            return SyncServerResourcesMessage(manager, manager.deserializeFromNetwork(packetBuffer))
        }
    }
}
