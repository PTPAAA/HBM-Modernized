/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.rbmk

import dev.ntmr.nucleartech.content.menu.ContainerDataListener
import dev.ntmr.nucleartech.content.menu.NTechContainerMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKAutoControlMenu
import dev.ntmr.nucleartech.content.menu.slots.data.DoubleDataSlot
import dev.ntmr.nucleartech.content.menu.slots.data.NTechDataSlot
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import dev.ntmr.nucleartech.packets.SetRBMKAutoControlRodValuesMessage
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAutoControlBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory
import java.util.function.Predicate

class RBMKAutoControlScreen(
    menu: RBMKAutoControlMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKAutoControlMenu>(menu, playerInventory, title), ContainerDataListener<RBMKAutoControlBlockEntity> {
    private val texture = ntm("textures/gui/rbmk/auto_control.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    private lateinit var editBoxes: Array<EditBox>

    override fun init() {
        super.init()

        val editBoxFilter = Predicate<String> { string -> string != null && string.all { char -> char.isDigit() || char == '.' }}
        editBoxes = Array(4) {
            EditBox(font, leftPos + 30, topPos + 27 + it * 11, 26, 6, Component.empty()).apply {
                setTextColor(-1)
                setTextColorUneditable(-1)
                setBordered(false)
                setMaxLength(if (it < 2) 5 else 6)
                setFilter(editBoxFilter)
                this@RBMKAutoControlScreen.addRenderableWidget(this)
            }
        }

        menu.addDataListener(this)
    }

    override fun dataChanged(menu: NTechContainerMenu<RBMKAutoControlBlockEntity>, data: NTechDataSlot.Data) {
        if (data is DoubleDataSlot.DoubleData) {
            editBoxes[data.slot.toInt()].value = ((data.value * 10.0).toInt() / 10.0).toString()
        }
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == 256) minecraft!!.player!!.closeContainer()

        var inputConsumed = false
        for (editBox in editBoxes) {
            editBox.keyPressed(keyCode, scanCode, modifiers)
            inputConsumed = editBox.canConsumeInput()
            if (inputConsumed) break
        }

        return if (inputConsumed) true else super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun resize(minecraft: Minecraft, x: Int, y: Int) {
        val values = editBoxes.map { it.value }
        super.resize(minecraft, x, y)
        editBoxes.forEachIndexed { index, editBox -> editBox.value = values[index] }
    }

    override fun removed() {
        super.removed()
        menu.removeDataListener(this)
    }

    override fun containerTick() {
        super.containerTick()
        editBoxes.forEach { it.tick() }
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        if (x.toInt() in leftPos + 28..leftPos + 58 && y.toInt() in topPos + 70..topPos + 80) {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
            val data = editBoxes.mapIndexed { index, editBox ->
                val clamp = if (index < 2) 100.0 else 9999.0
                var result = (editBox.value.toDoubleOrNull() ?: 0.0).coerceIn(0.0, clamp)
                result = (result * 10).toInt() / 10.0
                editBox.value = result.toString()
                result
            }
            NuclearPacketHandler.sendToServer(SetRBMKAutoControlRodValuesMessage(data[0], data[1], data[2], data[3]))
        }

        for (i in 0..2) {
            if (x.toInt() in leftPos + 61..leftPos + 83 && y.toInt() in topPos + 48 + i * 11..topPos + 58 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i)
                return true
            }
        }

        return super.mouseClicked(x, y, button)
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTicks)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight)

        val height = (56 * (1.0 - menu.blockEntity.rodLevel)).toInt()
        if (height > 0) graphics.blit(texture, leftPos + 124, topPos + 29, 176, 56 - height, 8, height)

        val function = menu.blockEntity.function.ordinal
        graphics.blit(texture, leftPos + 59, topPos + 27, 184, function * 19, 26, 19)
    }
}






