/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.recipe.anvil

import dev.ntmr.nucleartech.content.NTechRecipeSerializers
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.network.chat.Component

import net.minecraft.core.RegistryAccess

class AnvilRenameRecipe(recipeID: ResourceLocation) : SpecialAnvilRecipe(recipeID, 1) {
    private val nameTagIngredient = Ingredient.of(Items.NAME_TAG)

    override fun matches(container: Container): Boolean {
        return if (container.containerSize < 2) false
        else nameTagIngredient.test(container.getItem(1))
    }

    override fun assemble(container: Container, registryAccess: RegistryAccess): ItemStack {
        val output = container.getItem(0).copy()
        val name = container.getItem(1).hoverName.string.replace("\\&", "§")
        output.hoverName = Component.literal("§r$name")
        return output
    }

    override fun getSerializer(): net.minecraft.world.item.crafting.RecipeSerializer<*> = TODO("Fix serializer")
}
