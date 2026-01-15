package dev.ntmr.nucleartech.content.block.entity.physics

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class LHCBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.lhc.get(), pos, state) {
    val energyStorage = EnergyStorageExposed(100000000, 100000, 0) // 100M HE capacity

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: LHCBlockEntity) {
            if (!level.isClientSide) {
                 // Placeholder: Validate 100-diameter ring structure
                 // Placeholder: Consume insane energy
                 // Placeholder: Spawn Antimatter or Black Hole
            }
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energyStorage.deserializeNBT(tag.get("Energy"))
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Energy", energyStorage.serializeNBT())
    }
}
