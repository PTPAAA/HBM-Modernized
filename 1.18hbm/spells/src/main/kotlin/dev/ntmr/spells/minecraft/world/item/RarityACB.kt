/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraft.world.item

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.ChatFormattingACB
import dev.ntmr.spells.minecraft.network.chat.StyleACB
import java.util.function.UnaryOperator

@Linkage(MC18, "net.minecraft.world.item.Rarity")
@MirrorBlueprint
@MirrorBlueprint.Enum
interface RarityACB {
    val COMMON: RarityACB
    val UNCOMMON: RarityACB
    val RARE: RarityACB
    val EPIC: RarityACB

    val color: ChatFormattingACB
    fun getStyleModifier(): UnaryOperator<StyleACB>
}
