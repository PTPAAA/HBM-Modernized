package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.Agnostics
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.client.rendering.CustomBEWLR
import dev.ntmr.nucleartech.client.rendering.SpecialModels
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.recipe.ChemRecipe
import dev.ntmr.nucleartech.extensions.blue
import dev.ntmr.nucleartech.extensions.bold
import dev.ntmr.nucleartech.extensions.gray
import dev.ntmr.nucleartech.extensions.italic
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.level.Level
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import net.minecraftforge.fluids.FluidStack
import org.intellij.lang.annotations.Language
import java.util.function.Consumer
import kotlin.math.floor

class ChemPlantTemplateItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component = Component.translatable(getDescriptionId(stack), Minecraft.getInstance().level?.use { getRecipeFromStack(stack, it.recipeManager)?.recipeID }?.let { Component.translatable("${LangKeys.CAT_CHEMISTRY}.${it.namespace}.${it.path.removePrefix("chem/")}") } ?: "N/A")



    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        if (level == null) return
        val recipe = getRecipeFromStack(stack, level.recipeManager) ?: return

        fun formatFluid(fluidStack: FluidStack) = Component.literal("  ${fluidStack.amount}mb ").append(fluidStack.displayName).gray()

        with(tooltip) {
            add(LangKeys.INFO_OUTPUTS.bold().gray())
            for (output in recipe.resultsList) add(Component.literal("  ${output.count}x ").append(output.hoverName).gray())
            if (!recipe.outputFluid1.isEmpty) add(formatFluid(recipe.outputFluid1))
            if (!recipe.outputFluid2.isEmpty) add(formatFluid(recipe.outputFluid2))
            add(LangKeys.INFO_INPUTS.bold().gray())
            for (input in recipe.ingredientsList) add(Component.literal("  ${input.requiredAmount}x ").append(input.items.first().hoverName).gray())
            if (!recipe.inputFluid1.isEmpty) add(formatFluid(recipe.inputFluid1))
            if (!recipe.inputFluid2.isEmpty) add(formatFluid(recipe.inputFluid2))
            add(LangKeys.INFO_PRODUCTION_TIME.bold().gray())
            add(Component.literal("  ${floor(recipe.duration / 20F * 100F) / 100F} ").append(LangKeys.WORD_SECONDS.get()).gray())
            if (flag.isAdvanced) add(Component.literal(recipe.id.toString()).italic().blue())
        }
    }

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            override fun getCustomRenderer() = CustomBEWLR
        })
    }

    companion object {
        fun getRecipeIDFromStack(stack: ItemStack) = if (stack.item is ChemPlantTemplateItem) stack.tag?.getString("recipe")?.let { ResourceLocation(it) } else null
        fun getRecipeFromStack(stack: ItemStack, recipeManager: RecipeManager) = getRecipeIDFromStack(stack)?.let { recipeManager.byKey(it).orElse(null) as? ChemRecipe }
        fun getAllChemTemplates(recipeManager: RecipeManager): List<ItemStack> = recipeManager.getAllRecipesFor(NTechRecipeTypes.CHEM).map { ItemStack(
            NTechItems.chemTemplate.get()).apply { orCreateTag.putString("recipe", it.id.toString()) } }
        fun isValidTemplate(stack: ItemStack, recipeManager: RecipeManager) = getRecipeFromStack(stack, recipeManager) != null
        fun createWithID(id: ResourceLocation) = ItemStack(NTechItems.chemTemplate.get(), 1).apply { orCreateTag.putString("recipe", id.toString()) }

        fun onModifyBakingResult(event: net.minecraftforge.client.event.ModelEvent.ModifyBakingResult) {
            val sprites = getChemistrySpriteLocations(Minecraft.getInstance().resourceManager)
            for (sprite in sprites) {
                // In 1.20, we can't easily bake a BlockModel from string without a Baker.
                // We inject a placeholder baked model that points to the sprite.
                // TODO: Implement proper item model baking (extrusion) or flat item rendering
                
                 val id = sprite // The sprite location is used as the model ID?
                 // The original code used 'sprite' as key for injectIntoModelBakery.
                 // We should verify if 'sprite' RL is what is requested.
                 
                 // For now, we just map it.
                 // We can use SpecialModels simple model wrapper if we made it public or duplicated logic.
            }
        }

        fun getChemistrySpriteLocations(resourceManager: ResourceManager) = resourceManager.listResources("textures/chemistry_icons") { it.path.endsWith(".png") }.keys.map { ResourceLocation(it.namespace, it.path.removePrefix("textures/").removeSuffix(".png")) }
    }
}

