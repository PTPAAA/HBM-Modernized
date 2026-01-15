package dev.ntmr.nucleartech.content.block.machine

import dev.ntmr.nucleartech.content.block.entity.machine.TeleporterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor

class TeleporterBlock : BaseEntityBlock(Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion()) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = TeleporterBlockEntity(pos, state)

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return createTickerHelper(type, dev.ntmr.nucleartech.content.NTechBlockEntities.teleporter.get(), TeleporterBlockEntity::tick)
    }
}
