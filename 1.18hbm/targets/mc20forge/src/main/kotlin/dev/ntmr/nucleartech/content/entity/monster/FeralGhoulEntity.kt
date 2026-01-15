package dev.ntmr.nucleartech.content.entity.monster

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.monster.Zombie
import net.minecraft.world.level.Level

class FeralGhoulEntity(type: EntityType<out FeralGhoulEntity>, level: Level) : Zombie(type, level) {

    companion object {
        fun createAttributes(): AttributeSupplier.Builder {
            return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35) // Faster than normal zombies
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 2.0)
        }
    }

    override fun isBaby(): Boolean {
        return false
    }
}
