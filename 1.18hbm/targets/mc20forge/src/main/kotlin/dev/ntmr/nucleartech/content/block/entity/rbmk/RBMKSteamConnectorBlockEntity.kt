package dev.ntmr.nucleartech.content.block.entity.rbmk

import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.content.block.entity.BaseMachineBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.rbmk.RBMKPart
import dev.ntmr.nucleartech.content.fluid.FluidOutputTank
import dev.ntmr.nucleartech.extensions.getOrNull
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.capability.IFluidHandler

class RBMKSteamConnectorBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(NTechBlockEntities.rbmkSteamConnectorBlockEntityType.get(), pos, state) {
    //region Boilerplate
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)

    override val defaultName: Component get() = throw UnsupportedOperationException("No menu for RBMK steam connectors")
    override fun canOpen(player: Player) = false
    override fun createMenu(windowID: Int, inventory: Inventory): AbstractContainerMenu = throw UnsupportedOperationException("No menu for RBMK steam connectors")
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK steam connectors")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        throw UnsupportedOperationException("No menu for RBMK steam connectors")
    }
    //endregion

    private val nullWaterTank = object : FluidOutputTank(0) {
        override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) =
            if (action.simulate()) FluidStack(Fluids.WATER, 1) else FluidStack.EMPTY

        override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) =
            if (resource.fluid.isSame(Fluids.WATER) && action.simulate()) FluidStack(Fluids.WATER, 1) else FluidStack.EMPTY
    }.also { it.forceFluid(FluidStack(Fluids.WATER, 0)) }

    private val nullTank = FluidOutputTank(0)

    override fun registerCapabilityHandlers() {
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, this::nullWaterTank, Direction.UP)
        registerCapabilityHandler(ForgeCapabilities.FLUID_HANDLER, {
            if (!hasLevel()) return@registerCapabilityHandler nullTank
            val abovePos = blockPos.offset(0, 2, 0)
            val aboveBlockState = levelUnchecked.getBlockState(abovePos)
            val aboveBlock = aboveBlockState.block
            return@registerCapabilityHandler if (aboveBlock is RBMKPart) {
                val boiler = aboveBlock.getTopRBMKBase(levelUnchecked, abovePos, aboveBlockState) as? RBMKBoilerBlockEntity ?: return@registerCapabilityHandler nullTank
                val steamCapability = boiler.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP)
                steamCapability.addListener { reviveCaps() } // when the boiler's capability gets invalidated, refresh ours
                steamCapability.getOrNull() ?: nullTank
            } else nullTank
        }, Direction.DOWN)
    }
}
