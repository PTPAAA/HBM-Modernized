package dev.ntmr.nucleartech.content.entity.missile

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.level.Level
import net.minecraftforge.network.NetworkHooks

class DoomsdayMissileEntity(type: EntityType<DoomsdayMissileEntity>, level: Level) : Projectile(type, level) {

    override fun defineSynchedData() {
    }

    override fun tick() {
        super.tick()
        if (!level().isClientSide) {
            // Takeoff
            if (tickCount < 200) {
                deltaMovement = deltaMovement.add(0.0, 0.05, 0.0)
            } else if (tickCount > 400) {
                 // Reentry
                 deltaMovement = deltaMovement.add(0.0, -0.3, 0.0)
            }
            
            setPos(x + deltaMovement.x, y + deltaMovement.y, z + deltaMovement.z)
            
            if (onGround()) {
                 explode()
            }
        }
    }
    
    fun explode() {
        if (!level().isClientSide) {
             // Massive Boom
             level().explode(this, x, y, z, 100.0F, Level.ExplosionInteraction.TNT) // 100 strength!
             discard()
        }
    }

    override fun getPacket(): Packet<ClientGamePacketListener> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }
}
