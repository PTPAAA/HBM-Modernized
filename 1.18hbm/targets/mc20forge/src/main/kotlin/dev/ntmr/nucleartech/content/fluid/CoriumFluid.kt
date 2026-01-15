/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid

import dev.ntmr.nucleartech.content.NTechBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.fluids.ForgeFlowingFluid
import java.util.*

abstract class CoriumFluid(properties: Properties) : ForgeFlowingFluid(properties) {
    class Flowing(properties: Properties) : CoriumFluid(properties) {
        override fun createFluidStateDefinition(builder: StateDefinition.Builder<Fluid, FluidState>) {
            super.createFluidStateDefinition(builder)
            builder.add(LEVEL)
        }

        override fun getAmount(fluidState: FluidState): Int = fluidState.getValue(LEVEL)
        override fun isSource(fluidState: FluidState) = false

        override fun randomTick(level: Level, pos: BlockPos, fluidState: FluidState, random: net.minecraft.util.RandomSource) {
            level.setBlockAndUpdate(pos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, pos, pos, NTechBlocks.corebblestone.get().defaultBlockState()))
        }

        override fun isRandomlyTicking() = true
    }

    class Source(properties: Properties) : CoriumFluid(properties) {
        override fun getAmount(fluidState: FluidState) = 8
        override fun isSource(fluidState: FluidState) = true

        override fun randomTick(level: Level, pos: BlockPos, fluidState: FluidState, random: net.minecraft.util.RandomSource) {
            level.setBlockAndUpdate(pos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, pos, pos, NTechBlocks.corium.get().defaultBlockState()))
        }

        override fun isRandomlyTicking() = true
    }
}
