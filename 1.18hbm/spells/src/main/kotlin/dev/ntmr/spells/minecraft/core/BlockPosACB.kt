/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.core

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.core.BlockPos")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "III")
interface BlockPosACB : Vec3iACB {
}
