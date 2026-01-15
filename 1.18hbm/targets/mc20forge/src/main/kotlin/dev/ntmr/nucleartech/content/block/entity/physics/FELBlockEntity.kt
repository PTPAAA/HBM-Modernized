package dev.ntmr.nucleartech.content.block.entity.physics

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FELBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.fel.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(50_000_000, 500_000, 0)
    var laserPower: Int = 0

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: FELBlockEntity) {
            if (level.isClientSide) return
            
            // Placeholder logic: Fire laser if energy is sufficient
            if (blockEntity.energy.energyStored > 100_000) {
                 // Fire laser
            }
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Energy", energy.serializeNBT())
        tag.putInt("LaserPower", laserPower)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.deserializeNBT(tag.get("Energy"))
        laserPower = tag.getInt("LaserPower")
    }
}
