package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.menu.BatteryBoxMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.ForgeCapabilities

class BatteryBoxBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(NTechBlockEntities.batteryBoxBlockEntityType.get(), pos, state), TickingServerBlockEntity {

    override val shouldPlaySoundLoop: Boolean = false
    override val soundLoopEvent: SoundEvent = SoundEvents.LAVA_POP
    
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, MAX_TRANSFER, MAX_TRANSFER)

    override val mainInventory: MutableList<ItemStack> = MutableList(2) { ItemStack.EMPTY }

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = stack.getCapability(ForgeCapabilities.ENERGY).isPresent

    override fun createMenu(windowID: Int, inventory: Inventory) = BatteryBoxMenu(windowID, inventory, this)
    override val defaultName: Component = Component.translatable("container.nucleartech.battery_box")

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        var changed = false
        
        // Slot 0: Discharge item into battery box
        val dischargeSlot = mainInventory[0]
        if (!dischargeSlot.isEmpty && energy < MAX_ENERGY) {
            dischargeSlot.getCapability(ForgeCapabilities.ENERGY).ifPresent { storage ->
                 if (storage.canExtract()) {
                     val transferred = transferEnergy(storage, energyStorage, MAX_TRANSFER)
                     if (transferred > 0) changed = true
                 }
            }
        }

        // Slot 1: Charge item from battery box
        val chargeSlot = mainInventory[1]
        if (!chargeSlot.isEmpty && energy > 0) {
             val transferred = transferEnergy(energyStorage, chargeSlot, MAX_TRANSFER)
             if (transferred > 0) changed = true
        }

        // Output to neighbors
        if (energy > 0) {
             for (side in Direction.values()) {
                 val neighbor = level.getBlockEntity(pos.relative(side))
                 if (neighbor != null) {
                     neighbor.getCapability(ForgeCapabilities.ENERGY, side.opposite).ifPresent { target ->
                         val transferred = transferEnergy(energyStorage, target, MAX_TRANSFER)
                         if (transferred > 0) changed = true
                     }
                 }
             }
        }
        
        if (changed) setChanged()
    }
    
    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
    }

    init {
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 2_000_000
        const val MAX_TRANSFER = 10_000
    }
}
