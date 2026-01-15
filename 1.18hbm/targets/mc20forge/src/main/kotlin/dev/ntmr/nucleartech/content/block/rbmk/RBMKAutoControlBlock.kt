package dev.ntmr.nucleartech.content.block.rbmk

import dev.ntmr.nucleartech.api.block.entities.createSidedTickerChecked
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAutoControlBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

class RBMKAutoControlBlock(column: Supplier<out RBMKColumnBlock>, properties: Properties) : RBMKBaseBlock(column, properties) {
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(LID_TYPE, LidType.CONCRETE)
    override val hasMenu = true
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKAutoControlBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, NTechBlockEntities.rbmkAutoControlBlockEntityType.get())
}
