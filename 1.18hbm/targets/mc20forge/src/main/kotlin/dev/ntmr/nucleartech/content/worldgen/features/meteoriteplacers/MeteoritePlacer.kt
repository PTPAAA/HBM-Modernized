/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.worldgen.features.meteoriteplacers

import com.mojang.datafixers.Products
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.NTechRegistries
import dev.ntmr.nucleartech.content.NTechWorldFeatures
import dev.ntmr.nucleartech.content.worldgen.features.configurations.MeteoriteFeatureConfiguration
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

import net.minecraft.util.RandomSource

abstract class MeteoritePlacer(val radius: Int) {
    abstract fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: RandomSource, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration)

    protected abstract fun type(): NTechWorldFeatures.MeteoritePlacerType<*>

    companion object {
        val CODEC: Codec<MeteoritePlacer> = NTechRegistries.METEORITE_PLACERS.get().codec.dispatch(MeteoritePlacer::type, NTechWorldFeatures.MeteoritePlacerType<*>::codec)

        @JvmStatic
        protected fun <P : MeteoritePlacer> meteoritePlacerParts(instance: RecordCodecBuilder.Instance<P>): Products.P1<RecordCodecBuilder.Mu<P>, Int> = instance.group(
            Codec.intRange(1, 20).fieldOf("radius").forGetter(MeteoritePlacer::radius)
        )
    }
}
