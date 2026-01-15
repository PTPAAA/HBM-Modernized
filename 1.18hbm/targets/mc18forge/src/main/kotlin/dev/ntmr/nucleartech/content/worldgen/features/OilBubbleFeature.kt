/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.worldgen.features

import dev.ntmr.nucleartech.math.placeSpherical
import com.mojang.serialization.Codec
import dev.ntmr.nucleartech.content.NTechBlocks
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class OilBubbleFeature(codec: Codec<NoneFeatureConfiguration>) : net.minecraft.world.level.levelgen.feature.Feature<NoneFeatureConfiguration>(codec) {
    private val stoneTest = { state: BlockState -> state.`is`(BlockTags.STONE_ORE_REPLACEABLES) }
    private val deepslateTest = { state: BlockState -> state.`is`(BlockTags.DEEPSLATE_ORE_REPLACEABLES) }

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val radius = 7 + context.random().nextInt(9)

        val oilDeposit = NTechBlocks.oilDeposit.get().defaultBlockState()
        val deepOilDeposit = NTechBlocks.deepslateOilDeposit.get().defaultBlockState()

        val worldGen = context.level()
        placeSpherical(context.origin(), radius) {
            when {
                worldGen.isStateAtPosition(it, stoneTest) -> setBlock(context.level(), it, oilDeposit)
                worldGen.isStateAtPosition(it, deepslateTest) -> setBlock(context.level(), it, deepOilDeposit)
            }
        }

        return true
    }
}
