package dev.ntmr.nucleartech.system.world

import dev.ntmr.nucleartech.content.fluid.trait.FluidTraitHandler
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity

object BlockEntityTracker {
    private val listeners = mutableListOf<BlockEntityListener>()

    init {
        addListener(FluidTraitHandler.BlockEntityFluidTraitListener)
    }

    fun addListener(listener: BlockEntityListener) {
        listeners.add(listener)
    }

    private fun onBlockEntityAdded(level: Level, pos: BlockPos, blockEntity: BlockEntity) {
        for (listener in listeners)
            listener.onBlockEntityAdded(level, pos, blockEntity)
    }

    private fun onBlockEntityRemoved(level: Level, pos: BlockPos, blockEntity: BlockEntity) {
        for (listener in listeners)
            listener.onBlockEntityRemoved(level, pos, blockEntity)
    }

    /**
     * Interface for listener objects to implement. They can then be added with [BlockEntityTracker.addListener].
     */
    interface BlockEntityListener {
        /**
         * Called when a [BlockEntity] is added to a [net.minecraft.world.level.chunk.LevelChunk].
         *
         * This can happen either through [Level.setBlockEntity] (a [BlockEntity] being added normally after [Level.setBlock]),
         * or after loading a chunk and all saved block entities being added at once.
         *
         * It may not be possible to distinguish between these two cases.
         */
        fun onBlockEntityAdded(level: Level, pos: BlockPos, blockEntity: BlockEntity) {}

        /**
         * Called when a [BlockEntity] is removed from a [net.minecraft.world.level.chunk.LevelChunk].
         *
         * This can happen either through a [BlockEntity] being removed normally after, for example, [Level.setBlock],
         * or when a chunk is unloaded with all block entities being invalidated and removed at once.
         *
         * These two cases can be distinguished by checking [BlockEntity.isRemoved], as that function will return
         * `true` on chunk unload, but `false` for normal removal, which is due to the implementation of
         * [net.minecraft.world.level.chunk.LevelChunk.removeBlockEntity].
         */
        fun onBlockEntityRemoved(level: Level, pos: BlockPos, blockEntity: BlockEntity) {}
    }

    class DelegatingBlockEntityMap(private val level: Level, private val delegate: MutableMap<BlockPos, BlockEntity>) : MutableMap<BlockPos, BlockEntity> by delegate {
        override fun clear() {
            for ((pos, blockEntity) in this) {
                onBlockEntityRemoved(level, pos, blockEntity)
            }
            return delegate.clear()
        }

        override fun remove(key: BlockPos, value: BlockEntity): Boolean {
            val result = super.remove(key, value)
            if (result)
                onBlockEntityRemoved(level, key, value)
            return result
        }

        override fun remove(key: BlockPos): BlockEntity? {
            val result = delegate.remove(key)
            if (result != null)
                onBlockEntityRemoved(level, key, result)
            return result
        }

        override fun putAll(from: Map<out BlockPos, BlockEntity>) {
            for ((pos, blockEntity) in filter { contains(it.key) })
                onBlockEntityRemoved(level, pos, blockEntity)

            for ((pos, blockEntity) in from)
                onBlockEntityAdded(level, pos, blockEntity)
            return delegate.putAll(from)
        }

        override fun put(key: BlockPos, value: BlockEntity): BlockEntity? {
            val result = delegate.put(key, value)
            onBlockEntityAdded(level, key, value)
            return result
        }
    }
}
