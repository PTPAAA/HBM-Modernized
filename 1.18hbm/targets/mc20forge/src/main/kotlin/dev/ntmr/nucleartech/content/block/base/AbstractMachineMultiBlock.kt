@file:Suppress("OVERRIDE_DEPRECATION")

package dev.ntmr.nucleartech.content.block.base

import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.NTechBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

abstract class AbstractMachineMultiBlock(properties: Properties) : BaseEntityBlock(properties) {
    abstract override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity
    abstract override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>?

    abstract override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult
    abstract override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack)
    abstract fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState): Boolean

    abstract fun placeMultiBlock(placer: MultiBlockPlacer)
    val defaultStructurePart: BlockState get() = NTechBlocks.genericMultiBlockPart.get().defaultBlockState()
    val defaultStructureIO: BlockState get() = NTechBlocks.genericMultiBlockPort.get().defaultBlockState()

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, piston: Boolean) {
        removeMultiBlock(state, level, pos, newState)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, piston)
    }

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F
}
