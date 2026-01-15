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
import dev.ntmr.spells.minecraft.world.InteractionResultACB
import dev.ntmr.spells.minecraft.world.item.context.UseOnContextACB
import dev.ntmr.spells.minecraft.world.level.ItemLikeACB
import dev.ntmr.spells.minecraft.world.level.LevelACB
import dev.ntmr.spells.minecraftforge.registries.ForgeRegistryEntryACB

@Linkage(MC18, "net.minecraft.world.item.Item")
@MirrorBlueprint
@MirrorBlueprint.Constructor(MC18, "L%%GENERATED%%net/minecraft/world/item/Item\$Properties;")
interface ItemACB : ForgeRegistryEntryACB<ItemACB>, ItemLikeACB {
    fun useOn(context: UseOnContextACB): InteractionResultACB

    fun canBeDepleted(): Boolean

    fun isBarVisible(stack: ItemStackACB): Boolean

    fun getBarWidth(stack: ItemStackACB): Int

    fun getBarColor(stack: ItemStackACB): Int

    @MirrorBlueprint.Protected
    fun getOrCreateDescriptionId(): String

    fun getDescriptionId(): String

    fun getDescriptionId(stack: ItemStackACB): String

    fun shouldOverrideMultiplayerNbt(): Boolean

    fun getContainerItem(stack: ItemStackACB): ItemStackACB

    fun hasContainerItem(stack: ItemStackACB): Boolean

    fun isComplex(): Boolean

    fun getUseDuration(stack: ItemStackACB): Int

    fun appendHoverText(stack: ItemStackACB, level: LevelACB?, texts: MutableList<ComponentACB>, flag: TooltipFlagACB)

    fun isFoil(stack: ItemStackACB): Boolean

    fun isEnchantable(stack: ItemStackACB): Boolean

    fun getEnchantmentValue(): Int

    fun fillItemCategory(tab: CreativeModeTabACB, items: NonNullListACB<ItemStackACB>)

    @MirrorBlueprint.Protected
    fun allowedIn(tab: CreativeModeTabACB): Boolean

    @MirrorBlueprint.Final
    fun getItemCategory(): CreativeModeTabACB?

    fun isValidRepairItem(stack: ItemStackACB, repairItem: ItemStackACB): Boolean

    fun isRepairable(stack: ItemStackACB): Boolean

    fun useOnRelease(stack: ItemStackACB): Boolean

    fun getDefaultInstance(): ItemStackACB

    fun isEdible(): Boolean

    fun isFireResistant(): Boolean

    fun canFitInsideContainerItems(): Boolean

    fun isBookEnchantable(stack: ItemStackACB): Boolean

    fun getDamage(stack: ItemStackACB): Int

    fun getMaxDamage(stack: ItemStackACB): Int

    fun isDamaged(stack: ItemStackACB): Boolean

    fun setDamage(stack: ItemStackACB, damage: Int)

    fun getItemStackLimit(stack: ItemStackACB): Int

    fun getItemEnchantability(stack: ItemStackACB): Int

    fun shouldCauseReequipAnimation(oldStack: ItemStackACB, newStack: ItemStackACB, slotChanged: Boolean): Boolean

    fun shouldCauseBlockBreakReset(oldStack: ItemStackACB, newStack: ItemStackACB): Boolean

    fun canContinueUsing(oldStack: ItemStackACB, newStack: ItemStackACB): Boolean

    fun getCreatorModId(stack: ItemStackACB): String?

    fun isDamageable(stack: ItemStackACB): Boolean

    fun getDefaultTooltipHideFlags(stack: ItemStackACB): Int

    @Linkage(MC18, "net.minecraft.world.item.Item\$Properties")
    @MirrorBlueprint
    interface PropertiesACB {
        // TODO food
        fun stacksTo(amount: Int): PropertiesACB
        fun defaultDurability(durability: Int): PropertiesACB
        fun durability(durability: Int): PropertiesACB
        fun craftRemainder(item: ItemACB): PropertiesACB
        fun tab(tab: CreativeModeTabACB): PropertiesACB
        fun rarity(rarity: RarityACB): PropertiesACB
        fun fireResistant(): PropertiesACB
        fun setNoRepair(): PropertiesACB
    }
}
