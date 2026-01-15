package dev.ntmr.nucleartech.content.entity.missile

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.level.Level
import net.minecraftforge.network.NetworkHooks

class ICBMEntity(type: EntityType<ICBMEntity>, level: Level) : Projectile(type, level) {

    override fun defineSynchedData() {
        // No extra data needed for now
    }

    override fun tick() {
        super.tick()
        if (level().isClientSide) {
           // Particles
        } else {
            // Simple flight logic for now, goes up then down
            if (tickCount < 100) {
                deltaMovement = deltaMovement.add(0.0, 0.1, 0.0) // Takeoff
            } else if (tickCount > 300) {
                deltaMovement = deltaMovement.add(0.0, -0.2, 0.0) // Reentry
            }
            
            setPos(x + deltaMovement.x, y + deltaMovement.y, z + deltaMovement.z)
            
            if (onGround()) {
                 explode()
            }
        }
    }
    
    fun explode() {
        if (!level().isClientSide) {
             // Boom
             level().explode(this, x, y, z, 50.0F, Level.ExplosionInteraction.TNT)
             discard()
        }
    }

    override fun getPacket(): Packet<ClientGamePacketListener> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }
}
