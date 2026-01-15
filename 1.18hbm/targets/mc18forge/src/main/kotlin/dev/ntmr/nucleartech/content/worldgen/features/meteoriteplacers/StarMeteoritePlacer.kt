/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.worldgen.features.meteoriteplacers

import dev.ntmr.nucleartech.math.placeOctahedral
import dev.ntmr.nucleartech.content.worldgen.features.configurations.MeteoriteFeatureConfiguration
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.NTechWorldFeatures
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

class StarMeteoritePlacer(radius: Int) : MeteoritePlacer(radius) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        placeOctahedral(origin, radius + .5F) {
            meteoriteConsumer.accept(it, blockStateProvider.getState(random, it))
        }

        val halfRadius = radius / 2
        for (x in -halfRadius..halfRadius) for (y in -halfRadius..halfRadius) for (z in -halfRadius..halfRadius) {
            val pos = origin.offset(x, y, z)
            meteoriteConsumer.accept(pos, blockStateProvider.getState(random, pos))
        }
    }

    override fun type() = NTechWorldFeatures.MeteoritePlacerType.STAR_PLACER.get()

    companion object {
        val CODEC: Codec<StarMeteoritePlacer> = RecordCodecBuilder.create { builder ->
            meteoritePlacerParts(builder).apply(builder, ::StarMeteoritePlacer)
        }
    }
}
