/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraftforge.fml.javafmlmod

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraftforge.eventbus.api.IEventBusACB

@Linkage(MC18, "net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext")
@MirrorBlueprint
interface FMLJavaModLoadingContextACB {
    fun getModEventBus(): IEventBusACB

    @MirrorBlueprint.Static
    fun get(): FMLJavaModLoadingContextACB
}
