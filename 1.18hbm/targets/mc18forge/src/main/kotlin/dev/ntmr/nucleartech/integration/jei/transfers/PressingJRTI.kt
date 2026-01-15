package dev.ntmr.nucleartech.integration.jei.transfers

import dev.ntmr.nucleartech.content.menu.PressMenu
import dev.ntmr.nucleartech.content.recipe.PressingRecipe
import dev.ntmr.nucleartech.integration.jei.NuclearRecipeTypes
import dev.ntmr.nucleartech.ntm
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.Slot

class PressingJRTI : IRecipeTransferInfo<PressMenu, PressingRecipe> {
    override fun getContainerClass(): Class<PressMenu> = PressMenu::class.java
    @Deprecated("Deprecated in Java")
    override fun getRecipeClass(): Class<PressingRecipe> = PressingRecipe::class.java
    @Deprecated("Deprecated in Java")
    override fun getRecipeCategoryUid(): ResourceLocation = ntm("pressing")
    override fun getRecipeType() = NuclearRecipeTypes.PRESSING
    override fun canHandle(container: PressMenu, recipe: PressingRecipe) = true
    override fun getRecipeSlots(container: PressMenu, recipe: PressingRecipe): MutableList<Slot> = MutableList(2) { container.slots[it] }
    override fun getInventorySlots(container: PressMenu, recipe: PressingRecipe): MutableList<Slot> = MutableList(36) { container.slots[it + 4] }
    override fun requireCompleteSets(container: PressMenu, recipe: PressingRecipe) = false
}
