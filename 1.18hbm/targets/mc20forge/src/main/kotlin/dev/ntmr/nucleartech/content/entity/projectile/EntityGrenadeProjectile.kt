package dev.ntmr.nucleartech.content.entity.projectile

import dev.ntmr.nucleartech.content.NTechEntities
import dev.ntmr.nucleartech.content.item.weapon.GrenadeItem
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult

class EntityGrenadeProjectile(type: EntityType<out EntityGrenadeProjectile>, level: Level) : ThrowableItemProjectile(type, level) {
    
    var grenadeType: GrenadeItem.GrenadeType = GrenadeItem.GrenadeType.NORMAL

    constructor(level: Level, shooter: LivingEntity, type: GrenadeItem.GrenadeType) : this(NTechEntities.grenade.get(), level) {
        this.owner = shooter
        this.grenadeType = type
        this.setPos(shooter.x, shooter.eyeY - 0.1, shooter.z)
    }

    override fun getDefaultItem(): Item {
        return Items.SNOWBALL // Placeholder
    }

    override fun onHit(result: HitResult) {
        if (!this.level().isClientSide) {
            when (grenadeType) {
                GrenadeItem.GrenadeType.NORMAL -> this.level().explode(this, this.x, this.y, this.z, 3.0f, Level.ExplosionInteraction.TNT)
                GrenadeItem.GrenadeType.BLACK_HOLE -> {
                     dev.ntmr.nucleartech.content.entity.effect.BlackHoleEntity.create(this.level(), this.blockPosition(), 5.0f)
                }
                GrenadeItem.GrenadeType.EMP -> {
                     // EMP Effect logic placeholder
                     this.level().explode(this, this.x, this.y, this.z, 0.0f, Level.ExplosionInteraction.NONE)
                }
            }
            this.discard()
        }
    }
}
