package dev.ntmr.nucleartech.content.entity.monster

import dev.ntmr.nucleartech.content.NTechItems
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class AlienEntity(type: EntityType<out AlienEntity>, level: Level) : Monster(type, level) {

    override fun registerGoals() {
        this.goalSelector.addGoal(0, FloatGoal(this))
        this.goalSelector.addGoal(2, MeleeAttackGoal(this, 1.0, false)) // Placeholder for Ranged
        this.goalSelector.addGoal(7, WaterAvoidingRandomStrollGoal(this, 1.0))
        this.goalSelector.addGoal(8, LookAtPlayerGoal(this, Player::class.java, 8.0F))
        this.goalSelector.addGoal(8, RandomLookAroundGoal(this))

        this.targetSelector.addGoal(1, HurtByTargetGoal(this))
        this.targetSelector.addGoal(2, NearestAttackableTargetGoal(this, Player::class.java, true))
    }

    companion object {
        fun createAttributes(): AttributeSupplier.Builder {
            return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
        }
    }
}
