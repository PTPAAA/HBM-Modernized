package dev.ntmr.nucleartech.datagen.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.ntmr.nucleartech.content.NTechRecipeSerializers
import dev.ntmr.nucleartech.extensions.toJson
import dev.ntmr.nucleartech.mc
import dev.ntmr.nucleartech.ntm
import net.minecraftforge.fluids.FluidStack
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Consumer

class CentrifugeRecipeBuilder(private val input: Ingredient, private vararg val outputs: ItemStack) : RecipeBuilder {
    constructor(input: Ingredient, vararg outputs: Item) : this(input, *outputs.map(Item::getDefaultInstance).toTypedArray())

    private var inputFluid: FluidStack = FluidStack.EMPTY
    private val fluidOutputs: MutableList<FluidStack> = mutableListOf()

    fun requires(fluid: FluidStack) = apply { this.inputFluid = fluid }
    fun results(fluid: FluidStack) = apply { this.fluidOutputs.add(fluid) }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): CentrifugeRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this
    override fun getResult(): Item = if (outputs.isNotEmpty()) outputs.first().item else net.minecraft.world.item.Items.AIR // fallback if no item output

    override fun save(consumer: Consumer<FinishedRecipe>) = save(consumer, ntm("${if(outputs.isNotEmpty()) RecipeBuilder.getDefaultRecipeId(result).path else "fluid_recipe"}_from_centrifuge"))
    override fun save(consumer: Consumer<FinishedRecipe>, name: String) = save(consumer, ntm("${name}_from_centrifuge"))

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (outputs.isEmpty() && fluidOutputs.isEmpty()) throw IllegalStateException("Not enough data specified")
        if (outputs.size > 4) throw IllegalStateException("Too many outputs")
        consumer.accept(Result(recipeName, input, outputs.toList(), inputFluid, fluidOutputs, advancement, ResourceLocation(recipeName.namespace, "recipes/centrifuge/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val input: Ingredient,
        private val outputs: List<ItemStack>,
        private val inputFluid: FluidStack,
        private val fluidOutputs: List<FluidStack>,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            if (!input.isEmpty) json.add("ingredient", input.toJson())
            
            if (outputs.isNotEmpty()) {
                json.add("results", JsonArray().apply {
                    for (output in outputs) add(output.toJson())
                })
            }

            if (!inputFluid.isEmpty) {
                val inputJson = JsonObject()
                inputJson.addProperty("fluid", inputFluid.fluid.registryName.toString())
                inputJson.addProperty("amount", inputFluid.amount)
                if (inputFluid.hasTag()) inputJson.addProperty("nbt", inputFluid.tag.toString())
                json.add("inputFluid", inputJson)
            }

            if (fluidOutputs.isNotEmpty()) {
                val fluidsArray = JsonArray()
                for (fluid in fluidOutputs) {
                    val fluidJson = JsonObject()
                    fluidJson.addProperty("fluid", fluid.fluid.registryName.toString())
                    fluidJson.addProperty("amount", fluid.amount)
                    if (fluid.hasTag()) fluidJson.addProperty("nbt", fluid.tag.toString())
                    fluidsArray.add(fluidJson)
                }
                json.add("fluidOutputs", fluidsArray)
            }
        }

        override fun getId() = id
        override fun getType() = NTechRecipeSerializers.CENTRIFUGE.get()
        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()
        override fun getAdvancementId(): ResourceLocation = advancementID
    }
}
