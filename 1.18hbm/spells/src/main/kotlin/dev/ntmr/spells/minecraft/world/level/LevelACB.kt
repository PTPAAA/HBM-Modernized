/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.level

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.core.BlockPosACB

@Linkage(MC18, "net.minecraft.world.level.Level")
@MirrorBlueprint
@MirrorBlueprint.Abstract
interface LevelACB {
    val isClientSide: Boolean

    fun getBlockState(pos: BlockPosACB)
}
