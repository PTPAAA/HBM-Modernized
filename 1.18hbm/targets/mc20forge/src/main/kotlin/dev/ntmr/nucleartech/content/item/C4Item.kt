package dev.ntmr.nucleartech.content.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class C4Item(properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        // Placeholder for C4 placement logic
        // Ideally spawns a C4 Entity or Block
        return InteractionResult.SUCCESS
    }
}
