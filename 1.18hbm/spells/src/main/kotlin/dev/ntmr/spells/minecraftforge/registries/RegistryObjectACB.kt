/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraftforge.registries

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.sorcerer.api.MirrorBlueprint.Static
import dev.ntmr.spells.minecraft.resources.ResourceLocationACB
import java.util.function.Supplier

@Linkage(MC18, "net.minecraftforge.registries.RegistryObject")
@MirrorBlueprint
interface RegistryObjectACB<T> : Supplier<T> {
    @Static fun <T : IForgeRegistryEntryACB<T>, U : T> create(name: ResourceLocationACB, registry: IForgeRegistryACB<T>): RegistryObjectACB<U>
    @Static fun <T, U : T> create(name: ResourceLocationACB, registryName: ResourceLocationACB, modid: String): RegistryObjectACB<U>
    @Static fun <T, U : T> createOptional(name: ResourceLocationACB, registryName: ResourceLocationACB, modid: String): RegistryObjectACB<U>

    fun getId(): ResourceLocationACB
    fun isPresent(): Boolean
}
