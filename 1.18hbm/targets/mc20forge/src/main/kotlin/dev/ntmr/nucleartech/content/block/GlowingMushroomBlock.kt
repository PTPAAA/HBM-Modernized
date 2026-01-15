package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.NTechTags
import dev.ntmr.nucleartech.content.NTechBlocks
import dev.ntmr.nucleartech.content.NTechWorldFeatures
import dev.ntmr.nucleartech.system.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.MushroomBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.util.RandomSource
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceKey
import java.util.function.Supplier

class GlowingMushroomBlock(properties: Properties) : MushroomBlock(properties, ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation("hydrogen", "huge_glowing_mushroom"))) {
    override fun canSurvive(state: BlockState, world: LevelReader, pos: BlockPos): Boolean =
        world.getBlockState(pos.below()).`is`(NTechTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK)

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (NuclearConfig.world.enableGlowingMyceliumSpread.get() &&
            world.getBlockState(pos.below()).`is`(NTechBlocks.deadGrass.get()) &&
            random.nextInt(5) == 0
        ) {
            world.setBlockAndUpdate(pos.below(), NTechBlocks.glowingMycelium.get().defaultBlockState()) // create new mycelium
        }
        if (random.nextInt(if (world.getBlockState(pos.below()).`is`(NTechBlocks.glowingMycelium.get())) 10 else 1000) != 0) return
        var currentPos = pos
        var count = 5
        for (blockPos in BlockPos.betweenClosed(currentPos.offset(-5, -1, -5), currentPos.offset(5, 1, 5))) {
            if (world.getBlockState(blockPos).`is`(this)) {
                count--
                if (count <= 0) return
            }
        }
        var newPos = currentPos.offset(
            random.nextInt(4) - 1,
            random.nextInt(2) - random.nextInt(2),
            random.nextInt(4) - 1
        )
        for (k in 0..3) { // spread
            if (world.isEmptyBlock(newPos) && state.canSurvive(world, newPos)) {
                currentPos = newPos
            }
            newPos = currentPos.offset(
                random.nextInt(4) - 1,
                random.nextInt(2) - random.nextInt(2),
                random.nextInt(4) - 1
            )
        }
        if (world.isEmptyBlock(newPos) && state.canSurvive(world, newPos)) {
            world.setBlock(newPos, state, 2)
        }
    }
}
