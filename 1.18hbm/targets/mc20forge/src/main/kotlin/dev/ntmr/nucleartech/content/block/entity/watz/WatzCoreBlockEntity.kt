package dev.ntmr.nucleartech.content.block.entity.watz

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.machine.watz.WatzStructureBlock
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WatzCoreBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.watzCore.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(1_000_000_000, 10_000_000, 10_000_000)
    private var isStructureValid = false

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: WatzCoreBlockEntity) {
            if (level.isClientSide) return

            if (level.getGameTime() % 100 == 0L) {
                blockEntity.checkStructure()
            }
        }
    }

    private fun checkStructure() {
        val level = this.level ?: return
        // Placeholder check: Look for 6 structure blocks immediately around it
        var count = 0
        for (dir in net.minecraft.core.Direction.values()) {
            if (level.getBlockState(this.worldPosition.relative(dir)).block is WatzStructureBlock) {
                count++
            }
        }
        isStructureValid = count >= 1 // Very lenient start
    }
    
    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Energy", energy.serializeNBT())
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.deserializeNBT(tag.get("Energy"))
    }
}
