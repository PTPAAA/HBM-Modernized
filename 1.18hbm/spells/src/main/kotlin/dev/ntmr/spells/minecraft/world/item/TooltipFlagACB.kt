/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraft.world.item

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.world.item.TooltipFlag")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface TooltipFlagACB {
    fun isAdvanced(): Boolean

    @Linkage(MC18, "net.minecraft.world.item.TooltipFlag\$Default")
    @MirrorBlueprint
    @MirrorBlueprint.Enum
    interface DefaultACB : TooltipFlagACB {
        val NORMAL: DefaultACB
        val ADVANCED: DefaultACB
    }
}
