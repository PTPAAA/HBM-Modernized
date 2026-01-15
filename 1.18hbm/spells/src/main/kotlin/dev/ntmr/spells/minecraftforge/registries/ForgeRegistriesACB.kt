/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused", "PropertyName")

package dev.ntmr.spells.minecraftforge.registries

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.sorcerer.api.MirrorBlueprint.Static
import dev.ntmr.spells.minecraft.world.item.ItemACB
import dev.ntmr.spells.minecraft.world.level.block.BlockACB

@Linkage(MC18, "net.minecraftforge.registries.ForgeRegistries")
@MirrorBlueprint
interface ForgeRegistriesACB {
    @Static val BLOCKS: IForgeRegistryACB<BlockACB>
    @Static val ITEMS: IForgeRegistryACB<ItemACB>
}
