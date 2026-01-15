package dev.ntmr.nucleartech.content.block.entity.oil

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.machine.oil.RefineryStructureBlock
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class OilRefineryBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.oilRefinery.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(1_000_000, 1_000, 0)
    private var isStructureValid = false

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: OilRefineryBlockEntity) {
            if (level.isClientSide) return

            if (level.getGameTime() % 40 == 0L) {
                blockEntity.checkStructure()
            }
        }
    }

    private fun checkStructure() {
        val level = this.level ?: return
        // Minimal vertical check: 3 blocks high tower above
        var valid = true
        for (i in 1..3) {
            if (level.getBlockState(this.worldPosition.above(i)).block !is RefineryStructureBlock) {
                valid = false
                break
            }
        }
        isStructureValid = valid
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
