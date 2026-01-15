package dev.ntmr.nucleartech.content.entity.turret

import dev.ntmr.nucleartech.content.entity.EntityBullet
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class GunTurretEntity(type: EntityType<GunTurretEntity>, level: Level) : TurretEntity(type, level) {

    override fun getFireRate(): Int = 5 // Fast fire rate

    override fun fire(target: LivingEntity) {
        val direction = target.position().add(0.0, target.eyeHeight * 0.5, 0.0).subtract(this.position().add(0.0, 1.5, 0.0)).normalize()
        
        val bullet = EntityBullet(this.level(), this).apply {
            setPos(this@GunTurretEntity.x, this@GunTurretEntity.y + 1.5, this@GunTurretEntity.z)
            shoot(direction.x, direction.y, direction.z, 2.0f, 0.5f)
            damage = 5.0f
        }
        this.level().addFreshEntity(bullet)
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0f, 2.0f) // Placeholder sound
    }
}
