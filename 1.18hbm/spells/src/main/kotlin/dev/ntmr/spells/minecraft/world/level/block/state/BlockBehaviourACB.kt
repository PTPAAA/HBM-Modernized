/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.level.block.state

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.world.level.block.BlockACB
import dev.ntmr.spells.minecraftforge.registries.ForgeRegistryEntryACB
import java.util.function.Supplier

@Linkage(MC18, "net.minecraft.world.level.block.state.BlockBehaviour")
@MirrorBlueprint
@MirrorBlueprint.Abstract
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/level/block/state/BlockBehaviour\$Properties;")
interface BlockBehaviourACB : ForgeRegistryEntryACB<BlockACB> {
    @Linkage(MC18, "net.minecraft.world.level.block.state.BlockBehaviour\$Properties")
    @MirrorBlueprint
    interface PropertiesACB {
        fun noCollision(): PropertiesACB
        fun noOcclusion(): PropertiesACB
        fun friction(value: Float): PropertiesACB
        fun speedFactor(factor: Float): PropertiesACB
        fun jumpFactor(factor: Float): PropertiesACB
        // TODO sound
        // TODO lightLevel
        fun strength(destroyTime: Float, explosionResistance: Float): PropertiesACB
        fun instabreak(): PropertiesACB
        fun strength(value: Float): PropertiesACB
        fun randomTicks(): PropertiesACB
        fun dynamicShape(): PropertiesACB
        fun noDrops(): PropertiesACB
        fun lootFrom(blockIn: Supplier<out BlockACB>): PropertiesACB
        fun air(): PropertiesACB
        // TODO isValidSpawn
        // TODO isRedstoneConductor
        // TODO isSuffocating
        // TODO isViewBlocking
        // TODO hasPostProcess
        // TODO emissiveRendering
        fun requiresCorrectToolForDrops(): PropertiesACB
        // TODO color
        fun destroyTime(value: Float): PropertiesACB
        fun explosionResistance(value: Float): PropertiesACB
    }
}
