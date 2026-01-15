package dev.ntmr.nucleartech.content.block

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.extensions.yellow
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.parameters.LootContextParams

@Suppress("OVERRIDE_DEPRECATION")
class VolcanicGemOreBlock(properties: Properties) : RotatedPillarBlock(properties) {
    override fun appendHoverText(stack: ItemStack, level: BlockGetter?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += LangKeys.INFO_ORE_CLUSTER.yellow()
    }

    override fun getDrops(state: BlockState, context: LootParams.Builder): List<ItemStack> =
        if (context.getOptionalParameter(LootContextParams.THIS_ENTITY) is Player)
            @Suppress("DEPRECATION") super.getDrops(state, context)
        else emptyList()
}
