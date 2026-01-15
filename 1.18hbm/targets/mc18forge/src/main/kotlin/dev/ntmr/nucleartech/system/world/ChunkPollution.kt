/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.world

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.extensions.getList
import dev.ntmr.nucleartech.system.config.NuclearConfig
import it.unimi.dsi.fastutil.ints.IntIntPair
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.SectionPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.saveddata.SavedData
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.living.LivingSpawnEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.fml.common.Mod
import java.util.*

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ChunkPollution {
    const val SOOT_PER_SECOND: Float = 1f / 25f
    const val HEAVY_METAL_PER_SECOND: Float = 1f / 50f
    const val POISON_PER_SECOND: Float = 1f / 50f

    fun changePollution(level: LevelAccessor, pos: BlockPos, type: PollutionType, amount: Float) {
        setPollution(level, pos, type, getPollution(level, pos, type) + amount)
    }

    fun addPollution(level: LevelAccessor, pos: BlockPos, deltas: PollutionData) {
        getOrCreatePollutionData(level, pos)?.addFrom(deltas.times(NuclearConfig.radiation.pollutionMultiplier.get().toFloat()))
        perWorldPollution[level]?.setDirty()
    }

    fun getPollution(level: LevelAccessor, pos: BlockPos, type: PollutionType): Float {
        val data = getPollutionData(level, pos) ?: return 0F
        return data.get(type)
    }

    fun getPollution(level: LevelAccessor, pos: BlockPos): PollutionData {
        return getPollutionData(level, pos)?.copy() ?: PollutionData()
    }

    fun setPollution(level: LevelAccessor, pos: BlockPos, type: PollutionType, value: Float) {
        val data = getOrCreatePollutionData(level, pos) ?: return
        data.set(type, value)

        if (data.isZeroed)
            perWorldPollution[level]?.data?.remove(blockToAreaPos(pos))

        perWorldPollution[level]?.setDirty()
    }

    @SubscribeEvent @JvmStatic
    fun onWorldLoad(event: WorldEvent.Load) {
        val level = event.world
        if (level !is ServerLevel || !NuclearConfig.radiation.enablePollution.get()) return
        val savedData = level.dataStorage.computeIfAbsent(PollutionSavedData::loadSavedData, ::PollutionSavedData, MODID + "_pollution")

        perWorldPollution[event.world] = savedData
    }

    @SubscribeEvent @JvmStatic
    fun onWorldUnload(event: WorldEvent.Unload) {
        if (!event.world.isClientSide)
            perWorldPollution.remove(event.world)
    }

    private var eggTimer = 0

    @SubscribeEvent @JvmStatic
    fun updateTimer(event: TickEvent.ServerTickEvent) {
        if (!NuclearConfig.radiation.enablePollution.get())
            return

        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            if (++eggTimer > 60) {
                update()
                eggTimer = 0
            }

            destroyEnvironment()
        }
    }

    val MAX_HEALTH_MOD: UUID = UUID.fromString("25462f6c-2cb2-4ca8-9b47-3a011cc61207")
    val ATTACK_DAMAGE_MOD: UUID = UUID.fromString("8f442d7c-d03f-49f6-a040-249ae742eed9")

    @SubscribeEvent @JvmStatic
    fun modifyMobAttributes(event: LivingSpawnEvent.SpecialSpawn) {
        if (event.world.isClientSide || !NuclearConfig.radiation.enablePollution.get() || !NuclearConfig.radiation.mobPollutionEffects.get()) return

        val entity = event.entityLiving
        if (entity !is Monster) return
        val data = getPollutionData(event.world, event.entity.blockPosition()) ?: return

        if (data.soot > NuclearConfig.radiation.mobSootThreshold.get()) {
            val maxHealth = entity.getAttribute(Attributes.MAX_HEALTH)
            val attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE)

            if (maxHealth != null && maxHealth.getModifier(MAX_HEALTH_MOD) == null) {
                maxHealth.addPermanentModifier(AttributeModifier(MAX_HEALTH_MOD, "Soot anger health increase", NuclearConfig.radiation.mobHealthModifier.get(), AttributeModifier.Operation.MULTIPLY_BASE))
            }
            if (attackDamage != null && attackDamage.getModifier(ATTACK_DAMAGE_MOD) == null) {
                attackDamage.addPermanentModifier(AttributeModifier(ATTACK_DAMAGE_MOD, "Soot anger damage increase", NuclearConfig.radiation.mobDamageModifier.get(), AttributeModifier.Operation.MULTIPLY_BASE))
            }

            entity.heal(entity.maxHealth)
        }
    }

    private fun update() {
        for ((_, worldPollution) in perWorldPollution) {
            val old = worldPollution.data.toMap()
            worldPollution.data.clear()

            for ((pos, pollution) in old) {
                val neighborSpread = PollutionData()

                if (pollution.soot > 10F) {
                    neighborSpread.soot = pollution.soot * 0.05F
                    pollution.soot *= 0.8F
                }

                pollution.soot *= 0.99F

                if (pollution.poison > 10F) {
                    neighborSpread.poison = pollution.poison * 0.025F
                    pollution.poison *= 0.9F
                } else {
                    pollution.poison *= 0.995F
                }

                pollution.heavyMetal *= 0.9995F

                val newPollution = worldPollution.data.getOrPut(pos, ::PollutionData)
                newPollution.addFrom(pollution)


                for (direction in Direction.Plane.HORIZONTAL.stream()) {
                    val neighborPos = IntIntPair.of(pos.leftInt() + direction.stepX, pos.rightInt() + direction.stepZ)
                    val neighborData = worldPollution.data.getOrPut(neighborPos, ::PollutionData)
                    neighborData.addFrom(neighborSpread)
                }
            }

            val iterator = worldPollution.data.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().value.isZeroed)
                    iterator.remove()
            }

            if (old.isNotEmpty())
                worldPollution.setDirty()
        }
    }

    private fun destroyEnvironment() {
        if (!NuclearConfig.radiation.worldPollutionEffects.get())
            return

        for ((level, worldPollution) in perWorldPollution) {
            for ((pos, pollution) in worldPollution.data) {
                if (pollution.poison >= NuclearConfig.radiation.worldPoisonThreshold.get()) repeat(NuclearConfig.radiation.worldEffectCount.get()) {
                    val x = areaToBlockPos(pos.firstInt()) + level.random.nextInt(64)
                    val z = areaToBlockPos(pos.secondInt()) + level.random.nextInt(64)

                    if (level.hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))) {
                        val y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - level.random.nextInt(3) + 1
                        val blockPos = BlockPos(x, y, z)
                        val blockState = level.getBlockState(blockPos)

                        // TODO configurable world effects
                        if (blockState.`is`(BlockTags.DIRT) && !blockState.`is`(Blocks.COARSE_DIRT))
                            level.setBlock(blockPos, Blocks.COARSE_DIRT.defaultBlockState(), Block.UPDATE_ALL)
                        else if (blockState.material == Material.PLANT || blockState.`is`(BlockTags.LEAVES) || blockState.`is`(BlockTags.REPLACEABLE_PLANTS) || blockState.`is`(BlockTags.TALL_FLOWERS)) {
                            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)
                        }
                    }
                }
            }
        }
    }

    // Data is mapped for each 4x4 area of chunks
    private val perWorldPollution = mutableMapOf<LevelAccessor, PollutionSavedData>()

    private fun getPollutionData(level: LevelAccessor, pos: BlockPos): PollutionData? =
        if (NuclearConfig.radiation.enablePollution.get())
            perWorldPollution[level]?.data?.get(blockToAreaPos(pos))
        else null

    private fun getOrCreatePollutionData(level: LevelAccessor, pos: BlockPos): PollutionData? =
        if (NuclearConfig.radiation.enablePollution.get())
            perWorldPollution[level]?.data?.getOrPut(blockToAreaPos(pos), ::PollutionData)
        else null

    private fun blockToAreaPos(pos: BlockPos) = IntIntPair.of(pos.x shr 6, pos.z shr 6)
    private fun areaToBlockPos(coordinate: Int) = coordinate shl 6

    data class PollutionData(
        var soot: Float = 0F,
        var poison: Float = 0F,
        var heavyMetal: Float = 0F,
    ) {
        val isZeroed: Boolean get() = soot == 0F && poison == 0F && heavyMetal == 0F

        constructor(tag: CompoundTag) : this(
            soot = tag.getFloat("soot"),
            poison = tag.getFloat("poison"),
            heavyMetal = tag.getFloat("heavy_metal"),
        )

        fun get(type: PollutionType): Float = when (type) {
            PollutionType.Soot -> soot
            PollutionType.Poison -> poison
            PollutionType.HeavyMetal -> heavyMetal
        }

        fun set(type: PollutionType, value: Float) = when (type) {
            PollutionType.Soot -> soot = value.coerceIn(0F, 10_000F)
            PollutionType.Poison -> poison = value.coerceIn(0F, 10_000F)
            PollutionType.HeavyMetal -> heavyMetal = value.coerceIn(0F, 10_000F)
        }

        fun addFrom(other: PollutionData) {
            soot += other.soot
            poison += other.poison
            heavyMetal += other.heavyMetal
        }

        fun getSaveTag() = CompoundTag().apply {
            putFloat("soot", soot)
            putFloat("poison", poison)
            putFloat("heavy_metal", heavyMetal)
        }

        fun toMap() = mapOf(
            PollutionType.Soot to soot,
            PollutionType.Poison to poison,
            PollutionType.HeavyMetal to heavyMetal,
        )

        operator fun times(factor: Float) = PollutionData(
            soot = soot * factor,
            poison = poison * factor,
            heavyMetal = heavyMetal * factor,
        )
    }

    private class PollutionSavedData(val data: MutableMap<IntIntPair, PollutionData> = mutableMapOf()) : SavedData() {
        override fun save(tag: CompoundTag) = tag.apply {
            val listTag = ListTag()
            for ((pos, pollutionData) in data) {
                if (pollutionData.isZeroed)
                    continue

                listTag.add(CompoundTag().apply {
                    putLong("Coordinate", (pos.leftInt().toLong() shl 32) or pos.rightInt().toUInt().toLong())
                    put("PollutionData", pollutionData.getSaveTag())
                })
            }
            put("PollutionMap", listTag)
        }

        companion object {
            fun loadSavedData(tag: CompoundTag): PollutionSavedData {
                val listTag = tag.getList("PollutionMap", Tag.TAG_COMPOUND)
                val map = mutableMapOf<IntIntPair, PollutionData>()
                for (item in listTag) {
                    item as CompoundTag
                    val packedCoordinates = item.getLong("Coordinate")
                    val data = PollutionData(item.getCompound("PollutionData"))
                    map[IntIntPair.of((packedCoordinates ushr 32).toInt(), packedCoordinates.toInt())] = data
                }
                return PollutionSavedData(map)
            }
        }
    }

    enum class PollutionType {
        Soot, Poison, HeavyMetal
    }
}
