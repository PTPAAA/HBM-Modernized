/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.resources

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.sorcerer.api.MirrorBlueprint.Static

@Linkage(MC18, "net.minecraft.resources.ResourceLocation")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "Lkotlin/String;")
@MirrorBlueprint.Constructor(MC18, "Lkotlin/String;Lkotlin/String;")
interface ResourceLocationACB : Comparable<ResourceLocationACB> {
    val path: String
    val namespace: String

    fun compareNamespaced(other: ResourceLocationACB): Int

    @Static fun tryParse(location: String): ResourceLocationACB?
    @Static fun isAllowedInResourceLocation(char: Char): Boolean
    @Static fun validPathChar(char: Char): Boolean
    @Static fun validNamespaceChar(char: Char): Boolean
    @Static fun isValidResourceLocation(location: String): Boolean
}
