package dev.ntmr.nucleartech.content.block.rbmk

import dev.ntmr.nucleartech.api.block.entities.createServerTickerChecked
import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.base.AbstractOrientableMachineMultiBlock
import dev.ntmr.nucleartech.content.block.dropMultiBlockEntityContentsAndRemoveStructure
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKConsoleBlockEntity
import dev.ntmr.nucleartech.content.block.openMenu
import dev.ntmr.nucleartech.content.block.setMachineCustomName
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class RBMKConsoleBlock(properties: Properties) : AbstractOrientableMachineMultiBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKConsoleBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, NTechBlockEntities.rbmkConsoleBlockEntityType.get())

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<RBMKConsoleBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<RBMKConsoleBlockEntity>(level, pos, stack)
    override fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState) = dropMultiBlockEntityContentsAndRemoveStructure<RBMKConsoleBlockEntity>(state, level, pos, newState, ::placeMultiBlock)

    override fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
        fill(-2, 0, 0, 2, 0, 0, defaultStructurePart)
        fill(-2, 0, 1, 2, 3, 1, defaultStructurePart)
    }
}
