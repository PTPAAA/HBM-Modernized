package dev.ntmr.nucleartech.api.item

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingEvent

public interface TickingArmor {
    public fun tickArmor(event: LivingEvent.LivingTickEvent, stack: ItemStack)
}
