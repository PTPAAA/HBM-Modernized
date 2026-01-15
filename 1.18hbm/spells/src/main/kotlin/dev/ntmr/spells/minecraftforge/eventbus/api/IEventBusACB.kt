/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraftforge.eventbus.api

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint

@Linkage(MC18, "net.minecraftforge.eventbus.api.IEventBus")
@MirrorBlueprint
@MirrorBlueprint.Interface
interface IEventBusACB
