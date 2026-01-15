package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.api.block.entities.createServerTickerChecked
import dev.ntmr.nucleartech.api.block.multi.MultiBlockPlacer
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.base.AbstractMachineMultiBlock
import dev.ntmr.nucleartech.content.block.entity.OilDerrickBlockEntity
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

class OilDerrickBlock(properties: Properties) : AbstractMachineMultiBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = OilDerrickBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, NTechBlockEntities.oilDerrickBlockEntityType.get())

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<OilDerrickBlockEntity>(level, pos, stack)
    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<OilDerrickBlockEntity>(level, pos, player)
    override fun removeMultiBlock(state: BlockState, level: Level, pos: BlockPos, newState: BlockState) = dropMultiBlockEntityContentsAndRemoveStructure<OilDerrickBlockEntity>(state, level, pos, newState, ::placeMultiBlock)

    override fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
        place(1, 0, 0, defaultStructureIO)
        place(-1, 0, 0, defaultStructureIO)
        place(0, 0, 1, defaultStructureIO)
        place(0, 0, -1, defaultStructureIO)
        fill(-1, 0, -1, 1, 5, 1, defaultStructurePart)
    }
}
