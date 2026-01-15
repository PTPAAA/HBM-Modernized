/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block.machine.processor

import dev.ntmr.nucleartech.api.block.entities.createServerTickerChecked
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.entity.processor.ArcFurnaceBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class ArcFurnaceBlock(properties: Properties) : BaseEntityBlock(properties) {

    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.LIT, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.horizontalDirection.opposite)
    }

    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = ArcFurnaceBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): Type<T>? {
        return if (level.isClientSide) null else createServerTickerChecked(type, NTechBlockEntities.arcFurnaceBlockEntityType.get(), ArcFurnaceBlockEntity::tick)
    }
}
