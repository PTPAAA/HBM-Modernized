package dev.ntmr.nucleartech.content.entity.projectile

import dev.ntmr.nucleartech.content.NTechEntities
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult

class EntityRocket(type: EntityType<out EntityRocket>, level: Level) : ThrowableProjectile(type, level) {
    constructor(level: Level, shooter: LivingEntity) : this(NTechEntities.rocket.get(), level) {
        this.owner = shooter
        this.setPos(shooter.x, shooter.eyeY - 0.1, shooter.z)
    }

    override fun onHit(result: HitResult) {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.x, this.y, this.z, 4.0f, Level.ExplosionInteraction.TNT)
            this.discard()
        }
    }

    override fun defineSynchedData() {}
}
