package dev.ntmr.nucleartech.content.block.entity.chemistry

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CatalyticReformerBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.catalyticReformer.get(), pos, state) {
    val energyStorage = EnergyStorageExposed(50000, 1000, 0)

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: CatalyticReformerBlockEntity) {
            // Processing logic
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
