/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraftforge.registries

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.resources.ResourceLocationACB

@Linkage(MC18, "net.minecraftforge.registries.IForgeRegistry")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface IForgeRegistryACB<V : IForgeRegistryEntryACB<V>> : Iterable<V> {
    fun getRegistrySuperType(): Class<V>
    fun register(value: V)
    fun registerAll(vararg values: V)
    fun containsKey(key: ResourceLocationACB): Boolean
    fun containsValue(value: V): Boolean
    fun getValue(key: ResourceLocationACB): V?
    fun getKey(value: V): ResourceLocationACB?
    fun getKeys(): Set<ResourceLocationACB>
    fun getValuesCollection(): Collection<V>
    fun getEntries(): Set<Map.Entry<ResourceLocationACB, V>>
    fun <T> getSlaveMap(slaveMapName: ResourceLocationACB, type: Class<T>): T
}
