/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.core

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.core.NonNullList")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "Lkotlin/collections/MutableList<GE;>;GE?;")
sealed interface NonNullListACB<E> : MutableList<E & Any> {
}
