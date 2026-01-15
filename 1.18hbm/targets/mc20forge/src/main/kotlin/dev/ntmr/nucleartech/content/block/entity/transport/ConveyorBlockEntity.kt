/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block.entity.transport

import dev.ntmr.nucleartech.content.NTechBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.AABB

class ConveyorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.conveyorBlockEntityType.get(), pos, state) {

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: ConveyorBlockEntity) {
            val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)
            val speed = 0.1
            
            val box = AABB(pos).expandTowards(0.0, 0.5, 0.0)
            val entities = level.getEntitiesOfClass(ItemEntity::class.java, box)
            
            for (entity in entities) {
                if (entity.y < pos.y + 0.5) { // Ensure on top
                    entity.setPos(entity.x, pos.y + 0.5, entity.z)
                }
                
                // Keep centered logic broadly
                // Simple movement for now
                var motionX = 0.0
                var motionZ = 0.0
                
                when (facing) {
                    Direction.NORTH -> motionZ = -speed
                    Direction.SOUTH -> motionZ = speed
                    Direction.WEST -> motionX = -speed
                    Direction.EAST -> motionX = speed
                    else -> {}
                }
                
                entity.deltaMovement = entity.deltaMovement.add(motionX, 0.0, motionZ)
                
                // Center alignment helper (simple)
                if (facing.axis == Direction.Axis.Z) {
                   if (entity.x < pos.x + 0.4) entity.deltaMovement = entity.deltaMovement.add(0.02, 0.0, 0.0)
                   if (entity.x > pos.x + 0.6) entity.deltaMovement = entity.deltaMovement.add(-0.02, 0.0, 0.0)
                } else {
                   if (entity.z < pos.z + 0.4) entity.deltaMovement = entity.deltaMovement.add(0.0, 0.0, 0.02)
                   if (entity.z > pos.z + 0.6) entity.deltaMovement = entity.deltaMovement.add(0.0, 0.0, -0.02)
                }
            }
        }
    }
}
