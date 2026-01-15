/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.entity.*
import dev.ntmr.nucleartech.content.entity.missile.*
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechEntities : NTechRegistry<EntityType<*>> {
    override val forgeRegistry: DeferredRegister<EntityType<*>> = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID)

    val clusterFragment = register("cluster_fragment") { EntityType.Builder.of(::ClusterFragment, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(10).build("cluster_fragment") }
    val falloutRain = register("fallout_rain") { EntityType.Builder.of(::FalloutRain, MobCategory.MISC).fireImmune().build("fallout_rain") }
    val meteor = register("meteor") { EntityType.Builder.of(::Meteor, MobCategory.MISC).sized(4F, 4F).fireImmune().clientTrackingRange(100).build("meteor") }
    val missileBunkerBuster = register("bunker_buster_missile") { EntityType.Builder.of(::BunkerBusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("bunker_buster_missile") }
    val missileBunkerBusterStrong = register("strong_bunker_buster_missile") { EntityType.Builder.of(::StrongBunkerBusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_bunker_buster_missile") }
    val missileBurst = register("burst_missile") { EntityType.Builder.of(::BurstMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("burst_missile") }
    val missileCluster = register("cluster_missile") { EntityType.Builder.of(::ClusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("cluster_missile") }
    val missileClusterStrong = register("strong_cluster_missile") { EntityType.Builder.of(::StrongClusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_cluster_missile") }
    val missileDrill = register("drill_missile") { EntityType.Builder.of(::DrillMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("drill_missile") }
    val missileHE = register("he_missile") { EntityType.Builder.of(::HEMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("he_missile") }
    val missileHEStrong = register("strong_missile") { EntityType.Builder.of(::StrongHEMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_missile") }
    val missileIncendiary = register("incendiary_missile") { EntityType.Builder.of(::IncendiaryMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("incendiary_missile") }
    val missileIncendiaryStrong = register("strong_incendiary_missile") { EntityType.Builder.of(::StrongIncendiaryMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_incendiary_missile") }
    val missileInferno = register("inferno_missile") { EntityType.Builder.of(::InfernoMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("inferno_missile") }
    val missileNuclear = register("nuclear_missile") { EntityType.Builder.of(::NuclearMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("nuclear_missile") }
    val missileRain = register("rain_missile") { EntityType.Builder.of(::RainMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("rain_missile") }
    val missileTectonic = register("tectonic_missile") { EntityType.Builder.of(::TectonicMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("tectonic_missile") }
    val mushroomCloud = register("mushroom_cloud") { EntityType.Builder.of(::MushroomCloud, MobCategory.MISC).fireImmune().sized(20F, 40F).clientTrackingRange(64).build("mushroom_cloud") }
    val nuclearCreeper = register("nuclear_creeper") { EntityType.Builder.of(::NuclearCreeper, MobCategory.MONSTER).sized(.6F, 1.7F).clientTrackingRange(8).build("nuclear_creeper") }
    val nuclearExplosion = register("nuclear_explosion") { EntityType.Builder.of(::NukeExplosion, MobCategory.MISC).fireImmune().build("nuclear_explosion") }
    val oilSpill = register("oil_spill") { EntityType.Builder.of(::OilSpill, MobCategory.MISC).build("oil_spill") }
    val rbmkDebris = register("rbmk_debris") { EntityType.Builder.of(::RBMKDebris, MobCategory.MISC).sized(1F, 1F).updateInterval(20).build("rbmk_debris") }
    val shrapnel = register("shrapnel") { EntityType.Builder.of(::Shrapnel, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(10).build("shrapnel") }
    val volcanicShrapnel = register("volcanic_shrapnel") { EntityType.Builder.of(::VolcanicShrapnel, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(16).build("volcanic_shrapnel") }
    val wasteItemEntity = register("waste_item") { EntityType.Builder.of(::WasteItemEntity, MobCategory.MISC).sized(.25F, .25F).clientTrackingRange(6).updateInterval(20).build("waste_item") }

    val bullet = register("bullet") { EntityType.Builder.of(::EntityBullet, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20).build("bullet") }
    
    val gunTurret = register("gun_turret") { EntityType.Builder.of(::GunTurretEntity, MobCategory.MISC).sized(0.8F, 0.8F).build("gun_turret") }
    val missileTurret = register("missile_turret") { EntityType.Builder.of(::MissileTurretEntity, MobCategory.MISC).sized(1.2F, 1.2F).build("missile_turret") }
}
