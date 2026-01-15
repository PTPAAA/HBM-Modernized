package dev.ntmr.nucleartech.content.entity.turret

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.level.Level

class LaserTurretEntity(type: EntityType<LaserTurretEntity>, level: Level) : TurretEntity(type, level) {

    override fun getFireRate(): Int = 10

    init {
        this.targetSelector.addGoal(1, NearestAttackableTargetGoal(this, Monster::class.java, true))
    }

    override fun fire(target: LivingEntity) {
        if (tickCount % 10 == 0) { // Slower fire rate
             // Instant damage, ignores armor if we could (using generic for now)
             target.hurt(level().damageSources().magic(), 10.0F)
             // Laser beam visual logic would go here
        }
    }
}
