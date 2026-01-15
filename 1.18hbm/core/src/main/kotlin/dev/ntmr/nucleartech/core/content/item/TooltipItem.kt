/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content.item

import dev.ntmr.nucleartech.core.content.util.autoTooltip
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.Component
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.ItemStack
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.TooltipFlag
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.level.Level

open class TooltipItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, level: Level?, texts: MutableList<Component>, flag: TooltipFlag) {
        autoTooltip(stack, texts)
    }
}
