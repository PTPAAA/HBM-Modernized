package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.api.block.entities.BombBlockEntity
import dev.ntmr.nucleartech.api.block.entities.SoundLoopBlockEntity
import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.api.item.TargetDesignator
import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.item.MissileItem
import dev.ntmr.nucleartech.content.menu.LaunchPadMenu
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.slots.data.IntDataSlot
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import dev.ntmr.nucleartech.system.energy.transferEnergy
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import java.util.function.Supplier

class LaunchPadBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(NTechBlockEntities.launchPadBlockEntityType.get(), pos, state), BombBlockEntity<LaunchPadBlockEntity>, TickingServerBlockEntity {
    val missileItem: ItemStack get() = mainInventory[0]

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(3, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.item is MissileItem<*>
        1 -> stack.item is TargetDesignator
        2 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        else -> false
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        if (!isClientSide() && slot == 0) {
            sendContinuousUpdatePacket()
        }
    }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, 10000, 0)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    override fun createMenu(windowID: Int, inventory: Inventory) = LaunchPadMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_LAUNCH_PAD.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
    }

    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 16, 3))

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for missile launch pad")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override val requiresComponentsToDetonate = true
    override fun getRequiredDetonationComponents(): Map<out Supplier<out Item>, Int> = emptyMap()
    override fun isComplete() = !mainInventory[0].isEmpty && !mainInventory[1].isEmpty
    override fun canDetonate(): Boolean {
        if (!super.canDetonate() || energy < LAUNCH_ENERGY_COST || mainInventory[0].item !is MissileItem<*>) return false
        val designator = mainInventory[1]
        if (designator.item !is TargetDesignator || !designator.hasTag()) return false
        return true
    }

    override fun detonate(): Boolean {
        val level = level
        if (level == null || level.isClientSide) return false

        val missile = mainInventory[0].item as? MissileItem<*> ?: return false
        val designator = mainInventory[1].item as? TargetDesignator ?: return false
        val designatorTag = mainInventory[1].tag ?: return false
        if (!designator.hasValidTarget(level, designatorTag, blockPos)) return false

        level.playSound(null, worldPosition, NTechSounds.missileTakeoff.get(), SoundSource.BLOCKS, 2F, 1F)
        if (!level.addFreshEntity(missile.missileSupplier(level, blockPos.above(2), designator.getTargetPos(level, designatorTag, blockPos)))) return false

        mainInventory[0].shrink(1)
        energy -= LAUNCH_ENERGY_COST
        setChanged()
        sendContinuousUpdatePacket()
        return true
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val energyItem = mainInventory[2]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        put("Missile", missileItem.save(CompoundTag()))
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        mainInventory[0] = ItemStack.of(tag.getCompound("Missile"))
    }

    init {
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 100_000
        const val LAUNCH_ENERGY_COST = 75_000
    }
}
