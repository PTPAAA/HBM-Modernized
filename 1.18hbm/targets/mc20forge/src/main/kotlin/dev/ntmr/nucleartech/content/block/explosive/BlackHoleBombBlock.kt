package dev.ntmr.nucleartech.content.block.explosive

import dev.ntmr.nucleartech.content.block.entity.explosive.BlackHoleBombBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class BlackHoleBombBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return BlackHoleBombBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (!level.isClientSide) {
            val be = level.getBlockEntity(pos)
            if (be is BlackHoleBombBlockEntity) {
                be.arm()
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Black Hole Bomb Armed! Detonation in 10s..."))
                return InteractionResult.SUCCESS
            }
        }
        return InteractionResult.SUCCESS
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return createTickerHelper(type, dev.ntmr.nucleartech.content.NTechBlockEntities.blackHoleBomb.get(), BlackHoleBombBlockEntity::tick)
    }
}
