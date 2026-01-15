/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import dev.ntmr.nucleartech.content.NTechBlockItems
import dev.ntmr.nucleartech.content.NTechRecipeSerializers
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level

class AssemblyRecipe(
    val recipeID: ResourceLocation,
    val ingredientsList: NonNullList<StackedIngredient>,
    val result: ItemStack,
    val duration: Int
) : Recipe<Container> {
    override fun matches(container: Container, level: Level) = container.containerSize >= ingredientsList.size && ingredientsList.containerSatisfiesRequirements(container)
    override fun assemble(container: Container, registryAccess: RegistryAccess): ItemStack = result.copy()
    override fun getId() = recipeID
    override fun getSerializer(): Serializer = NTechRecipeSerializers.ASSEMBLY.get()
    override fun getType() = NTechRecipeTypes.ASSEMBLY
    override fun isSpecial() = true
    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int) = true
    override fun getToastSymbol() = ItemStack(NTechBlockItems.assemblerPlacer.get())
    @Suppress("UNCHECKED_CAST")
    override fun getIngredients(): NonNullList<Ingredient> = ingredientsList as NonNullList<Ingredient>
    override fun getResultItem(registryAccess: RegistryAccess) = result
    
    // Backwards compatibility alias
    val resultItem: ItemStack get() = result

    class Serializer : RecipeSerializer<AssemblyRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): AssemblyRecipe {
            val ingredients = GsonHelper.getAsJsonArray(json, "ingredients").map(Ingredient::fromJson).map { it as? StackedIngredient ?: throw JsonParseException("Couldn't parse ingredient as StackedIngredient") }
            val ingredientsStupidMojangList = NonNullList.createWithCapacity<StackedIngredient>(ingredients.size)
            for (ingredient in ingredients) ingredientsStupidMojangList.add(ingredient)
            val result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"))
            val duration = GsonHelper.getAsInt(json, "duration")
            return AssemblyRecipe(id, ingredientsStupidMojangList, result, duration)
        }

        override fun fromNetwork(id: ResourceLocation, buffer: FriendlyByteBuf): AssemblyRecipe {
            val ingredients = buffer.readList { Ingredient.fromNetwork(it) }.map { it as? StackedIngredient ?: throw IllegalStateException("Received non-StackedIngredient over network") }
            val ingredientsStupidMojangList = NonNullList.createWithCapacity<StackedIngredient>(ingredients.size)
            for (ingredient in ingredients) ingredientsStupidMojangList.add(ingredient)
            val result = buffer.readItem()
            val duration = buffer.readInt()
            return AssemblyRecipe(id, ingredientsStupidMojangList, result, duration)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblyRecipe) {
            buffer.writeCollection(recipe.ingredientsList) { packet, ingredient -> ingredient.toNetwork(packet) }
            buffer.writeItem(recipe.result)
            buffer.writeInt(recipe.duration)
        }
    }
}
