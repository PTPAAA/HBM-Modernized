/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.recipe.AssemblyRecipe
import dev.ntmr.nucleartech.content.recipe.BatteryRecipe
import dev.ntmr.nucleartech.content.recipe.BlastingRecipe
import dev.ntmr.nucleartech.content.recipe.CentrifugeRecipe
import dev.ntmr.nucleartech.content.recipe.ChemRecipe
import dev.ntmr.nucleartech.content.recipe.PressingRecipe
import dev.ntmr.nucleartech.content.recipe.ShreddingRecipe
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilConstructingRecipe
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilRenameRecipe
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilSmithingRecipe
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleRecipeSerializer
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechRecipeSerializers : NTechRegistry<RecipeSerializer<*>> {
    override val forgeRegistry: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID)

    val ASSEMBLY = register("assembly", AssemblyRecipe::Serializer)
    val BATTERY = register("battery", BatteryRecipe::Serializer)
    val BLASTING = register("blasting", BlastingRecipe::Serializer)
    val CHEM = register("chem", ChemRecipe::Serializer)
    val CENTRIFUGE = register("centrifuge", CentrifugeRecipe::Serializer)
    val CONSTRUCTING = register("anvil_constructing", AnvilConstructingRecipe::Serializer)
    val PRESSING = register("pressing", PressingRecipe::Serializer)
    val SHREDDING = register("shredding", ShreddingRecipe::Serializer)
    val SMITHING = register("anvil_smithing", AnvilSmithingRecipe::Serializer)
    val SMITHING_RENAMING = register("anvil_smithing_special_renaming") { SimpleRecipeSerializer(::AnvilRenameRecipe) }
}
