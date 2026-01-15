package dev.ntmr.nucleartech.content.block.rbmk

import dev.ntmr.nucleartech.api.block.entities.createSidedTickerChecked
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratedReaSimRodBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

class RBMKModeratedReaSimRodBlock(column: Supplier<out RBMKColumnBlock>, properties: Properties) : RBMKReaSimRodBlock(column, properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKModeratedReaSimRodBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, NTechBlockEntities.rbmkModeratedReaSimRodBlockEntityType.get())
}
