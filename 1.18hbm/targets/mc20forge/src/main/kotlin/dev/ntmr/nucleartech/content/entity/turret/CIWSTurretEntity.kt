package dev.ntmr.nucleartech.content.entity.turret

import dev.ntmr.nucleartech.content.entity.missile.ICBMEntity
import dev.ntmr.nucleartech.content.entity.missile.MissileEntity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.Level

class CIWSTurretEntity(type: EntityType<CIWSTurretEntity>, level: Level) : TurretEntity(type, level) {

    override fun getFireRate(): Int = 2

    init {
        this.targetSelector.addGoal(1, NearestAttackableTargetGoal(this, MissileEntity::class.java, true))
        this.targetSelector.addGoal(2, NearestAttackableTargetGoal(this, ICBMEntity::class.java, true))
        this.targetSelector.addGoal(3, NearestAttackableTargetGoal(this, Monster::class.java, true))
    }

    override fun fire(target: LivingEntity) {
        // High fire rate, low damage, hitscan-like behavior for missiles
        if (tickCount % 2 == 0) { // Fire every 2 ticks
            if (target is MissileEntity || target is ICBMEntity) {
                target.hurt(level().damageSources().generic(), 5.0F) // Anti-missile
            } else {
                target.hurt(level().damageSources().generic(), 2.0F) // Anti-mob
            }
            // Add sound/particles here
        }
    }
}
