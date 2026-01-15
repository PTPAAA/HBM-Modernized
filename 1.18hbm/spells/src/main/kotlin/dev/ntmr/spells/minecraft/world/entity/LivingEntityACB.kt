/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.entity

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraft.world.entity.LivingEntity")
@MirrorBlueprint
@MirrorBlueprint.Abstract
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/entity/EntityType<L%%GENERATED%%net/minecraft/world/entity/LivingEntity;+>;L%%GENERATED%%net/minecraft/world/level/Level;")
interface LivingEntityACB : EntityACB {

}
