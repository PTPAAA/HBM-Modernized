/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.network.chat

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.network.chat.TranslatableComponent")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "Lkotlin/String;Lkotlin/Any;~")
interface TranslatableComponentACB : BaseComponentACB {
    fun getKey(): String
    fun getArgs(): Array<Any>
}
