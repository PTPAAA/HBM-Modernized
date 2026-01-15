/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content.util

import dev.ntmr.nucleartech.core.content.item.PolaroidItem
import dev.ntmr.nucleartech.core.extensions.gray
import dev.ntmr.nucleartech.core.extensions.red
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.client.resources.language.I18n
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.Component
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.TextComponent
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.ItemStack

/**
 * Looks for tooltip texts and splits around each `'\n'`, appending them to the list of [tooltip]s. Returns whether texts exist.
 */
fun autoTooltip(stack: ItemStack, tooltip: MutableList<Component>, ignoreMissing: Boolean = false): Boolean {
    val baseKey = "${stack.getDescriptionId()}.desc"
    val exists = I18n.exists(baseKey)

    val polaroidKey = "${baseKey}11"
    val usePolaroidTooltip = PolaroidItem.isBroken && I18n.exists(polaroidKey)

    when {
        usePolaroidTooltip -> {
            for (polaroidText in I18n.get(polaroidKey).split('\n'))
                tooltip.add(TextComponent(polaroidText).gray())
        }
        exists -> {
            for (text in I18n.get(baseKey).split('\n'))
                tooltip.add(TextComponent(text).gray())
        }
        !ignoreMissing -> tooltip.add(TextComponent("Missing Tooltip").red())
    }

    return exists
}
