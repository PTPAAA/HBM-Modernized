package dev.ntmr.nucleartech.content.item.weapon

import dev.ntmr.nucleartech.content.item.GunItem
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents

class AlienBlasterItem(properties: Properties, ammo: Item) : GunItem(properties, 20, 40, 5, ammo) {

    override fun spawnProjectile(level: Level, player: Player, stack: ItemStack) {
        // Simple logic for now: spawn an EntityBullet with high damage and different visuals if possible
        // Since EntityBullet is generic, we can just set high damage.
        // Ideally we would have EntityPlasma or similar.
        val bullet = dev.ntmr.nucleartech.content.entity.EntityBullet(level, player)
        bullet.damage = 15.0f // High damage
        bullet.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 3.0f, 1.0f)
        bullet.setNoGravity(true) // Plasma should fly straight?
        level.addFreshEntity(bullet)
    }

    override fun getShootSound(): SoundEvent = SoundEvents.BEACON_ACTIVATE // Placeholder sci-fi sound
}
