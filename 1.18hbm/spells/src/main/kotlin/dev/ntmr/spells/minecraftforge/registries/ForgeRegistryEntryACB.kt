/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraftforge.registries

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraftforge.registries.ForgeRegistryEntry")
@MirrorBlueprint
@MirrorBlueprint.Abstract
interface ForgeRegistryEntryACB<V : IForgeRegistryEntryACB<V>> : IForgeRegistryEntryACB<V> {

}
