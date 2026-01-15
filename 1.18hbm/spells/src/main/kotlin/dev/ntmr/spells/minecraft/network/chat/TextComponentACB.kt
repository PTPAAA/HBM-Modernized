/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraft.network.chat

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.network.chat.TextComponent")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "Lkotlin/String;")
interface TextComponentACB : BaseComponentACB {
    @MirrorBlueprint.Static
    val EMPTY: ComponentACB

    fun getText(): String
}
