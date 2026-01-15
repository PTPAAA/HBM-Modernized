package dev.ntmr.nucleartech.content.fluid

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fluids.capability.IFluidHandler

class SimpleContainerFluidHandler(private val container: Container, fluidHandler: IFluidHandler) : ContainerFluidHandler, Container by container, IFluidHandler by fluidHandler {
    override fun getMaxStackSize(): Int {
        return container.maxStackSize
    }

    override fun startOpen(player: Player) {
        container.startOpen(player)
    }

    override fun stopOpen(player: Player) {
        container.stopOpen(player)
    }

    override fun canPlaceItem(slot: Int, stack: ItemStack): Boolean {
        return container.canPlaceItem(slot, stack)
    }

    override fun countItem(item: Item): Int {
        return container.countItem(item)
    }

    override fun hasAnyOf(items: Set<Item>): Boolean {
        return container.hasAnyOf(items)
    }
}
