package dev.ntmr.nucleartech.content.entity.turret

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.level.Level
import net.minecraft.core.particles.ParticleTypes

class HeavyRailgunTurretEntity(type: EntityType<HeavyRailgunTurretEntity>, level: Level) : TurretEntity(type, level) {

    override fun getFireRate(): Int = 40

    init {
        this.targetSelector.addGoal(1, NearestAttackableTargetGoal(this, Monster::class.java, true))
    }

    override fun fire(target: LivingEntity) {
        if (tickCount % 40 == 0) { // Very slow fire rate (2 seconds)
             // Massive damage
             target.hurt(level().damageSources().generic(), 50.0F)
             
             // Railgun trail effect
             if (level().isClientSide) {
                  val dx = target.x - x
                  val dy = target.eyeY - eyeY
                  val dz = target.z - z
                  for (i in 0..20) {
                      level().addParticle(ParticleTypes.ELECTRIC_SPARK, x + dx * i / 20.0, eyeY + dy * i / 20.0, z + dz * i / 20.0, 0.0, 0.0, 0.0)
                  }
             }
        }
    }
}
