package dev.ntmr.nucleartech.content.entity.missile

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.level.Level
import net.minecraftforge.network.NetworkHooks

class MIRVEntity(type: EntityType<MIRVEntity>, level: Level) : Projectile(type, level) {

    override fun defineSynchedData() {
    }

    override fun tick() {
        super.tick()
        if (!level().isClientSide) {
             // Flight
             if (tickCount < 150) {
                  deltaMovement = deltaMovement.add(0.0, 0.08, 0.0)
             } else if (tickCount == 250) {
                  split()
             }
             setPos(x + deltaMovement.x, y + deltaMovement.y, z + deltaMovement.z)
        }
    }
    
    fun split() {
        // Split into 8 sub-warheads
        for (i in 0..7) {
             val warhead = ICBMEntity(dev.ntmr.nucleartech.content.NTechEntities.icbm.get(), level())
             warhead.setPos(x, y, z)
             warhead.deltaMovement = deltaMovement.add(random.nextGaussian() * 0.5, -0.5, random.nextGaussian() * 0.5)
             level().addFreshEntity(warhead)
        }
        discard()
    }

    override fun getPacket(): Packet<ClientGamePacketListener> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }
}
