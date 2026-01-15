package dev.ntmr.nucleartech.content.entity

import dev.ntmr.nucleartech.content.NTechEntities
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks

open class EntityBullet(type: EntityType<out EntityBullet>, level: Level) : Projectile(type, level) {

    var damage = 5.0f

    constructor(level: Level, shooter: LivingEntity, damage: Float) : this(NTechEntities.bullet.get(), level) {
        this.owner = shooter
        this.damage = damage
        this.setPos(shooter.eyePosition.x, shooter.eyePosition.y - 0.1, shooter.eyePosition.z)
        this.setDeltaMovement(shooter.lookAngle.scale(3.0)) // High speed
    }

    constructor(level: Level, shooter: LivingEntity) : this(level, shooter, 5.0f)

    override fun defineSynchedData() {}

    override fun tick() {
        super.tick()

        val hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity)
        if (hitResult.type != HitResult.Type.MISS) {
            onHit(hitResult)
        }

        checkInsideBlocks()
        
        val movement = deltaMovement
        val nextPos = position().add(movement)
        
        // Trail particles could go here
        
        setPos(nextPos)
        
        if (!isNoGravity) {
            deltaMovement = deltaMovement.add(0.0, -0.05, 0.0) // Gravity
        }
        
        if (this.tickCount > 200) { // Despawn after 10 seconds
            discard()
        }
    }

    override fun onHitEntity(result: EntityHitResult) {
        super.onHitEntity(result)
        val target = result.entity
        val owner = this.owner
        
        if (target != owner) {
            target.hurt(this.damageSources().thrown(this, owner), damage)
            discard()
        }
    }

    override fun onHitBlock(result: BlockHitResult) {
        super.onHitBlock(result)
        // Bullet hole effect?
        discard()
    }

    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }
}
