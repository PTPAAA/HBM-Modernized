package dev.ntmr.nucleartech.content.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.gameevent.GameEvent
import java.util.function.Supplier

class TurretItem(private val entityType: Supplier<out EntityType<*>>, properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        if (!level.isClientSide) {
            val direction = context.clickedFace
            val pos = context.clickedPos.relative(direction)
            val entity = entityType.get().create(level)
            
            if (entity != null) {
                entity.setPos(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5)
                if (entity is dev.ntmr.nucleartech.content.entity.turret.TurretEntity) {
                    entity.setOwnerUUID(context.player?.uuid)
                }
                level.addFreshEntity(entity)
                level.gameEvent(context.player, GameEvent.ENTITY_PLACE, pos)
                context.itemInHand.shrink(1)
                return InteractionResult.CONSUME
            }
        }
        return InteractionResult.SUCCESS
    }
}
