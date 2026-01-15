package dev.ntmr.nucleartech.packets

import dev.ntmr.nucleartech.ntm
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.*
import java.util.Optional
import kotlin.reflect.KClass



object NuclearPacketHandler {
    const val PROTOCOL_VERSION = "1"

    val INSTANCE = NetworkRegistry.newSimpleChannel(
        ntm("main"),
        { PROTOCOL_VERSION },
        { it == PROTOCOL_VERSION },
        { it == PROTOCOL_VERSION }
    )

    private var currentPacketID = 0

    internal fun initialize() {
        registerMessage(AnvilConstructMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(CraftMachineTemplateMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKAutoControlRodValuesMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKConsoleControlRodLevelMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKConsoleScreenAssignedColumnsMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SpawnNuclearExplosionMessage::decode, NetworkDirection.PLAY_TO_SERVER)

        registerMessage(SyncServerResourcesMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(ContainerMenuUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT)

        registerMessage(ContaminationValuesUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(HurtAnimationMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(SirenMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(BlockEntityUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT) // TODO replace others with this
        registerMessage(ExplosionVNTMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(PushNotificationMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(FogIsComingMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
    }

    private fun <T : NetworkMessage<T>> registerMessage(message: KClass<T>, decoder: (FriendlyByteBuf) -> T, networkDirection: NetworkDirection? = null): NuclearPacketHandler {
        val builder = if (networkDirection != null) {
            INSTANCE.messageBuilder(message.java, currentPacketID++, networkDirection)
        } else {
            INSTANCE.messageBuilder(message.java, currentPacketID++)
        }
        builder.encoder(NetworkMessage<T>::encode)
            .decoder(decoder)
            .consumerMainThread(NetworkMessage<T>::handle)
            .add()
        return this
    }

    private inline fun <reified T : NetworkMessage<T>> registerMessage(noinline decoder: (FriendlyByteBuf) -> T, networkDirection: NetworkDirection? = null) =
        registerMessage(T::class, decoder, networkDirection)

    fun send(target: PacketDistributor.PacketTarget, message: Any) {
        INSTANCE.send(target, message)
    }

    fun sendTo(message: Any, player: net.minecraft.server.level.ServerPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with { player }, message)
    }

    fun sendToServer(message: Any) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message)
    }
}
