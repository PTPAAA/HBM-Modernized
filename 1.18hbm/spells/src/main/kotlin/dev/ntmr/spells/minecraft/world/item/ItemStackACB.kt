/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.world.item

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import dev.ntmr.spells.minecraft.nbt.CompoundTagACB
import dev.ntmr.spells.minecraft.network.chat.ComponentACB

@Linkage(MC18, "net.minecraft.world.item.ItemStack")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/level/ItemLike;")
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/level/ItemLike;I")
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/level/ItemLike;IL%%GENERATED%%net/minecraft/nbt/CompoundTag?;")
interface ItemStackACB {
    fun getMaxStackSize(): Int
    fun isStackable(): Boolean
    fun isDamageableItem(): Boolean
    fun isDamaged(): Boolean
    fun getDamageValue(): Int
    fun setDamageValue(damage: Int)
    fun getMaxDamage(): Int

    fun getDescriptionId(): String

    fun useOnRelease(): Boolean

    fun hasTag(): Boolean
    fun getTag(): CompoundTagACB?
    fun getOrCreateTag(): CompoundTagACB
    fun getOrCreateTagElement(key: String): CompoundTagACB
    fun getTagElement(key: String): CompoundTagACB?
    fun removeTagKey(key: String)
    fun setTag(tagACB: CompoundTagACB?)

    fun getHoverName(): ComponentACB
    fun setHoverName(name: ComponentACB?): ItemStackACB
    fun resetHoverName()
    fun hasCustomHoverName(): Boolean
}
