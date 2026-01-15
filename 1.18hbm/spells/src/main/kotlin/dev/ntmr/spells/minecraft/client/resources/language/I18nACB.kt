/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.client.resources.language

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.sorcerer.api.MirrorBlueprint.Static

@Linkage(MC18, "net.minecraft.client.resources.language.I18n")
@MirrorBlueprint
interface I18nACB {
    @Static fun get(key: String, vararg args: Any): String
    @Static fun exists(key: String): Boolean
}
