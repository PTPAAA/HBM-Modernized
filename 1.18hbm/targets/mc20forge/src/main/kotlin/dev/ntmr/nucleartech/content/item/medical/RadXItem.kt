package dev.ntmr.nucleartech.content.item.medical

import dev.ntmr.nucleartech.content.NTechMobEffects
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class RadXItem(properties: Properties) : Item(properties) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!world.isClientSide) {
            // Apply radiation resistance for 10 minutes (12000 ticks)
            player.addEffect(MobEffectInstance(NTechMobEffects.radiationResistance.get(), 12000, 0))
            if (!player.isCreative) {
                stack.shrink(1)
            }
            // TODO: Sound effect
            return InteractionResultHolder.consume(stack)
        }
        return InteractionResultHolder.pass(stack)
    }
}
