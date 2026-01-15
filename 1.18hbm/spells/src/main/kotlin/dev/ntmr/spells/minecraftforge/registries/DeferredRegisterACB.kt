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
import dev.ntmr.spells.minecraftforge.eventbus.api.IEventBusACB
import java.util.function.Supplier

@Linkage(MC18, "net.minecraftforge.registries.DeferredRegister")
@MirrorBlueprint
interface DeferredRegisterACB<T> {
    @Static fun <B : IForgeRegistryEntryACB<B>> create(reg: IForgeRegistryACB<B>, modid: String): DeferredRegisterACB<B>
    @Static fun <B> create(registryName: ResourceLocationACB, modid: String): DeferredRegisterACB<B>

    fun <I : T> register(name: String, sup: Supplier<out I>): RegistryObjectACB<I>
    fun getEntries(): Collection<RegistryObjectACB<T>>
    fun getRegistryName(): ResourceLocationACB?

    fun register(bus: IEventBusACB)

    fun <T> test(): T
}
