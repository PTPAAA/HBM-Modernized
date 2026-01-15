package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.particle.CoolingTowerCloudParticleOptions
import dev.ntmr.nucleartech.math.component1
import dev.ntmr.nucleartech.math.component2
import dev.ntmr.nucleartech.math.component3
import dev.ntmr.nucleartech.math.toVec3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class SmallCoolingTowerBlockEntity(pos: BlockPos, state: BlockState) : AbstractCoolingTowerBlockEntity(NTechBlockEntities.smallCoolingTowerBlockEntityType.get(), pos, state, 1000, 1000) {
    override val particleTact = 2

    override fun spawnParticles(level: Level, pos: BlockPos) {
        val (x, y, z) = pos.toVec3().add(0.5, 18.0, 0.5)
        level.addParticle(
            CoolingTowerCloudParticleOptions(0.5F, 4F, 1F, 250 + level.random.nextInt(250)),
            x, y, z,
            0.0, 0.0, 0.0
        )
    }

    override fun getRenderBoundingBox() = AABB(blockPos.offset(-2, 0, -2), blockPos.offset(3, 20, 3))

    override val ioConfigurations = IODelegatedBlockEntity.Companion.fromTriples(blockPos, Rotation.NONE,
        Triple(BlockPos(3, 0, 0), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.Companion.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-3, 0, 0), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.Companion.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, 3), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.Companion.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, -3), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.Companion.DEFAULT_FLUID_ACTION))),
    )
}
