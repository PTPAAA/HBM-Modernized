/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraft

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.sorcerer.api.MirrorBlueprint.Static

@Linkage(MC18, "net.minecraft.ChatFormatting")
@MirrorBlueprint
@MirrorBlueprint.Enum
interface ChatFormattingACB {
    val BLACK: ChatFormattingACB
    val DARK_BLUE: ChatFormattingACB
    val DARK_GREEN: ChatFormattingACB
    val DARK_AQUA: ChatFormattingACB
    val DARK_RED: ChatFormattingACB
    val DARK_PURPLE: ChatFormattingACB
    val GOLD: ChatFormattingACB
    val GRAY: ChatFormattingACB
    val DARK_GRAY: ChatFormattingACB
    val BLUE: ChatFormattingACB
    val GREEN: ChatFormattingACB
    val AQUA: ChatFormattingACB
    val RED: ChatFormattingACB
    val LIGHT_PURPLE: ChatFormattingACB
    val YELLOW: ChatFormattingACB
    val WHITE: ChatFormattingACB
    val OBFUSCATED: ChatFormattingACB
    val BOLD: ChatFormattingACB
    val STRIKETHROUGH: ChatFormattingACB
    val UNDERLINE: ChatFormattingACB
    val ITALIC: ChatFormattingACB
    val RESET: ChatFormattingACB

    fun getChar(): Char
    fun getId(): Int
    fun isFormat(): Boolean
    fun isColor(): Boolean
    fun getColor(): Int?
    fun getName(): String

    @Static fun stripFormatting(text: String?): String?
    @Static fun getByName(name: String?): ChatFormattingACB?
    @Static fun getById(id: Int): ChatFormattingACB?
    @Static fun getByCode(code: Char): ChatFormattingACB?
}
