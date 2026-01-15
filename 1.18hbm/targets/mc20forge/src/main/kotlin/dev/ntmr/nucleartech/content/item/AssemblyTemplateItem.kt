package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.Agnostics
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.client.rendering.CustomBEWLR
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.recipe.AssemblyRecipe
import dev.ntmr.nucleartech.extensions.blue
import dev.ntmr.nucleartech.extensions.bold
import dev.ntmr.nucleartech.extensions.gray
import dev.ntmr.nucleartech.extensions.italic
import net.minecraft.client.Minecraft
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.level.Level
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer
import kotlin.math.floor

class AssemblyTemplateItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component = Component.translatable(getDescriptionId(stack), Minecraft.getInstance().level?.let { getRecipeFromStack(stack, it.recipeManager)?.resultItem?.hoverName } ?: "N/A")



    override fun appendHoverText(stack: ItemStack, level: Level?, tooltips: MutableList<Component>, flag: TooltipFlag) {
        if (level == null) return
        val recipe = getRecipeFromStack(stack, level.recipeManager) ?: return
        val resultItem = recipe.resultItem

        with(tooltips) {
            add(LangKeys.INFO_OUTPUT.bold().gray())
            add(Component.literal("${resultItem.count}x ".prependIndent("  ")).append(resultItem.hoverName).gray())
            add(LangKeys.INFO_INPUTS.bold().gray())
            for (input in recipe.ingredientsList) add(Component.literal("${input.requiredAmount}x ".prependIndent("  ")).append(input.items.first().hoverName).gray())
            add(LangKeys.INFO_PRODUCTION_TIME.bold().gray())
            add(Component.literal("${floor(recipe.duration / 20F * 100F) / 100F} ".prependIndent("  ")).append(LangKeys.WORD_SECONDS.get()).gray())
        }

        if (flag.isAdvanced) tooltips += Component.literal(recipe.id.toString()).italic().blue()
    }

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            override fun getCustomRenderer() = CustomBEWLR
        })
    }

    companion object {
        fun getRecipeIDFromStack(stack: ItemStack) = if (stack.item is AssemblyTemplateItem) stack.tag?.getString("recipe")?.let { ResourceLocation(it) } else null
        fun getRecipeFromStack(stack: ItemStack, recipeManager: RecipeManager) = getRecipeIDFromStack(stack)?.let { recipeManager.byKey(it).orElse(null) as? AssemblyRecipe }
        fun getAllTemplates(recipeManager: RecipeManager): List<ItemStack> = recipeManager.getAllRecipesFor(NTechRecipeTypes.ASSEMBLY).map { ItemStack(
            NTechItems.assemblyTemplate.get()).apply { orCreateTag.putString("recipe", it.id.toString()) } }
        fun isValidTemplate(stack: ItemStack, recipeManager: RecipeManager) = getRecipeFromStack(stack, recipeManager) != null
        fun createWithID(id: ResourceLocation) = ItemStack(NTechItems.assemblyTemplate.get(), 1).apply { orCreateTag.putString("recipe", id.toString()) }
    }
}

