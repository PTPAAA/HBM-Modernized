package dev.ntmr.nucleartech.content.item.medical

import dev.ntmr.nucleartech.system.capability.Capabilities
import dev.ntmr.nucleartech.system.capability.contamination.decontaminate
import dev.ntmr.nucleartech.system.hazard.EntityContaminationHandler
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class RadAwayItem(properties: Properties) : Item(properties) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!world.isClientSide) {
            val capability = Capabilities.getContamination(player)
            if (capability != null) {
                // Remove 500 RADs
                EntityContaminationHandler.decontaminate(capability, EntityContaminationHandler.HazardType.Radiation, 500F)
                if (!player.isCreative) {
                    stack.shrink(1)
                }
                // TODO: Sound effect
                return InteractionResultHolder.consume(stack)
            }
        }
        return InteractionResultHolder.pass(stack)
    }
}
