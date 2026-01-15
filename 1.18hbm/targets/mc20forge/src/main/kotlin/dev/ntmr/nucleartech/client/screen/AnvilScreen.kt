/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.NTechRecipeTypes
import dev.ntmr.nucleartech.content.menu.AnvilMenu
import dev.ntmr.nucleartech.content.recipe.StackedIngredient
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilConstructingRecipe
import dev.ntmr.nucleartech.content.recipe.containerSatisfiesRequirements
import dev.ntmr.nucleartech.content.recipe.getItems
import dev.ntmr.nucleartech.extensions.green
import dev.ntmr.nucleartech.extensions.red
import dev.ntmr.nucleartech.extensions.yellow
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.packets.AnvilConstructMessage
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.searchtree.SearchRegistry
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.player.Inventory
import kotlin.math.ceil
import kotlin.math.min

class AnvilScreen(anvilMenu: AnvilMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<AnvilMenu>(anvilMenu, playerInventory, title) {
    init {
        imageWidth = GUI_WIDTH
        imageHeight = GUI_HEIGHT
        inventoryLabelY = imageHeight - 94
    }

    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var constructButton: Button
    private lateinit var recipeButtons: List<Button>
    private lateinit var searchBox: EditBox
    private val tier = menu.tier
    private val originalRecipeList = menu.playerInventory.player.level().recipeManager.getAllRecipesFor(NTechRecipeTypes.CONSTRUCTING).filter { it.isTierWithinBounds(tier) }.sortedBy { it.getResultItem(menu.playerInventory.player.level().registryAccess()).displayName.string }
    private val searchResults = mutableListOf<AnvilConstructingRecipe>()
    private var pagesCount = ceil(originalRecipeList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
    private var currentPage = 1

    override fun init() {
        super.init()

        backButton = addRenderableWidget(ChangePageButton(leftPos + 7, topPos + 71, false) { pageBack() })
        nextButton = addRenderableWidget(ChangePageButton(leftPos + 106, topPos + 71, true) { pageForward() })
        constructButton = addRenderableWidget(ConstructButton(leftPos + 52, topPos + 53))

        val buttonsStartX = leftPos + 16
        val buttonsStartY = topPos + 71
        val buttonOffset = 18

        val newButtons = mutableListOf<Button>()
        for (buttonNumberY in 0 until 2) for (buttonNumberX in 0 until 5) {
            newButtons += addRenderableWidget(RecipeButton(
                buttonsStartX + buttonNumberX * buttonOffset,
                buttonsStartY + buttonNumberY * buttonOffset,
                buttonNumberY * 5 + buttonNumberX
            ))
        }
        recipeButtons = newButtons.toList()

        searchBox = addRenderableWidget(EditBox(font, leftPos + 10, topPos + 111, 106, 12, Component.translatable("itemGroup.search")))
        with(searchBox) {
            setTextColor(0xFFFFFF)
            setTextColorUneditable(0xFFFFFF)
            setResponder(this@AnvilScreen::refreshSearchResults)
            setBordered(false)
            setEditable(true)
            setMaxLength(50)
            value = ""
        }

        updateButtonVisibility()
    }

    override fun removed() {
        super.removed()
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        searchBox.mouseClicked(mouseX, mouseY, button) // so the search bar can deselect properly
        return super.mouseClicked(mouseX, mouseY, button)
    }

    private fun pageBack() {
        if (currentPage > 1) currentPage--
        else currentPage = pagesCount
        updateButtonVisibility()
    }

    private fun pageForward() {
        if (currentPage < pagesCount) currentPage++
        else currentPage = 1
        updateButtonVisibility()
    }

    private fun updateButtonVisibility() {
        backButton.visible = currentPage > 1
        nextButton.visible = currentPage < pagesCount
        if (currentPage == pagesCount) {
            val recipeCount = (if (searchResults.isEmpty()) originalRecipeList.size else searchResults.size) - (currentPage - 1) * RECIPES_PER_PAGE
            for (i in 0 until recipeCount) recipeButtons[i].visible = true
            for (i in recipeCount until RECIPES_PER_PAGE) recipeButtons[i].visible = false
        } else recipeButtons.forEach { it.visible = true }
        setSelection(-1, -1, -1)
    }

    private fun refreshSearchResults(string: String) {
        searchResults.clear()
        currentPage = 1
        if (string.isBlank()) {
            pagesCount = ceil(originalRecipeList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
            updateButtonVisibility()
            return
        }
        val lowerString = string.lowercase(java.util.Locale.ROOT)
        searchResults.addAll(originalRecipeList.filter { 
            val output = it.getResultItem(menu.playerInventory.player.level().registryAccess())
            output.hoverName.string.lowercase(java.util.Locale.ROOT).contains(lowerString)
        })
        val recipesCount = if (searchResults.isEmpty()) originalRecipeList.size else searchResults.size
        pagesCount = ceil(recipesCount.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
        updateButtonVisibility()
    }

    private var selected = -1
    private var selectionX = -1
    private var selectionY = -1

    private fun setSelection(index: Int, x: Int, y: Int) {
        selected = index
        selectionX = x
        selectionY = y
    }

    private fun getOverlayTypeForButton(index: Int): AnvilConstructingRecipe.OverlayType {
        val results = if (searchResults.isEmpty()) originalRecipeList else searchResults
        return results[(currentPage - 1) * RECIPES_PER_PAGE + index].overlay
    }

    private fun getSelectedRecipe(): AnvilConstructingRecipe? {
        if (selected == -1) return null
        val results = if (searchResults.isEmpty()) originalRecipeList else searchResults
        return results[(currentPage - 1) * RECIPES_PER_PAGE + selected]
    }

    private fun canConstructSelectedRecipe(): Boolean {
        val selectedRecipe = getSelectedRecipe() ?: return false
        return selectedRecipe.ingredientsList.containerSatisfiesRequirements(menu.playerInventory)
    }

    private fun sendConstructPacket() {
        val recipe = getSelectedRecipe() ?: return
        NuclearPacketHandler.sendToServer(AnvilConstructMessage(recipe.recipeID, Screen.hasShiftDown()))
    }

    override fun containerTick() {
        super.containerTick()
        searchBox.tick()
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        RenderSystem.disableBlend()
        renderFg(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    private var slideSize = 1

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val slide = (slideSize - 42).coerceIn(0, 1000)
        var multiplier = 1
        while (true) {
            if (slide >= 51 * multiplier) {
                graphics.blit(TEXTURE, leftPos + 125 + 51 * multiplier, topPos + 17, 125, 17, 54, 108)
                multiplier++
            } else break
        }
        graphics.blit(TEXTURE, leftPos + 125 + slide, topPos + 17, 125, 17, 54, 108)

        if (searchBox.isFocused) graphics.blit(TEXTURE, leftPos + 8, topPos + 108, 150, 222, 106, 16)

        val recipeList = if (searchResults.isEmpty()) originalRecipeList else searchResults
        val itemsCount = min(recipeList.size - (currentPage - 1) * RECIPES_PER_PAGE, RECIPES_PER_PAGE)
        val itemsStartX = leftPos + 17
        val itemsStartY = topPos + 72
        val itemOffset = 18
        
        // blitOffset replacement: render items at slightly higher Z
        graphics.pose().pushPose()
        graphics.pose().translate(0.0f, 0.0f, 100.0f)
        
        for (itemNumberY in 0 until min(itemsCount / 5, 2)) for (itemNumberX in 0 until 5) {
             val stack = recipeList[(currentPage - 1) * RECIPES_PER_PAGE + itemNumberY * 5 + itemNumberX].getDisplay()
             val x = itemsStartX + itemNumberX * itemOffset
             val y = itemsStartY + itemNumberY * itemOffset
             graphics.renderItem(stack, x, y)
             graphics.renderItemDecorations(font, stack, x, y)
        }
        val lastRowItemsAmount = if (itemsCount == RECIPES_PER_PAGE) 0 else itemsCount.rem(5)
        for (lastRowEntry in 0 until lastRowItemsAmount) {
             val stack = recipeList[(currentPage - 1) * RECIPES_PER_PAGE + itemsCount - lastRowItemsAmount + lastRowEntry].getDisplay()
             val x = itemsStartX + lastRowEntry * itemOffset
             val y = itemsStartY + itemsCount / 5 * itemOffset
             graphics.renderItem(stack, x, y)
             graphics.renderItemDecorations(font, stack, x, y)
        }
        
        graphics.pose().popPose()
    }

    // Renamed from renderFg to avoid confusion with AbstractContainerScreen.renderLabels (which is not what this is)
    private fun renderFg(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (selectionX != -1 && selectionY != -1) {
            RenderSystem.disableDepthTest()
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            graphics.blit(TEXTURE, selectionX, selectionY, 0, GUI_HEIGHT, 18, 18)
            RenderSystem.enableDepthTest()
        }
        searchBox.render(graphics, mouseX, mouseY, partialTicks)

        val texts = getSelectedRecipeToListFormatted()
        if (texts.isEmpty()) {
            slideSize = 0
            return
        }

        val longest = texts.maxOf { font.width(it) }
        val scale = .5F
        graphics.pose().pushPose()
        graphics.pose().scale(scale, scale, scale)
        var offsetY = 0
        for (component in texts) {
            graphics.drawString(font, component, (leftPos * 2 + 260F).toInt(), (topPos * 2 + 50F + offsetY).toInt(), 0xFFFFFF, false)
            offsetY += font.lineHeight
        }
        slideSize = (longest * scale).toInt()
        graphics.pose().popPose()
    }

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        if (!menu.carried.isEmpty) return
        val recipeList = if (searchResults.isEmpty()) originalRecipeList else searchResults
        for (i in recipeButtons.indices) {
            val button = recipeButtons[i]
            if (button.isHoveredOrFocused && button.visible) {
                val itemStack = recipeList[(currentPage - 1) * RECIPES_PER_PAGE + i].getDisplay()
                graphics.renderTooltip(font, itemStack, mouseX, mouseY)
            }
        }
    }

    override fun keyPressed(key: Int, p_97766_: Int, p_97767_: Int): Boolean {
        if (key == 256) getMinecraft().player!!.closeContainer()
        return if (!searchBox.keyPressed(key, p_97766_, p_97767_) && !searchBox.canConsumeInput()) super.keyPressed(key, p_97766_, p_97767_) else true
    }

    private fun getSelectedRecipeToListFormatted(): List<Component> {
        val selectedRecipe = getSelectedRecipe() ?: return emptyList()
        val (available, missing) = partitionIngredients(selectedRecipe.ingredientsList)
        return buildList {
            add(LangKeys.INFO_INPUTS.yellow())
            addAll(missing.map { Component.literal("> ").append(it.red()).red() })
            addAll(available.map { Component.literal("> ").append(it.green()).green() })
            add(Component.empty())
            add(LangKeys.INFO_OUTPUTS.yellow())
            addAll(selectedRecipe.results.map { Component.literal("> ${it.stack.count}x ").append(it.stack.hoverName).apply { if (it.chance != 1F) append(" (${it.chance * 100}%)") }})
        }
    }

    private fun partitionIngredients(ingredients: List<StackedIngredient>): Pair<List<MutableComponent>, List<MutableComponent>> {
        val (available, missing) = ingredients.filterNot(StackedIngredient::isEmpty).partition {
            var requiredAmountLeft = it.requiredAmount
            for (stack in menu.playerInventory.getItems()) {
                if (it.test(stack)) {
                    val removeCount = min(stack.count, requiredAmountLeft)
                    stack.count -= removeCount
                    requiredAmountLeft -= removeCount
                }
            }
            requiredAmountLeft <= 0
        }
        return available.map { Component.literal("${it.requiredAmount}x ").append(it.items[0].hoverName) } to
                missing.map { Component.literal("${it.requiredAmount}x ").append(it.items[0].hoverName) }
    }

    private class ChangePageButton(x: Int, y: Int, val isForward: Boolean, onPress: OnPress) : Button(x, y, 9, 36, Component.empty(), onPress, DEFAULT_NARRATION) {
        override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            if (!isHoveredOrFocused) return
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            val x = if (isForward) GUI_WIDTH + 9 else GUI_WIDTH
            graphics.blit(TEXTURE, this.x, this.y, x, 186, width, height)
        }
    }

    private inner class RecipeButton(x: Int, y: Int, val index: Int) : Button(x, y, 18, 18, Component.empty(), { setSelection(index, x, y) }, DEFAULT_NARRATION) {
        override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            RenderSystem.disableDepthTest()
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            val x = 18 + getOverlayTypeForButton(index).ordinal * 18
            graphics.blit(TEXTURE, this.x, this.y, x, GUI_HEIGHT, width, height)
            RenderSystem.enableDepthTest()
            if (isHoveredOrFocused) graphics.fillGradient(this.x + 1, this.y + 1, this.x + width - 1, this.y + height - 1, 0x80FFFFFF.toInt(), 0x80FFFFFF.toInt())
        }

        override fun playDownSound(manager: SoundManager) {
            if (selected == index) return
            super.playDownSound(manager)
        }
    }

    private inner class ConstructButton(x: Int, y: Int) : Button(x, y, 18, 18, Component.empty(), { sendConstructPacket() }, DEFAULT_NARRATION){
        override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            if (!isHoveredOrFocused || !canConstructSelectedRecipe()) return
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            graphics.blit(TEXTURE, this.x, this.y, GUI_WIDTH, 150, 18, 18)
        }

        override fun playDownSound(manager: SoundManager) {
            if (!canConstructSelectedRecipe()) return
            super.playDownSound(manager)
        }
    }

    companion object {
        private const val GUI_WIDTH = 176
        private const val GUI_HEIGHT = 222
        private const val RECIPES_PER_PAGE = 10
        private val TEXTURE = ntm("textures/gui/anvil.png")

        fun reloadSearchTree() {
            // Deprecated/Unused in 1.20 port - filtering is local
        }
    }
}






