/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.rendering

import dev.ntmr.nucleartech.MODID
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource
import net.minecraft.world.level.levelgen.synth.SimplexNoise
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ViewportEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.random.Random

@Mod.EventBusSubscriber(Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object CameraShake {
    private const val MAX_YAW = 20
    private const val MAX_PITCH = 20
    private const val MAX_ROLL = 8
    private const val MAX_X_OFFSET = 0.6
    private const val MAX_Z_OFFSET = 0.6
    private const val MAX_Y_OFFSET = 0.3

    private val perlinNoise = SimplexNoise(SingleThreadedRandomSource(Random.nextLong()))
    private var lastNanoTime = 0L
    private var intensityLevelRotational = 0.0
        set(value) { field = value.coerceIn(0.0, 1.0) }
    private var intensityLevelTranslational = 0.0
        set(value) { field = value.coerceIn(0.0, 1.0) }

    fun addIntensity(delta: Double) {
        intensityLevelRotational += delta
        intensityLevelTranslational += delta
    }

    fun setIntensityAtLeast(value: Double) {
        if (intensityLevelRotational < value) intensityLevelRotational = value
        if (intensityLevelTranslational < value) intensityLevelTranslational = value
    }

    fun addRotational(delta: Double) { intensityLevelRotational += delta }
    fun addTranslational(delta: Double) { intensityLevelTranslational += delta }
    fun setRotationalAtLeast(value: Double) { if (intensityLevelRotational < value) intensityLevelRotational = value }
    fun setTranslationalAtLeast(value: Double) { if (intensityLevelTranslational < value) intensityLevelTranslational = value }

    @SubscribeEvent @JvmStatic
    fun tick(event: ViewportEvent.ComputeCameraAngles) {
        if (intensityLevelRotational > 0.0) {
            val noiseInput = System.nanoTime() * 5E-8
            val shake = intensityLevelRotational * intensityLevelRotational
            event.yaw += (MAX_YAW * shake * perlinNoise.getValue(noiseInput, -noiseInput)).toFloat()
            event.pitch += (MAX_PITCH * shake * perlinNoise.getValue(noiseInput + 1000, -noiseInput - 1000)).toFloat()
            event.roll += (MAX_ROLL * shake * perlinNoise.getValue(noiseInput - 1000, -noiseInput + 1000)).toFloat()
            intensityLevelRotational -= .25 * (System.nanoTime() - lastNanoTime) * 1E-9 // takes four seconds to go from 1 to 0
        }

        if (intensityLevelTranslational > 0.0) {
            val noiseInput = System.nanoTime() * 5E-8
            val shake = intensityLevelTranslational * intensityLevelTranslational
            event.camera.move(
                MAX_X_OFFSET * shake * perlinNoise.getValue(noiseInput + 500, -noiseInput - 500),
                MAX_Y_OFFSET * shake * perlinNoise.getValue(noiseInput + 250, -noiseInput - 250),
                MAX_Z_OFFSET * shake * perlinNoise.getValue(noiseInput - 500, -noiseInput + 500)
            )
            intensityLevelTranslational -= .25 * (System.nanoTime() - lastNanoTime) * 1E-9 // takes four seconds to go from 1 to 0
        }

        lastNanoTime = System.nanoTime()
    }
}
