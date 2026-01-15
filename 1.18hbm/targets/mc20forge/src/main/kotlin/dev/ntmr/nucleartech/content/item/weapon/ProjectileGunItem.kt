package dev.ntmr.nucleartech.content.item.weapon

import dev.ntmr.nucleartech.content.item.GunItem
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

abstract class ProjectileGunItem(properties: Properties, magazineSize: Int, reloadTime: Int, fireRate: Int, ammoItem: Item) : GunItem(properties, magazineSize, reloadTime, fireRate, ammoItem) {

    override fun spawnProjectile(level: Level, player: Player, stack: ItemStack) {
        val proj = createProjectile(level, player, stack)
        proj.shootFromRotation(player, player.xRot, player.yRot, 0.0f, getProjectileVelocity(), 1.0f)
        level.addFreshEntity(proj)
    }

    abstract fun createProjectile(level: Level, player: Player, stack: ItemStack): net.minecraft.world.entity.projectile.Projectile
    
    open fun getProjectileVelocity(): Float = 1.5f
}
