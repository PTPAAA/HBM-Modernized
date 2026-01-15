package dev.ntmr.nucleartech.content.entity.monster

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.monster.Vindicator
import net.minecraft.world.level.Level

class RaiderEntity(type: EntityType<out RaiderEntity>, level: Level) : Vindicator(type, level) {

    companion object {
        fun createAttributes(): AttributeSupplier.Builder {
            return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
        }
    }
    
    // Will equip with guns via loot tables or spawning logic later
}
