package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.ntm
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import java.util.function.Predicate

abstract class GunItem(properties: Properties, val magazineSize: Int, val reloadTime: Int, val fireRate: Int, val ammoItem: Item) : Item(properties) {

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (isReloading(stack)) return InteractionResultHolder.fail(stack)

        val ammo = getAmmo(stack)

        if (ammo > 0) {
            // Firing logic handled in fire(), but called here or via packet if we go complex. 
            // For now, simple right-click to shoot semi-auto or hold for auto (needs tick logic).
            // Let's do simple instant fire for now.
            shoot(level, player, stack)
            return InteractionResultHolder.consume(stack)
        } else {
            // Try reload
            startReload(player, stack)
            return InteractionResultHolder.pass(stack)
        }
    }

    // Checking for reload key logic would go here if using client input events, 
    // but for now simple right-click empty = reload is a good fallback.
    
    fun shoot(level: Level, player: Player, stack: ItemStack) {
        if (level.isClientSide) return
        
        // Cooldown check
        if (player.cooldowns.isOnCooldown(this)) return

        // Consume ammo
        setAmmo(stack, getAmmo(stack) - 1)
        player.cooldowns.addCooldown(this, fireRate)

        // Sound
        level.playSound(null, player.x, player.y, player.z, getShootSound(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f)

        // Spawn Bullet
        spawnProjectile(level, player, stack)
    }

    abstract fun spawnProjectile(level: Level, player: Player, stack: ItemStack)
    abstract fun getShootSound(): SoundEvent

    fun startReload(player: Player, stack: ItemStack) {
        if (getAmmo(stack) >= magazineSize) return // Full
        
        // Check inventory for ammo
        val ammoStack = findAmmo(player)
        if (ammoStack.isEmpty && !player.isCreative) return

        // Start reload state
        setReloading(stack, true)
        setReloadTimer(stack, reloadTime)
        
        // Play reload sound
        player.level().playSound(null, player.x, player.y, player.z, getReloadSound(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f)
    }
    
    open fun getReloadSound(): SoundEvent = SoundEvents.LEVER_CLICK

    private fun findAmmo(player: Player): ItemStack {
        if (player.isCreative) return ItemStack(ammoItem) // Dummy stack
        
        for (i in 0 until player.inventory.containerSize) {
            val s = player.inventory.getItem(i)
            if (s.item == ammoItem) return s
        }
        return ItemStack.EMPTY
    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (level.isClientSide) return
        
        if (isReloading(stack)) {
            var timer = getReloadTimer(stack)
            if (timer > 0) {
                setReloadTimer(stack, timer - 1)
            } else {
                // Reload complete
                completeReload(entity as? Player ?: return, stack)
            }
        }
    }

    private fun completeReload(player: Player, stack: ItemStack) {
        setReloading(stack, false)
        
        val currentAmmo = getAmmo(stack)
        val needed = magazineSize - currentAmmo
        if (needed <= 0) return

        if (player.isCreative) {
            setAmmo(stack, magazineSize)
            return
        }

        val ammoStack = findAmmo(player)
        if (!ammoStack.isEmpty) {
            val toAdd = Math.min(needed, ammoStack.count)
            setAmmo(stack, currentAmmo + toAdd)
            ammoStack.shrink(toAdd)
        }
    }

    // NBT Helpers
    fun getAmmo(stack: ItemStack): Int = stack.orCreateTag.getInt("Ammo")
    fun setAmmo(stack: ItemStack, count: Int) = stack.orCreateTag.putInt("Ammo", count)

    fun isReloading(stack: ItemStack): Boolean = stack.orCreateTag.getBoolean("Reloading")
    fun setReloading(stack: ItemStack, reloading: Boolean) = stack.orCreateTag.putBoolean("Reloading", reloading)

    fun getReloadTimer(stack: ItemStack): Int = stack.orCreateTag.getInt("ReloadTimer")
    fun setReloadTimer(stack: ItemStack, time: Int) = stack.orCreateTag.putInt("ReloadTimer", time)
    
    override fun isBarVisible(stack: ItemStack): Boolean = true
    override fun getBarWidth(stack: ItemStack): Int {
        return Math.round(13.0f * getAmmo(stack).toFloat() / magazineSize.toFloat())
    }
    override fun getBarColor(stack: ItemStack): Int = 0x00FF00
}
