package dev.ntmr.nucleartech.content.item.weapon

import dev.ntmr.nucleartech.content.entity.projectile.EntityMiniNuke
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class FatManItem(properties: Properties, ammoItem: Item) : ProjectileGunItem(properties, 1, 100, 40, ammoItem) {
    override fun createProjectile(level: Level, player: Player, stack: ItemStack): Projectile {
        return EntityMiniNuke(level, player)
    }

    override fun getShootSound(): SoundEvent {
        return SoundEvents.GENERIC_EXPLODE // Placeholder
    }
}
