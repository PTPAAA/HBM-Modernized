package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.content.entity.EntityBullet
import dev.ntmr.nucleartech.content.NTechItems
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class RevolverItem(properties: Properties) : GunItem(properties, 6, 40, 10, NTechItems.ammo357Magnum.get()) {
    override fun spawnProjectile(level: Level, player: Player, stack: ItemStack) {
        val bullet = EntityBullet(level, player, 10.0f)
        level.addFreshEntity(bullet)
    }

    override fun getShootSound(): SoundEvent = SoundEvents.GENERIC_EXPLODE // Placeholder
}

class AssaultRifleItem(properties: Properties) : GunItem(properties, 30, 60, 2, NTechItems.ammo556Nato.get()) {
    override fun spawnProjectile(level: Level, player: Player, stack: ItemStack) {
        val bullet = EntityBullet(level, player, 6.0f)
        level.addFreshEntity(bullet)
    }
    
    override fun getShootSound(): SoundEvent = SoundEvents.GENERIC_EXPLODE // Placeholder
}

class RocketLauncherItem(properties: Properties) : GunItem(properties, 1, 80, 20, NTechItems.ammoRocket.get()) {
    override fun spawnProjectile(level: Level, player: Player, stack: ItemStack) {
        val bullet = object : EntityBullet(level, player, 50.0f) {
             override fun onHit(result: net.minecraft.world.phys.HitResult) {
                 super.onHit(result)
                 if (!level().isClientSide) {
                     dev.ntmr.nucleartech.system.explosion.ExplosionLarge.createAndStart(level(), position(), 5.0f, dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams(cloud=true, rubble=true, shrapnel=true))
                 }
             }
        }
        level.addFreshEntity(bullet)
    }

    override fun getShootSound(): SoundEvent = SoundEvents.GENERIC_EXPLODE
}

