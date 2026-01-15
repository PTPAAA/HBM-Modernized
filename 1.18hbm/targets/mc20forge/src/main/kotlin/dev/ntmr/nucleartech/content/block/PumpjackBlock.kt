package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.api.block.entities.createSidedTickerChecked
import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.base.AbstractOrientableMachineMultiBlock
import dev.ntmr.nucleartech.content.block.entity.PumpjackBlockEntity
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

class PumpjackBlock(properties: Properties) : AbstractOrientableMachineMultiBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = PumpjackBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, NTechBlockEntities.pumpjackBlockEntityType.get())

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<PumpjackBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<PumpjackBlockEntity>(level, pos, stack)
    override fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState) = dropMultiBlockEntityContentsAndRemoveStructure<PumpjackBlockEntity>(state, level, pos, newState, ::placeMultiBlock)

    override fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
        fill(0, 0, 0, 0, 3, 6, defaultStructurePart)
        place(1, 0, 1, defaultStructurePart)
        place(1, 0, 2, defaultStructureIO)
        place(1, 0, 3, defaultStructurePart)
        place(1, 0, 4, defaultStructureIO)
        place(1, 0, 5, defaultStructurePart)
        place(-1, 0, 2, defaultStructureIO)
        place(-1, 0, 3, defaultStructurePart)
        place(-1, 0, 4, defaultStructureIO)
    }
}
