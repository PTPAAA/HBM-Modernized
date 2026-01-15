/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class FuelItem(val burnTime: Int, properties: Properties) : Item(properties) {
    override fun getBurnTime(itemStack: ItemStack?, recipeType: RecipeType<*>?) = burnTime
}
