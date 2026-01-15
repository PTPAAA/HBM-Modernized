package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.api.block.entities.createServerTickerChecked
import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.api.explosion.IgnitableExplosive
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechBlocks
import dev.ntmr.nucleartech.content.block.base.AbstractMachineMultiBlock
import dev.ntmr.nucleartech.content.block.entity.LaunchPadBlockEntity
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPart
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class LaunchPadBlock(properties: Properties) : AbstractMachineMultiBlock(properties), IgnitableExplosive {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = LaunchPadBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, NTechBlockEntities.launchPadBlockEntityType.get())

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<LaunchPadBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<LaunchPadBlockEntity>(level, pos, stack)
    override fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState) = dropMultiBlockEntityContentsAndRemoveStructure<LaunchPadBlockEntity>(state, level, pos, newState, ::placeMultiBlock)

    override fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
        fill(-1, 0, -1, 1, 0, 1, NTechBlocks.launchPadPart.get().defaultBlockState())
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onPlace(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, something: Boolean) {
        if (!newState.`is`(oldState.block) && level.hasNeighborSignal(pos))
            detonate(level, pos)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun neighborChanged(blockState: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, something: Boolean) {
        if (level.hasNeighborSignal(pos))
            detonate(level, pos)
    }

    class LaunchPadPartBlock : MultiBlockPart(::GenericMultiBlockPartBlockEntity), IgnitableExplosive {
        @Suppress("OVERRIDE_DEPRECATION")
        override fun onPlace(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, something: Boolean) {
            if (!newState.`is`(oldState.block) && level.hasNeighborSignal(pos))
                detonate(level, pos)
        }

        @Suppress("OVERRIDE_DEPRECATION")
        override fun neighborChanged(blockState: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, something: Boolean) {
            if (level.hasNeighborSignal(pos))
                detonate(level, pos)
        }

        override fun detonate(level: Level, pos: BlockPos): IgnitableExplosive.DetonationResult {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity !is GenericMultiBlockPartBlockEntity) return IgnitableExplosive.DetonationResult.InvalidBlockEntity
            return super.detonate(level, blockEntity.core)
        }
    }
}
