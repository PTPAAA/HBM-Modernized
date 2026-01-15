package dev.ntmr.nucleartech.content.block.entity.physics

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BreedingReactorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.breedingReactor.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(1_000_000, 10_000, 0)
    var heat: Int = 0
    var flux: Int = 0

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: BreedingReactorBlockEntity) {
            if (level.isClientSide) return
            
            // Placeholder logic
            // Breeding fuel requires heat and neutron flux
            if (blockEntity.heat > 500) {
                 // Convert U238 to Pu239 gradually
            }
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Energy", energy.serializeNBT())
        tag.putInt("Heat", heat)
        tag.putInt("Flux", flux)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.deserializeNBT(tag.get("Energy"))
        heat = tag.getInt("Heat")
        flux = tag.getInt("Flux")
    }
}
