package dev.ntmr.nucleartech.integration.jei

import dev.ntmr.nucleartech.content.recipe.AssemblyRecipe
import dev.ntmr.nucleartech.content.recipe.BlastingRecipe
import dev.ntmr.nucleartech.content.recipe.ChemRecipe
import dev.ntmr.nucleartech.content.recipe.PressingRecipe
import dev.ntmr.nucleartech.content.recipe.ShreddingRecipe
import dev.ntmr.nucleartech.integration.jei.categories.TemplateFolderJRC
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilConstructingRecipe
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilSmithingRecipe
import mezz.jei.api.recipe.RecipeType

object NuclearRecipeTypes {
    val ASSEMBLING = RecipeType(ntm("assembling"), AssemblyRecipe::class.java)
    val BLASTING = RecipeType(ntm("blasting"), BlastingRecipe::class.java)
    val CHEMISTRY = RecipeType(ntm("chemistry"), ChemRecipe::class.java)
    val CONSTRUCTING = RecipeType(ntm("constructing"), AnvilConstructingRecipe::class.java)
    val PRESSING = RecipeType(ntm("pressing"), PressingRecipe::class.java)
    val SHREDDING = RecipeType(ntm("shredding"), ShreddingRecipe::class.java)
    val SMITHING = RecipeType(ntm("smithing"), AnvilSmithingRecipe::class.java)
    val FOLDER_RESULTS = RecipeType(ntm("template_folder_results"), TemplateFolderJRC.TemplateFolderRecipe::class.java)
}
