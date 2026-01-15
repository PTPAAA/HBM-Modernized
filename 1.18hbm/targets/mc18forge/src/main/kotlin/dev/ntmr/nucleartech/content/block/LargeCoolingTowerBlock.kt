package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.api.block.entities.createSidedTickerChecked
import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.base.AbstractMachineMultiBlock
import dev.ntmr.nucleartech.content.block.entity.LargeCoolingTowerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class LargeCoolingTowerBlock(properties: Properties) : AbstractMachineMultiBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = LargeCoolingTowerBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, NTechBlockEntities.largeCoolingTowerBlockEntityType.get())

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = InteractionResult.PASS
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {}
    override fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState) = dropMultiBlockEntityContentsAndRemoveStructure<LargeCoolingTowerBlockEntity>(state, level, pos, newState, ::placeMultiBlock)

    override fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
        fill(-4, 0, -4, 4, 12, 4, defaultStructurePart)
        place(4, 0, 0, defaultStructureIO)
        place(-4, 0, 0, defaultStructureIO)
        place(0, 0, 4, defaultStructureIO)
        place(0, 0, -4, defaultStructureIO)
        place(4, 0, 3, defaultStructureIO)
        place(4, 0, -3, defaultStructureIO)
        place(-4, 0, 3, defaultStructureIO)
        place(-4, 0, -3, defaultStructureIO)
        place(3, 0, 4, defaultStructureIO)
        place(-3, 0, 4, defaultStructureIO)
        place(3, 0, -4, defaultStructureIO)
        place(-3, 0, -4, defaultStructureIO)
    }
}
