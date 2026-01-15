package dev.ntmr.nucleartech.content.entity.effect

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import kotlin.math.sqrt

class BlackHoleEntity(type: EntityType<BlackHoleEntity>, level: Level) : Entity(type, level) {

    var size: Float
        get() = entityData.get(SIZE)
        set(value) = entityData.set(SIZE, value)

    constructor(type: EntityType<BlackHoleEntity>, level: Level, size: Float) : this(type, level) {
        this.size = size
    }

    override fun defineSynchedData() {
        entityData.define(SIZE, 1.0F)
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        size = compound.getFloat("Size")
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putFloat("Size", size)
    }

    override fun tick() {
        super.tick()

        if (level().isClientSide) {
            // Visuals
            for (i in 0..10) {
                val r = size * 2.0
                val theta = random.nextDouble() * Math.PI * 2
                val phi = random.nextDouble() * Math.PI
                val x = r * Math.sin(phi) * Math.cos(theta)
                val y = r * Math.sin(phi) * Math.sin(theta)
                val z = r * Math.cos(phi)
                
                // Accretion disk effect
                level().addParticle(ParticleTypes.SMOKE, this.x + x, this.y + y, this.z + z, -x * 0.1, -y * 0.1, -z * 0.1)
            }
        } else {
            // Logic
            val radius = size * 10
            val range = AABB.ofSize(position(), radius.toDouble(), radius.toDouble(), radius.toDouble())
            
            // Sustain
            if (tickCount > 1200) { // 1 minute life for now
                if (size > 0.1) size -= 0.05F
                else discard()
            } else {
                 if (size < 5.0F) size += 0.01F
            }


            // Entity Pull
            val entities = level().getEntities(this, range, EntitySelector.NO_SPECTATORS)
            for (entity in entities) {
                if (entity == this) continue
                
                val dx = this.x - entity.x
                val dy = this.y - entity.y
                val dz = this.z - entity.z
                val distSq = dx * dx + dy * dy + dz * dz
                val dist = sqrt(distSq)
                
                if (dist < size * 0.5) {
                    // Horizon
                    entity.hurt(level().damageSources().outOfWorld(), Float.MAX_VALUE)
                    if (!entity.isAlive) entity.discard() // Instakill
                } else {
                    // Succ
                    val strength = (1.0 - dist / radius) * 0.5
                    entity.deltaMovement = entity.deltaMovement.add(dx / dist * strength, dy / dist * strength, dz / dist * strength)
                    entity.resetFallDistance()
                }
            }

            // Block Succ
            if (tickCount % 5 == 0) {
                val blockRange = size.toInt() * 3
                for (x in -blockRange..blockRange) {
                    for (y in -blockRange..blockRange) {
                        for (z in -blockRange..blockRange) {
                            if (x*x + y*y + z*z > blockRange*blockRange) continue
                            
                            val pos = blockPosition().offset(x, y, z)
                            val state = level().getBlockState(pos)
                            
                            if (!state.isAir && state.getDestroySpeed(level(), pos) >= 0) {
                                // Don't eat bedrock
                                level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3)
                                // Chance to spawn falling block visual? Nah, too laggy for full black hole.
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getPacket(): Packet<ClientGamePacketListener> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }

    companion object {
        val SIZE: EntityDataAccessor<Float> = SynchedEntityData.defineId(BlackHoleEntity::class.java, EntityDataSerializers.FLOAT)
    }
}
