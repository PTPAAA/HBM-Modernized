/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.spells.minecraft.network.chat

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.ChatFormattingACB

@Linkage(MC18, "net.minecraft.network.chat.MutableComponent")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface MutableComponentACB : ComponentACB {
    fun append(text: String): MutableComponentACB
    fun append(component: ComponentACB): MutableComponentACB

    fun withStyle(style: StyleACB): MutableComponentACB
    fun withStyle(formatting: ChatFormattingACB): MutableComponentACB
    fun withStyle(vararg formatting: ChatFormattingACB): MutableComponentACB
}
