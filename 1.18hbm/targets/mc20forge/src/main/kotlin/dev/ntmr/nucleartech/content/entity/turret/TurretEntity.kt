package dev.ntmr.nucleartech.content.entity.turret

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.OwnableEntity
import java.util.UUID

abstract class TurretEntity(type: EntityType<out TurretEntity>, level: Level) : PathfinderMob(type, level), OwnableEntity {

    private var ownerUUID: UUID? = null

    override fun getOwnerUUID(): UUID? = ownerUUID

    fun setOwnerUUID(uuid: UUID?) {
        this.ownerUUID = uuid
    }

    override fun getOwner(): LivingEntity? {
        return try {
            val uuid = this.ownerUUID
            if (uuid == null) null else this.level().getPlayerByUUID(uuid)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    override fun addAdditionalSaveData(tag: CompoundTag) {
        super.addAdditionalSaveData(tag)
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID!!)
        }
    }

    override fun readAdditionalSaveData(tag: CompoundTag) {
        super.readAdditionalSaveData(tag)
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner")
        }
    }

    override fun registerGoals() {
        this.goalSelector.addGoal(0, LookAtPlayerGoal(this, Player::class.java, 8.0f))
        this.goalSelector.addGoal(1, RandomLookAroundGoal(this))
        
        this.targetSelector.addGoal(1, NearestAttackableTargetGoal(this, Monster::class.java, true))
        this.targetSelector.addGoal(2, NearestAttackableTargetGoal(this, Player::class.java, 10, true, false) {
            it.uuid != ownerUUID
        })
    }

    override fun tick() {
        super.tick()
        if (!this.level().isClientSide) {
            val target = this.target
            if (target != null && target.isAlive && this.hasLineOfSight(target) && target.uuid != ownerUUID) {
                this.lookControl.setLookAt(target, 30.0f, 30.0f)
                if (this.tickCount % getFireRate() == 0) {
                    fire(target)
                }
            } else if (target?.uuid == ownerUUID) {
                this.target = null
            }
        }
        
        // Prevent movement
        this.deltaMovement = net.minecraft.world.phys.Vec3.ZERO
    }

    abstract fun getFireRate(): Int // Ticks between shots
    abstract fun fire(target: LivingEntity)

    companion object {
        fun createAttributes(): AttributeSupplier.Builder {
            return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0) // Turrets don't move
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
        }
    }
    
    // Prevent pushing
    override fun isPushable(): Boolean = false
}
