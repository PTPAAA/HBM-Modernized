package dev.ntmr.nucleartech.content.block.entity.physics

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CyclotronBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.cyclotron.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(10_000_000, 100_000, 0)
    var progress: Int = 0

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: CyclotronBlockEntity) {
            if (level.isClientSide) return
            
            // Placeholder logic
            if (blockEntity.energy.energyStored > 1000) {
                 // Accelerate particles
            }
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Energy", energy.serializeNBT())
        tag.putInt("Progress", progress)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.deserializeNBT(tag.get("Energy"))
        progress = tag.getInt("Progress")
    }
}
