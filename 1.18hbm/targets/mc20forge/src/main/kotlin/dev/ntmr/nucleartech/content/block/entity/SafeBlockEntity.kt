package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.menu.SafeMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

class SafeBlockEntity(pos: BlockPos, state: BlockState) : RandomizableContainerBlockEntity(NTechBlockEntities.safeBlockEntityType.get(), pos, state) {
    private var items = NonNullList.withSize(15, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            setChanged()
        }
    }

    override fun getContainerSize(): Int = 15

    private var inventoryCapability: LazyOptional<IItemHandlerModifiable>? = null

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        nbt.merge(inventory.serializeNBT())
    }

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        inventory.deserializeNBT(nbt)
    }

    override fun createMenu(windowId: Int, playerInventory: Inventory): AbstractContainerMenu =
        SafeMenu(windowId, playerInventory, this)

    override fun getDefaultName() = LangKeys.CONTAINER_SAFE.get()

    override fun getItems(): NonNullList<ItemStack> = items

    override fun setItems(newItems: NonNullList<ItemStack>) {
        items = newItems
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (inventoryCapability == null)
                inventoryCapability = LazyOptional.of(this::createHandler)
            return inventoryCapability!!.cast()
        }
        return super.getCapability(cap, side)
    }

    private fun createHandler(): IItemHandlerModifiable = inventory

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability?.invalidate()
    }
}
