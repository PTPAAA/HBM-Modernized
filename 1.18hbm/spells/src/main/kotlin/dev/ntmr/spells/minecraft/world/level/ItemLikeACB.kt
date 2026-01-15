/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.level

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.world.item.ItemACB

@Linkage(MC18, "net.minecraft.world.level.ItemLike")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface ItemLikeACB {
    fun asItem(): ItemACB
}
