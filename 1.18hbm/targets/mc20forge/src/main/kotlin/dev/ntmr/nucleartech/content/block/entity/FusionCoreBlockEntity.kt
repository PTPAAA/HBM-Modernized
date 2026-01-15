package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.machine.FusionMagnetBlock
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FusionCoreBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.fusionCoreBlockEntityType.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(100_000_000, 1_000_000, 1_000_000)
    private var isStructureValid = false
    private var plasmaTemp = 0L

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: FusionCoreBlockEntity) {
            if (level.isClientSide) return

            if (level.getGameTime() % 100 == 0L) { // Check structure every 5 seconds
                blockEntity.checkStructure()
            }
            
            if (blockEntity.isStructureValid) {
                // Logic
            }
        }
    }

    private fun checkStructure() {
        val level = this.level ?: return
        var magnetCount = 0
        val radius = 3
        for (x in -radius..radius) {
            for (z in -radius..radius) {
                val checkPos = this.worldPosition.offset(x, 0, z)
                if (level.getBlockState(checkPos).block is FusionMagnetBlock) {
                    magnetCount++
                }
            }
        }
        
        isStructureValid = magnetCount >= 8 
    }
    
    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy.energyStored)
        tag.putLong("PlasmaTemp", plasmaTemp)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.receiveEnergy(tag.getInt("Energy"), false)
        plasmaTemp = tag.getLong("PlasmaTemp")
    }
}
