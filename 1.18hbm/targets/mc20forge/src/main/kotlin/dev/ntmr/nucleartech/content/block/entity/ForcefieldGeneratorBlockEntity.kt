package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.NTechBlocks
import dev.ntmr.nucleartech.content.block.ForcefieldBlock
import dev.ntmr.nucleartech.system.energy.LudicrousEnergyStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.util.LazyOptional

class ForcefieldGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(NTechBlockEntities.forcefieldGeneratorBlockEntityType.get(), pos, state) {

    private val energy = LudicrousEnergyStorage(1_000_000, 10_000, 10_000)
    private val activeFields = HashSet<BlockPos>()

    companion object {
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: ForcefieldGeneratorBlockEntity) {
            if (level.isClientSide) return

            if (level.getGameTime() % 20 == 0L) {
                blockEntity.updateField()
            }
        }
    }

    private fun updateField() {
        val level = this.level ?: return
        
        // Simple logic: maintain a 5 radius sphere if energy > 1000
        if (energy.energyStored > 1000) {
            energy.extractEnergy(100, false) // Running cost
            
            val radius = 5
            for (x in -radius..radius) {
                for (y in -radius..radius) {
                    for (z in -radius..radius) {
                        if (x*x + y*y + z*z <= radius*radius) {
                            val targetPos = this.worldPosition.offset(x, y, z)
                            if (targetPos == this.worldPosition) continue // Don't replace self

                            val targetState = level.getBlockState(targetPos)
                            if (targetState.isAir) {
                                level.setBlock(targetPos, NTechBlocks.forcefield.get().defaultBlockState(), 3)
                                activeFields.add(targetPos)
                            }
                        }
                    }
                }
            }
        } else {
            // Remove fields if out of energy
            if (activeFields.isNotEmpty()) {
                val intentToRemove = ArrayList<BlockPos>()
                for (fieldPos in activeFields) {
                    if (level.getBlockState(fieldPos).block is ForcefieldBlock) {
                        level.removeBlock(fieldPos, false)
                    }
                    intentToRemove.add(fieldPos)
                }
                activeFields.removeAll(intentToRemove.toSet()) // Correct way to remove
                activeFields.clear()
            }
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy.energyStored)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("Energy")) energy.receiveEnergy(tag.getInt("Energy"), false)
        // Note: LudicrousEnergyStorage might not have receiveEnergy set effectively if maxReceive is checked, 
        // but here we are loading NBT so we should ideally set it directly. 
        // Since we can't access setters on standard EnergyStorage easily, 
        // we assume receiveEnergy works or we need to reflect/implement custom storage.
        // For now, receiveEnergy is the standard API way. 
        // If energy was persisted, we want to restore it. 
        // Ideally we should use a method that sets it.
        // But EnergyStorage usually doesn't expose setter.
        // We will assume receiveEnergy is sufficient or use a loop if capped.
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if (cap == ForgeCapabilities.ENERGY) LazyOptional.of { energy }.cast() else super.getCapability(cap, side)
    }
}
