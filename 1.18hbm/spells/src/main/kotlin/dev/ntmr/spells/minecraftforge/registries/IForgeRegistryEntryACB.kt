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

@Linkage(MC18, "net.minecraftforge.registries.IForgeRegistryEntry")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface IForgeRegistryEntryACB<V> {
    fun setRegistryName(name: ResourceLocationACB): V
    fun getRegistryName(): ResourceLocationACB?
    fun getRegistryType(): Class<V>
}
