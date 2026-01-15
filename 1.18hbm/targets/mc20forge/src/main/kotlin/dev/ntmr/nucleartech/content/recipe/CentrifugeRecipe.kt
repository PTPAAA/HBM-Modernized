/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dev.ntmr.nucleartech.content.NTechBlockItems
import dev.ntmr.nucleartech.content.NTechRecipeSerializers
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.block.entity.CentrifugeBlockEntity
import dev.ntmr.nucleartech.extensions.toStupidMojangList
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack

class CentrifugeRecipe(
    val recipeID: ResourceLocation,
    val ingredient: Ingredient,
    val resultsList: NonNullList<ItemStack>,
    val inputFluid: FluidStack = FluidStack.EMPTY,
    val outputFluids: NonNullList<FluidStack> = NonNullList.create()
) : Recipe<CentrifugeBlockEntity> {
    override fun matches(container: CentrifugeBlockEntity, level: Level): Boolean {
        // TODO: check fluid tanks
        // return container.containerSize > 1 && ingredient.test(container.getItem(3))
        // Placeholder check for items, need to expand for fluids later when BE is updated
        if (!ingredient.isEmpty) {
             if (!ingredient.test(container.getItem(3))) return false
        }
        if (!inputFluid.isEmpty) {
            // BE logic will handle this
        }
        return true
    }

    override fun assemble(container: CentrifugeBlockEntity, registryAccess: RegistryAccess): ItemStack = if (resultsList.isNotEmpty()) resultsList.first().copy() else ItemStack.EMPTY
    override fun getId() = recipeID
    override fun getSerializer() = NTechRecipeSerializers.CENTRIFUGE.get()
    override fun getType() = NTechRecipeTypes.CENTRIFUGE
    override fun isSpecial() = true
    override fun canCraftInDimensions(x: Int, y: Int) = true
    override fun getToastSymbol(): ItemStack = NTechBlockItems.centrifugePlacer.get().defaultInstance
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(ingredient)
    override fun getResultItem(registryAccess: RegistryAccess): ItemStack = if (resultsList.isNotEmpty()) resultsList.first() else ItemStack.EMPTY

    class Serializer : RecipeSerializer<CentrifugeRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): CentrifugeRecipe {
            val ingredient = if (json.has("ingredient")) Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient")) else Ingredient.EMPTY
            val results = if (json.has("results")) GsonHelper.getAsJsonArray(json, "results").map(JsonElement::getAsJsonObject).map(ShapedRecipe::itemStackFromJson) else emptyList()
            
            // TODO: parse fluids
            return CentrifugeRecipe(id, ingredient, results.toStupidMojangList())
        }

        override fun fromNetwork(id: ResourceLocation, buffer: FriendlyByteBuf): CentrifugeRecipe {
            val ingredient = Ingredient.fromNetwork(buffer)
            val results = buffer.readList(FriendlyByteBuf::readItem)
            val inputFluid = buffer.readFluidStack()
            val outputFluidsSize = buffer.readByte()
            val outputFluids = NonNullList.create<FluidStack>()
            for (i in 0 until outputFluidsSize) {
                outputFluids.add(buffer.readFluidStack())
            }

            return CentrifugeRecipe(id, ingredient, results.toStupidMojangList(), inputFluid, outputFluids)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: CentrifugeRecipe) {
            recipe.ingredient.toNetwork(buffer)
            buffer.writeCollection(recipe.resultsList, FriendlyByteBuf::writeItem)
            buffer.writeFluidStack(recipe.inputFluid)
            buffer.writeByte(recipe.outputFluids.size)
            for (fluid in recipe.outputFluids) {
                buffer.writeFluidStack(fluid)
            }
        }
    }
}
