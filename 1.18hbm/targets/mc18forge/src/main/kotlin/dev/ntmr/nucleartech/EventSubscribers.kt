package dev.ntmr.nucleartech

import dev.ntmr.nucleartech.api.item.AttackHandler
import dev.ntmr.nucleartech.api.item.DamageHandler
import dev.ntmr.nucleartech.api.item.FallHandler
import dev.ntmr.nucleartech.api.item.TickingArmor
import dev.ntmr.nucleartech.content.fluid.trait.FluidTraitHandler
import dev.ntmr.nucleartech.content.fluid.trait.FluidTraitManager
import dev.ntmr.nucleartech.content.item.IncreasedRangeItem
import dev.ntmr.nucleartech.packets.FogIsComingMessage
import dev.ntmr.nucleartech.packets.resources.SyncedResourceManagers
import dev.ntmr.nucleartech.system.capability.contamination.EntityContaminationHandler
import dev.ntmr.nucleartech.system.fallout.FalloutTransformationManager
import dev.ntmr.nucleartech.system.hazard.EntityContaminationEffects
import dev.ntmr.nucleartech.system.hazard.HazardSystem
import dev.ntmr.nucleartech.system.hazard.HazmatValues
import dev.ntmr.nucleartech.system.world.BlockEntityTracker
import dev.ntmr.nucleartech.system.world.ChunkRadiation
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraftforge.event.*
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.EntityLeaveWorldEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.world.ChunkEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.roundToInt

@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent(priority = EventPriority.HIGH)
    @JvmStatic
    fun hijackChunkBlockEntities(event: ChunkEvent.Load) {
        val level = event.world
        if (level !is ServerLevel) return

        event.chunk.blockEntities = BlockEntityTracker.DelegatingBlockEntityMap(level, event.chunk.blockEntities)
    }

    @SubscribeEvent @JvmStatic
    fun addServerResources(event: AddReloadListenerEvent) {
        val context = event.conditionContext
        event.addListener(FalloutTransformationManager)
        event.addListener(FluidTraitManager).also { FluidTraitManager.context = context }
    }

    @SubscribeEvent @JvmStatic
    fun syncServerResources(event: OnDatapackSyncEvent) {
        SyncedResourceManagers.syncAll(event.player)
    }

    @SubscribeEvent @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is LivingEntity)
            event.addCapability(ntm("contamination"), EntityContaminationHandler())
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    @JvmStatic
    fun trackItemEntityHazardSystem(event: EntityJoinWorldEvent) {
        val entity = event.entity
        if (entity is ItemEntity) HazardSystem.trackItemEntity(entity)
    }

    @SubscribeEvent @JvmStatic
    fun stopTrackingItemEntityHazardSystem(event: EntityLeaveWorldEvent) {
        val entity = event.entity
        if (entity is ItemEntity) HazardSystem.stopTrackingItemEntity(entity)
    }

    @SubscribeEvent @JvmStatic
    fun tickWorld(event: TickEvent.WorldTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            val level = event.world
            HazardSystem.tickWorldHazards(level)
            FluidTraitHandler.BlockEntityFluidTraitListener.clearUnelegantlyReleasedFluids()

            if (level is ServerLevel)
                FogIsComingMessage.theFogWillConsumeAll(level)
        }
    }

    @SubscribeEvent @JvmStatic
    fun tickHazardSystemClient(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) Minecraft.getInstance().level?.let { HazardSystem.tickWorldHazards(it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingUpdate(event: LivingEvent.LivingUpdateEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is TickingArmor) item.handleTick(event, it) }
        EntityContaminationEffects.update(event.entityLiving)
        HazardSystem.applyHazardsInInventory(event.entityLiving)
    }

    @SubscribeEvent @JvmStatic
    fun onLivingSpawn(event: LivingSpawnEvent.CheckSpawn) {
        if (EntityContaminationEffects.isRadiationImmune(event.entityLiving) || event.spawnReason != MobSpawnType.NATURAL) return
        val world = event.world
        val pos = BlockPos(event.x.roundToInt(), event.y.roundToInt(), event.z.roundToInt())
        if (ChunkRadiation.getRadiation(world, pos) > 2F) event.result = Event.Result.DENY
    }

    @SubscribeEvent @JvmStatic
    fun onLivingAttack(event: LivingAttackEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is AttackHandler) item.handleAttack(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingHurt(event: LivingHurtEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is DamageHandler) item.handleDamage(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingFall(event: LivingFallEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is FallHandler) item.handleFall(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onCreativeFall(event: PlayerFlyableFallEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is FallHandler) item.handleFall(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun modifyItemAttributes(event: ItemAttributeModifierEvent) {
        HazmatValues.addItemStackAttributes(event)
        IncreasedRangeItem.addItemStackAttributes(event)
    }

    @SubscribeEvent @JvmStatic
    fun onPlayerLeftClick(event: PlayerInteractEvent.LeftClickBlock) {
        event.isCanceled = event.isCanceled || !IncreasedRangeItem.checkCanBreakWithItem(event.itemStack, event.hand)
    }
}
