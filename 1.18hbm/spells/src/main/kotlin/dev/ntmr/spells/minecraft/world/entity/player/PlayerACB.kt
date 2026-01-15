/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.entity.player

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.network.chat.ComponentACB
import dev.ntmr.spells.minecraft.world.entity.LivingEntityACB

@Linkage(MC18, "net.minecraft.world.entity.player.Player")
@MirrorBlueprint
@MirrorBlueprint.Abstract
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/level/Level;L%%GENERATED%%net/minecraft/core/BlockPos;FL%%GENERATED%%com/mojang/authlib/GameProfile;")
interface PlayerACB : LivingEntityACB {
    fun displayClientMessage(message: ComponentACB, statusBar: Boolean)
}
