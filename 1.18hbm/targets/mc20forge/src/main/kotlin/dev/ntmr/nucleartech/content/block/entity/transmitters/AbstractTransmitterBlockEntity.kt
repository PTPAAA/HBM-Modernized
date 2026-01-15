package dev.ntmr.nucleartech.content.block.entity.transmitters

import dev.ntmr.nucleartech.api.block.entities.TickingServerBlockEntity
import dev.ntmr.nucleartech.content.block.entity.SyncedBlockEntity
import dev.ntmr.nucleartech.system.io.AbstractTransmitter
import dev.ntmr.nucleartech.system.io.TransmitterNetworks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class AbstractTransmitterBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : SyncedBlockEntity(type, pos, state), TickingServerBlockEntity {
    abstract val transmitter: AbstractTransmitter<*, *, *>

    var loaded = false
        protected set
    protected var shouldRefresh = true
    protected var forceNeighborUpdates = false

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (shouldRefresh) {
            transmitter.refresh(forceNeighborUpdates)
            shouldRefresh = false
            forceNeighborUpdates = false
        }
    }

    fun refresh(forceNeighborUpdates: Boolean = false) {
        shouldRefresh = true
        this.forceNeighborUpdates = forceNeighborUpdates
    }

    fun placeInWorld() {
        addToNetwork()
        transmitter.refresh()
    }

    fun neighborChanged(side: Direction) {
        transmitter.refresh(side)
    }

    override fun setRemoved() {
        super.setRemoved()
        removeFromNetwork()
        transmitter.remove()
    }

    override fun clearRemoved() {
        super.clearRemoved()
        addToNetwork()
    }

    fun addToNetwork() {
        loaded = true
        val level = level ?: return
        if (!level.isClientSide) {
            TransmitterNetworks.addOrphan(transmitter)
        }
    }

    fun removeFromNetwork() {
        loaded = false
        val level = level ?: return
        if (!level.isClientSide) {
            TransmitterNetworks.invalidateTransmitter(transmitter)
        }
    }
}
