/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.worldgen.features.meteoriteplacers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.NTechWorldFeatures
import dev.ntmr.nucleartech.content.worldgen.features.configurations.MeteoriteFeatureConfiguration
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

import net.minecraft.util.RandomSource

class BoxMeteoritePlacer(radius: Int) : MeteoritePlacer(radius) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: RandomSource, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        for (a in -radius..radius) for (b in -radius..radius) for (c in -radius..radius) {
            val pos = origin.offset(a, b, c)
            meteoriteConsumer.accept(pos, blockStateProvider.getState(random, pos))
        }
    }

    override fun type() = NTechWorldFeatures.MeteoritePlacerType.BOX_PLACER.get()

    companion object {
        val CODEC: Codec<BoxMeteoritePlacer> = RecordCodecBuilder.create { builder ->
            meteoritePlacerParts(builder).apply(builder, ::BoxMeteoritePlacer)
        }
    }
}
