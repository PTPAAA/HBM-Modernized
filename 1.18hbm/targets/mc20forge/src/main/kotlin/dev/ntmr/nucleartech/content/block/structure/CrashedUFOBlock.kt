package dev.ntmr.nucleartech.content.block.structure

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.MapColor

class CrashedUFOBlock : Block(Properties.of().mapColor(MapColor.METAL).strength(5.0F, 1200.0F).sound(SoundType.METAL).requiresCorrectToolForDrops())
