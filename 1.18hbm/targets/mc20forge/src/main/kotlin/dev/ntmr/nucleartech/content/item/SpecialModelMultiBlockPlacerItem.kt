package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.block.multi.RotatedMultiBlockPlacer
import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

open class SpecialModelMultiBlockPlacerItem<B : Block>(private val block: B, blockEntityFunc: (BlockPos, BlockState) -> BlockEntity, val placerFunc: B.(MultiBlockPlacer) -> Unit, properties: Properties, override val additionalRange: Float = 0F) : SpecialModelBlockItem(block, blockEntityFunc, properties), IncreasedRangeItem {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.canPlace(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply { placerFunc(block, this) }.canPlaceAt(context.level, context.clickedPos)
    }

    override fun placeBlock(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.placeBlock(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply { placerFunc(block, this) }.finish(context.level, context.clickedPos)
    }
}
