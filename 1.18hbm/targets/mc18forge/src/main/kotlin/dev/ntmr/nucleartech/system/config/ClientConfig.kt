package dev.ntmr.nucleartech.system.config

import dev.ntmr.nucleartech.client.rendering.Overlays
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class ClientConfig : ConfigBase {
    override val fileName = "client"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.CLIENT

    val displayUpdateMessage: ForgeConfigSpec.BooleanValue

    val pushNotificationPositionMode: ForgeConfigSpec.EnumValue<Overlays.PushNotificationsOverlay.OverlayPosition>
    val pushNotificationPositionOffsetHorizontal: ForgeConfigSpec.IntValue
    val pushNotificationPositionOffsetVertical: ForgeConfigSpec.IntValue

    val enableSootFog: ForgeConfigSpec.BooleanValue
    val sootFogThreshold: ForgeConfigSpec.DoubleValue
    val sootFogDivisor: ForgeConfigSpec.DoubleValue

    val displayCustomCapes: ForgeConfigSpec.BooleanValue

    val meteorTrailsPerTick: ForgeConfigSpec.IntValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Client config. Only present on client, duh.").push(fileName)

            displayUpdateMessage = comment("Will show a chat message with update info if a newer version of NTM is available").define("displayUpdateMessage", true)

            comment("HUD overlay settings").push("overlay")
            pushNotificationPositionMode = comment("How to position the notification overlay").defineEnum("pushNotificationPositionMode", Overlays.PushNotificationsOverlay.OverlayPosition.TopLeft)
            pushNotificationPositionOffsetHorizontal = comment("Customizable horizontal offset for the push notification overlay in scaled GUI pixels (one standard text line is 10 pixels high)").defineInRange("pushNotificationOffsetHorizontal", 0, -10_000, 10_000)
            pushNotificationPositionOffsetVertical = comment("Customizable vertical offset for the push notification overlay in scaled GUI pixels (one standard text line is 10 pixels high)").defineInRange("pushNotificationOffsetVertical", 0, -10_000, 10_000)
            pop()

            comment("Fog settings").push("fog")
            enableSootFog = comment("Whether smog should be visible").define("enableSootFog", true)
            sootFogThreshold = comment("How much soot is required for smog to become visible").defineInRange("sootFogThreshold", 35.0, .1, 10_000.0)
            sootFogDivisor = comment("The divisor for smog, higher numbers will require more soot for the same density").defineInRange("sootFogDivisor", 24.0, 1.0, 1_000.0)
            pop()

            comment("Render settings").push("rendering")
            displayCustomCapes = comment("Disable this if there are any rendering problems with other mods. Please leave it on otherwise for *respect*.").define("displayCustomCapes", true)
            pop()

            comment("Particle settings").push("particles")
            meteorTrailsPerTick = comment("How many meteor trail particle emitters are spawned per tick. One emitter renders ten particles.").defineInRange("meteorTrailsPerTick", 6, 0, 20)
            pop()

            pop()
            configSpec = build()
        }
    }
}
