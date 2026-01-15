package dev.ntmr.nucleartech.packets.resources

import net.minecraft.network.FriendlyByteBuf

interface ClientSyncedResourceManager<T : ClientSyncedResourceManager.SyncedData> {
    val syncManagerName: String

    fun getServerSyncedData(): T
    fun deserializeFromNetwork(buffer: FriendlyByteBuf): T
    fun handle(data: T)

    @Suppress("UNCHECKED_CAST")
    fun handleUnsafe(data: SyncedData) = handle(data as T)

    interface SyncedData {
        fun serializeToNetwork(buffer: FriendlyByteBuf)
    }
}
