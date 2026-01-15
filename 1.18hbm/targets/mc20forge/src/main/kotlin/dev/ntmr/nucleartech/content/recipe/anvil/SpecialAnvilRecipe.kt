/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.recipe.anvil

import dev.ntmr.nucleartech.content.recipe.StackedIngredient
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

abstract class SpecialAnvilRecipe(recipeID: ResourceLocation, tier: Int) : AnvilSmithingRecipe(recipeID, StackedIngredient.EMPTY, StackedIngredient.EMPTY, ItemStack.EMPTY, tier) {
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.create()
    override fun getResultItem(registryAccess: RegistryAccess): ItemStack = ItemStack.EMPTY
    override fun getAmountConsumed(index: Int, mirrored: Boolean) = 1
    final override fun matches(container: Container, level: Level): Boolean = matches(container)
    abstract fun matches(container: Container): Boolean
    override fun matchesInt(container: Container): Int = if (matches(container)) 0 else -1
    abstract override fun assemble(container: Container, registryAccess: RegistryAccess): ItemStack
    abstract override fun getSerializer(): RecipeSerializer<*>
}
