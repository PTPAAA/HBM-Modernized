/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.rendering

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.system.config.NuclearConfig
import net.minecraft.client.Minecraft
import net.minecraft.world.level.material.FogType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ViewportEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.level.LevelEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.abs
import kotlin.math.min

@Mod.EventBusSubscriber(modid = MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.FORGE)
object TheFogIsComing {
    private const val EPSILON = 0.05F

    private var targetSoot = 0F
    private var renderSoot = -1F

    fun theFogIsComing(soot: Float) {
        targetSoot = soot
    }

    @JvmStatic @SubscribeEvent
    fun theFogIsComingCloser(event: TickEvent.ClientTickEvent) {
        if (!Minecraft.getInstance().isPaused && event.phase == TickEvent.Phase.START && NuclearConfig.client.enableSootFog.get()) {
            when {
                abs(renderSoot - targetSoot) < EPSILON || renderSoot < 0F -> renderSoot = targetSoot
                renderSoot < targetSoot -> renderSoot += EPSILON
                renderSoot > targetSoot -> renderSoot -= EPSILON
            }
        }
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.LOW)
    fun theFogHasArrived(event: ViewportEvent.RenderFog) {
        val soot = (renderSoot - NuclearConfig.client.sootFogThreshold.get()).toFloat()
        if (soot > 0 && NuclearConfig.client.enableSootFog.get()) {
            val divisor = (1 + soot / NuclearConfig.client.sootFogDivisor.get()).toFloat()
            event.farPlaneDistance /= divisor
            if (event.camera.fluidInCamera != FogType.WATER) {
                // Fully smooth transitions woohoo
                event.nearPlaneDistance = (event.nearPlaneDistance / divisor - 35F * (divisor - 1F)).coerceIn(-200F, event.farPlaneDistance - (32F * (divisor - 1F).coerceIn(0F, 1F)))
            } else {
                event.nearPlaneDistance = event.nearPlaneDistance.coerceAtMost(event.farPlaneDistance - 8F)
            }
            event.isCanceled = true
        }
    }

    @JvmStatic @SubscribeEvent
    fun theFogTheFogTheFogTheFog(event: ViewportEvent.ComputeFogColor) {
        val soot = (renderSoot - NuclearConfig.client.sootFogThreshold.get()).toFloat()
        val sootColor = 0.15F
        val sootRequired = NuclearConfig.client.sootFogDivisor.get().toFloat() * 5F
        if (soot > 0 && NuclearConfig.client.enableSootFog.get()) {
            val interp = min(soot / sootRequired, 1F)
            event.red *= (1 - interp) + sootColor * interp
            event.green *= (1 - interp) + sootColor * interp
            event.blue *= (1 - interp) + sootColor * interp
        }
    }

    @SubscribeEvent @JvmStatic
    fun theFogWillReturn(event: LevelEvent.Unload) {
        targetSoot = 0F
        renderSoot = -1F
    }
}
