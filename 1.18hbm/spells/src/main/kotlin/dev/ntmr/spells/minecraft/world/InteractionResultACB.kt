/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraft.world

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.world.InteractionResult")
@MirrorBlueprint
@MirrorBlueprint.Enum
sealed interface InteractionResultACB {
    val SUCCESS: InteractionResultACB
    val CONSUME: InteractionResultACB
    val CONSUME_PARTIAL: InteractionResultACB
    val PASS: InteractionResultACB
    val FAIL: InteractionResultACB

    fun consumesAction(): Boolean
    fun shouldSwing(): Boolean
    fun shouldAwardStats(): Boolean

    @MirrorBlueprint.Static
    fun sidedSuccess(client: Boolean): InteractionResultACB
}
