package dev.ntmr.nucleartech.content.entity.turret

import dev.ntmr.nucleartech.content.entity.EntityBullet
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level

class MissileTurretEntity(type: EntityType<MissileTurretEntity>, level: Level) : TurretEntity(type, level) {

    override fun getFireRate(): Int = 60 // Slow fire rate

    override fun fire(target: LivingEntity) {
        val direction = target.position().add(0.0, target.eyeHeight * 0.5, 0.0).subtract(this.position().add(0.0, 1.5, 0.0)).normalize()

        // Using EntityBullet with explosion for now, similar to Rocket Launcher
        // In future, replace with specific Missile entity if needed
        val rocket = object : EntityBullet(this.level(), this) {
            override fun onHit(result: net.minecraft.world.phys.HitResult) {
                if (!level().isClientSide) {
                    dev.ntmr.nucleartech.api.world.ExplosionLarge.createAndStart(level(), this.position(), 3.0f, dev.ntmr.nucleartech.api.world.ExplosionLargeParams())
                    this.discard()
                }
            }
        }.apply {
            setPos(this@MissileTurretEntity.x, this@MissileTurretEntity.y + 1.5, this@MissileTurretEntity.z)
            shoot(direction.x, direction.y, direction.z, 1.5f, 0.0f)
        }
        
        this.level().addFreshEntity(rocket)
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0f, 0.5f)
    }
}
