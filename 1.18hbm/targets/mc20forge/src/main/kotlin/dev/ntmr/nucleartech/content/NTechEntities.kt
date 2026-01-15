/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.entity.*
import dev.ntmr.nucleartech.content.entity.monster.FeralGhoulEntity
import dev.ntmr.nucleartech.content.entity.monster.MaskManEntity
import dev.ntmr.nucleartech.content.entity.monster.RaiderEntity
import dev.ntmr.nucleartech.content.entity.monster.TaintCrawlerEntity
import dev.ntmr.nucleartech.content.entity.projectile.EntityGrenadeProjectile
import dev.ntmr.nucleartech.content.entity.projectile.EntityMiniNuke
import dev.ntmr.nucleartech.content.entity.projectile.EntityRocket
import dev.ntmr.nucleartech.content.entity.missile.*
import dev.ntmr.nucleartech.content.entity.monster.AlienEntity
import dev.ntmr.nucleartech.content.entity.turret.HeavyRailgunTurretEntity
import dev.ntmr.nucleartech.content.entity.vehicle.UFOEntity
import net.minecraft.world.entity.Entity
import dev.ntmr.nucleartech.content.entity.turret.*
import dev.ntmr.nucleartech.content.entity.monster.CyberCrabEntity
import dev.ntmr.nucleartech.content.entity.effect.BlackHoleEntity
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
    
    val taintCrawler = register("taint_crawler") { EntityType.Builder.of(::TaintCrawlerEntity, MobCategory.MONSTER).sized(0.5F, 0.4F).build("taint_crawler") }
    val cyberCrab = register("cyber_crab") { EntityType.Builder.of(::CyberCrabEntity, MobCategory.MONSTER).sized(0.6F, 0.5F).build("cyber_crab") }
    val maskMan = register("mask_man") { EntityType.Builder.of(::MaskManEntity, MobCategory.MONSTER).sized(0.6F, 1.8F).build("mask_man") }
    
    val blackHole = register("black_hole") { EntityType.Builder.of(::BlackHoleEntity, MobCategory.MISC).sized(1.0F, 1.0F).fireImmune().clientTrackingRange(64).updateInterval(20).build("black_hole") }
    
    val icbm = register("icbm") { EntityType.Builder.of(::ICBMEntity, MobCategory.MISC).sized(1.0F, 5.0F).clientTrackingRange(64).updateInterval(5).build("icbm") }
    val mirv = register("mirv") { EntityType.Builder.of(::MIRVEntity, MobCategory.MISC).sized(1.0F, 5.0F).clientTrackingRange(64).updateInterval(5).build("mirv") }
    
    val ciws = register("ciws") { EntityType.Builder.of(::CIWSTurretEntity, MobCategory.MISC).sized(1.5F, 2.0F).build("ciws") }
    val laserTurret = register("laser_turret") { EntityType.Builder.of(::LaserTurretEntity, MobCategory.MISC).sized(1.0F, 1.5F).build("laser_turret") }
    val heavyRailgun = register("heavy_railgun") { EntityType.Builder.of(::HeavyRailgunTurretEntity, MobCategory.MISC).sized(2.0F, 2.0F).build("heavy_railgun") }
    
    val doomsdayMissile = register("doomsday_missile") { EntityType.Builder.of(::DoomsdayMissileEntity, MobCategory.MISC).sized(2.0F, 8.0F).clientTrackingRange(64).updateInterval(5).build("doomsday_missile") }

    val feralGhoul = register("feral_ghoul") { EntityType.Builder.of(::FeralGhoulEntity, MobCategory.MONSTER).sized(0.6F, 1.95F).build("feral_ghoul") }
    val raider = register("raider") { EntityType.Builder.of(::RaiderEntity, MobCategory.MONSTER).sized(0.6F, 1.95F).build("raider") }

    // Phase 19
    val rocket = register("rocket") { EntityType.Builder.of(::EntityRocket, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(10).build("rocket") }
    val miniNuke = register("mini_nuke") { EntityType.Builder.of(::EntityMiniNuke, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(10).build("mini_nuke") }
    val grenade = register("grenade") { EntityType.Builder.of(::EntityGrenadeProjectile, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(64).updateInterval(10).build("grenade") }

    // Phase 25
    val alien = register("alien") { EntityType.Builder.of(::AlienEntity, MobCategory.MONSTER).sized(0.6F, 1.8F).build("alien") }
    val ufo = register("ufo") { EntityType.Builder.of(::UFOEntity, MobCategory.MISC).sized(4.0F, 2.0F).clientTrackingRange(128).build("ufo") }

    // Phase 26
    // val patrolBoat = register("patrol_boat") { EntityType.Builder.of(::dev.ntmr.nucleartech.content.entity.vehicle.PatrolBoatEntity, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).build("patrol_boat") }
}
