/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.item.context

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.core.BlockPosACB
import dev.ntmr.spells.minecraft.world.entity.player.PlayerACB
import dev.ntmr.spells.minecraft.world.item.ItemStackACB
import dev.ntmr.spells.minecraft.world.level.LevelACB

@Linkage(MC18, "net.minecraft.world.item.context.UseOnContext")
@MirrorBlueprint
//@MirrorBlueprint.Constructor(MC18, "")
sealed interface UseOnContextACB {
    fun getClickedPos(): BlockPosACB
    fun isInside(): Boolean
    fun getItemInHand(): ItemStackACB
    fun getPlayer(): PlayerACB?
    fun getLevel(): LevelACB
}
