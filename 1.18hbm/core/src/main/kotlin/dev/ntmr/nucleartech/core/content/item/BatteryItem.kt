/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content.item

import dev.ntmr.nucleartech.core.LangKeys
import dev.ntmr.nucleartech.core.energy.EnergyUnit
import dev.ntmr.nucleartech.core.extensions.gray
import dev.ntmr.nucleartech.core.math.formatPreferred
import dev.ntmr.nucleartech.core.math.formatStorageFilling
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.core.NonNullList
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.Component
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.CreativeModeTab
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.ItemStack
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.TooltipFlag
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.level.Level

open class BatteryItem(
    val capacity: Long,
    val chargeRate: Int,
    val dischargeRate: Int,
    properties: Properties
) : Item(properties.setNoRepair().stacksTo(1).durability(1000)) {
    val energyPerDamage = capacity / 1000

    override fun getDamage(stack: ItemStack) =
        if (!stack.hasTag()) getMaxDamage(stack) else stack.getTag()!!.getInt("Damage")

    override fun canBeDepleted() = false

    override fun isBarVisible(stack: ItemStack) = false

    override fun getBarColor(stack: ItemStack): Int {
        return super.getBarColor(stack)
    }

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        if (allowedIn(tab)) {
            if (chargeRate > 0) items += ItemStack(this).apply { setDamageValue(1000) }
            items += ItemStack(this).apply { getOrCreateTag().putLong("Energy", capacity) }
        }
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, texts: MutableList<Component>, flag: TooltipFlag) {
        val stored = stack.getOrCreateTag().getLong("Energy")
        texts += LangKeys.ENERGY_ENERGY_STORED.format(EnergyUnit.UnitType.HBM.formatStorageFilling(stored, capacity)).gray()
        texts += LangKeys.ENERGY_CHARGE_RATE.format(EnergyUnit.UnitType.HBM.formatPreferred(chargeRate)).gray()
        texts += LangKeys.ENERGY_DISCHARGE_RATE.format(EnergyUnit.UnitType.HBM.formatPreferred(dischargeRate)).gray()
    }
}
