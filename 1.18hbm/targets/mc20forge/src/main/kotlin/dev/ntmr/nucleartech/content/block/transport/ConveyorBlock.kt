/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block.transport

import dev.ntmr.nucleartech.api.block.entities.createServerTickerChecked
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.entity.transport.ConveyorBlockEntity
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
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class ConveyorBlock(properties: Properties) : BaseEntityBlock(properties) {
    
    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.horizontalDirection)
    }

    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun getShape(state: BlockState, level: net.minecraft.world.level.BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = ConveyorBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): Type<T>? {
        return if (level.isClientSide) null else createServerTickerChecked(type, NTechBlockEntities.conveyorBlockEntityType.get(), ConveyorBlockEntity::tick)
    }
    
    companion object {
        val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
    }
}
