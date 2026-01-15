/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.worldgen.features.meteoriteplacers

import dev.ntmr.nucleartech.content.NTechWorldFeatures
import dev.ntmr.nucleartech.content.worldgen.features.configurations.MeteoriteFeatureConfiguration
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

object NopMeteoritePlacer : MeteoritePlacer(0) {
    /* no-op */
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {}

    override fun type() = NTechWorldFeatures.MeteoritePlacerType.NOP_PLACER.get()
}
