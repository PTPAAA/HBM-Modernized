/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.network.chat

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.network.chat.BaseComponent")
@MirrorBlueprint
@MirrorBlueprint.Abstract
interface BaseComponentACB : MutableComponentACB {
//    @MirrorBlueprint.Protected
//    val siblings: List<Component>

    fun getContents(): String
    fun getSiblings(): List<ComponentACB>
    fun setStyle(style: StyleACB): MutableComponentACB
    fun getStyle(): StyleACB

    @MirrorBlueprint.Abstract
    fun plainCopy(): BaseComponentACB

    @MirrorBlueprint.Final
    fun copy(): MutableComponentACB
}
