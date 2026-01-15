package dev.ntmr.nucleartech.integration.jei.categories

import com.mojang.blaze3d.vertex.PoseStack
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.NTechBlockItems
import dev.ntmr.nucleartech.content.item.AssemblyTemplateItem
import dev.ntmr.nucleartech.content.recipe.AssemblyRecipe
import dev.ntmr.nucleartech.integration.jei.NuclearRecipeTypes
import dev.ntmr.nucleartech.ntm
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class AssemblingJRC(guiHelper: IGuiHelper) : IRecipeCategory<AssemblyRecipe> {
    private val texture = ntm("textures/gui/jei_assembler.png")
    private val background = guiHelper.createDrawable(texture, 0, 0, 153, 54)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechBlockItems.assemblerPlacer.get()))
    private val arrow = guiHelper.drawableBuilder(texture, 16, 54, 32, 15).buildAnimated(40, IDrawableAnimated.StartDirection.LEFT, false)
    private val energyBar = guiHelper.drawableBuilder(texture, 0, 54, 16, 52).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true)

    @Deprecated("Deprecated in Java")
    override fun getUid() = ntm("assembling")
    @Deprecated("Deprecated in Java")
    override fun getRecipeClass() = AssemblyRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.ASSEMBLING
    override fun getTitle() = LangKeys.JEI_CATEGORY_ASSEMBLING.get()
    override fun getBackground(): IDrawable = background
    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: AssemblyRecipe, focuses: IFocusGroup) {
        for (y in 0 until 3) for (x in 0 until 4)
            builder.addSlot(RecipeIngredientRole.INPUT, 28 + x * 18, 1 + y * 18).addIngredients(recipe.ingredients.getOrElse(x + y * 4) { Ingredient.EMPTY })
        builder.addSlot(RecipeIngredientRole.CATALYST, 109, 1).addItemStack(AssemblyTemplateItem.createWithID(recipe.id))
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 19).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: AssemblyRecipe, recipeSlotsView: IRecipeSlotsView, stack: PoseStack, mouseX: Double, mouseY: Double) {
        arrow.draw(stack, 101, 20)
        energyBar.draw(stack, 1, 1)
    }
}
