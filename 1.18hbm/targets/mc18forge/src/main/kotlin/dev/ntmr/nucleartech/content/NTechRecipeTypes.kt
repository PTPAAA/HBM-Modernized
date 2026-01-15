/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.recipe.AssemblyRecipe
import dev.ntmr.nucleartech.content.recipe.BlastingRecipe
import dev.ntmr.nucleartech.content.recipe.CentrifugeRecipe
import dev.ntmr.nucleartech.content.recipe.ChemRecipe
import dev.ntmr.nucleartech.content.recipe.PressingRecipe
import dev.ntmr.nucleartech.content.recipe.ShreddingRecipe
import dev.ntmr.nucleartech.content.recipe.StackedIngredient
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilConstructingRecipe
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilSmithingRecipe
import dev.ntmr.nucleartech.ntm
import net.minecraft.core.Registry
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.event.RegistryEvent

object NTechRecipeTypes {
    private val recipeTypes = mutableSetOf<RecipeType<*>>()

    // NOTE: all recipes need to have the isSpecial() = true override, so the recipe book does not issue a warning
    val ASSEMBLY = create<AssemblyRecipe>("assembly")
    val BLASTING = create<BlastingRecipe>("blasting")
    val CHEM = create<ChemRecipe>("chem")
    val CENTRIFUGE = create<CentrifugeRecipe>("centrifuge")
    val CONSTRUCTING = create<AnvilConstructingRecipe>("anvil_constructing")
    val PRESSING = create<PressingRecipe>("pressing")
    val SHREDDING = create<ShreddingRecipe>("shredding")
    val SMITHING = create<AnvilSmithingRecipe>("anvil_smithing")

    private fun <T : Recipe<*>> create(name: String): RecipeType<T> = object : RecipeType<T> {
        private val registryName = ntm(name)
        override fun toString() = registryName.toString()
    }.also { recipeTypes += it }

    fun getTypes() = recipeTypes.toSet()

    init {
        NuclearTech.submitGenericListener { event: RegistryEvent.Register<RecipeSerializer<*>> ->
            getTypes().forEach { Registry.register(Registry.RECIPE_TYPE, it.toString(), it) }
            CraftingHelper.register(StackedIngredient.NAME, StackedIngredient.Serializer)
        }
    }
}
