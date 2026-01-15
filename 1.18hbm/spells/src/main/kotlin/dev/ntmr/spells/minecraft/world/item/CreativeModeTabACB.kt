/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.item

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.core.NonNullListACB
import dev.ntmr.spells.minecraft.network.chat.ComponentACB
import dev.ntmr.spells.minecraft.resources.ResourceLocationACB

@Linkage(MC18, "net.minecraft.world.item.CreativeModeTab")
@MirrorBlueprint
@MirrorBlueprint.Abstract
@MirrorBlueprint.Constructor(MC18, "Lkotlin/String;")
interface CreativeModeTabACB {
    fun getId(): Int
    fun getRecipeFolderName(): String
    fun getDisplayName(): ComponentACB
    fun getIconItem(): ItemStackACB

    @MirrorBlueprint.Abstract
    fun makeIcon(): ItemStackACB

    fun setBackgroundImage(texture: ResourceLocationACB): CreativeModeTabACB
    fun setRecipeFolderName(name: String): CreativeModeTabACB
    fun showTitle(): Boolean
    fun hideTitle(): CreativeModeTabACB
    fun canScroll(): Boolean
    fun hideScroll(): CreativeModeTabACB
    fun getColumn(): Int
    fun isTopRow(): Boolean
    fun isAlignedRight(): Boolean
    fun fillItemList(items: NonNullListACB<ItemStackACB>)
    fun getTabPage(): Int
    fun hasSearchBar(): Boolean
    fun getSearchbarWidth(): Int
    fun getBackgroundImage(): ResourceLocationACB
    fun getTabsImage(): ResourceLocationACB
    fun getLabelColor(): Int
    fun getSlotColor(): Int
}
