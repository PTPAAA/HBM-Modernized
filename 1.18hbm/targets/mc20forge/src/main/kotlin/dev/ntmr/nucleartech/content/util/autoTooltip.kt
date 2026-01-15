/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.util

import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.extensions.gray
import dev.ntmr.nucleartech.extensions.red
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.ItemStack

/**
 * Looks for tooltip texts and splits around each `'\n'`, appending them to the list of [tooltip]s. Returns whether texts exist.
 */
fun autoTooltip(stack: ItemStack, tooltip: MutableList<Component>, ignoreMissing: Boolean = false): Boolean {
    val baseKey = "${stack.descriptionId}.desc"
    val exists = I18n.exists(baseKey)

    val polaroidKey = "${baseKey}11"
    val usePolaroidTooltip = NuclearTech.polaroidBroken && I18n.exists(polaroidKey)

    when {
        usePolaroidTooltip -> {
            for (polaroidText in I18n.get(polaroidKey).split('\n'))
                tooltip.add(Component.literal(polaroidText).gray())
        }
        exists -> {
            for (text in I18n.get(baseKey).split('\n'))
                tooltip.add(Component.literal(text).gray())
        }
        !ignoreMissing -> tooltip.add(Component.literal("Missing Tooltip").red())
    }

    return exists
}
