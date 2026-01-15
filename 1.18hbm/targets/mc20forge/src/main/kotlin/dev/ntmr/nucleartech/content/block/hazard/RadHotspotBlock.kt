package dev.ntmr.nucleartech.content.block.hazard

import dev.ntmr.nucleartech.content.block.HazardBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class RadHotspotBlock(properties: Properties) : HazardBlock(properties) {
    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.INVISIBLE
    }

    override fun getShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.empty()
    }
    
    // Ensure we can select it in creative mode if needed, effectively simpler to just be invisible block.
    // However, if we want it to be truly invisible and hard to find (like structure blocks for map makers),
    // Shapes.empty() might make it hard to break.
    // For now, let's stick with standard behavior but invisible model. A small hitbox might differ.
    // Actually, usually map maker blocks allow selection.
    
    // Reverting shape to standard cube for interaction, just invisible rendering.
    override fun getShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.block()
    }
}
