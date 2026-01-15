/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.mojang.authlib

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import java.util.*

@Linkage(MC18, "com.mojang.authlib.GameProfile")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "Ljava/util/UUID;Lkotlin/String;")
interface GameProfileACB {
    fun getId(): UUID
    fun getName(): String
    // TODO PropertyMap
    fun isComplete(): Boolean
    fun isLegacy(): Boolean
}
