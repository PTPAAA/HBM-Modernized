package dev.ntmr.nucleartech.api.block.entities

import dev.ntmr.nucleartech.api.sound.SoundHandler
import dev.ntmr.nucleartech.api.sound.isActive
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Handles playing the sound when needed based on implemented values.
 *
 * Implementations need to call this implementation of [dev.ntmr.nucleartech.api.block.entities.TickingClientBlockEntity.clientTick] when overriding the function.
 *
 * Corresponding blocks need to provide a client ticker.
 *
 * @see dev.ntmr.nucleartech.api.block.entities.createClientTickerChecked
 */
public interface SoundLoopBlockEntity : TickingClientBlockEntity {
    public val soundPos: BlockPos
    public val shouldPlaySoundLoop: Boolean
    public val soundLoopEvent: SoundEvent
    public val soundLoopSource: SoundSource get() = SoundSource.BLOCKS
    public val soundLoopVolume: Float get() = 1F
    public val soundLoopPitch: Float get() = 1F
    public val soundLoopStateMachine: SoundStateMachine

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        soundLoopStateMachine.tick()
    }

    public open class SoundStateMachine(public val blockEntity: SoundLoopBlockEntity) {
        protected var cooldown: Int = 0
        public var activeSoundInstance: SoundInstance? = null
            private set

        public open fun tick() {
            if (blockEntity.shouldPlaySoundLoop) {
                if (--cooldown > 0) return
                cooldown = 20

                if (!activeSoundInstance.isActive()) {
                    activeSoundInstance = SoundHandler.playBlockEntitySoundEvent(blockEntity.soundPos, blockEntity.soundLoopEvent, blockEntity.soundLoopSource, blockEntity.soundLoopVolume, blockEntity.soundLoopPitch)
                }
            } else if (activeSoundInstance != null) {
                SoundHandler.stopBlockEntitySound(blockEntity.soundPos)
                activeSoundInstance = null
                cooldown = 0
            }
        }
    }

    public class NoopStateMachine(blockEntity: SoundLoopBlockEntity) : SoundStateMachine(blockEntity) {
        override fun tick() { /* no-op */ }
    }
}
