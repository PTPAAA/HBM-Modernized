/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.client.screen.rbmk

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.extensions.contains
import dev.ntmr.nucleartech.extensions.yellow
import dev.ntmr.nucleartech.math.rotate1DSquareMatrixInPlaceClockwise
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKConsoleMenu
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import dev.ntmr.nucleartech.packets.SetRBMKConsoleControlRodLevelMessage
import dev.ntmr.nucleartech.packets.SetRBMKConsoleScreenAssignedColumnsMessage
import dev.ntmr.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKConsoleBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKManualControlBlockEntity
import it.unimi.dsi.fastutil.bytes.ByteArrayList
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.block.Rotation
import net.minecraftforge.registries.ForgeRegistries
import kotlin.math.ceil
import kotlin.math.min
import net.minecraft.sounds.SoundEvents as VanillaSoundEvents

class RBMKConsoleScreen(
    menu: RBMKConsoleMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKConsoleMenu>(menu, playerInventory, title) { // TODO consider making it not a *container* screen
    private val texture = ntm("textures/gui/rbmk/console.png")

    init {
        imageWidth = 244
        imageHeight = 172
        inventoryLabelY = imageHeight - 94
    }

    private val selection = BooleanArray(15 * 15)
    private var az5Lid = true
    private var lastEmergyPress = 0L
    private lateinit var editBox: EditBox

    override fun init() {
        super.init()


        editBox = EditBox(font, leftPos + 9, topPos + 84, 35, 9, Component.empty()).apply {
            setTextColor(0x00FF00)
            setTextColorUneditable(0x008000)
            setBordered(false)
            setMaxLength(5)
            setFilter { string -> string != null && string.all { char -> char.isDigit() || char == '.' }}
            this@RBMKConsoleScreen.addRenderableWidget(this)
        }
    }

    override fun resize(minecraft: Minecraft, x: Int, y: Int) {
        val value = editBox.value
        super.resize(minecraft, x, y)
        editBox.value = value
    }

    override fun removed() {
        super.removed()
    }

    override fun containerTick() {
        super.containerTick()
        editBox.tick()
    }

    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        val console = menu.blockEntity
        val mouseX = x.toInt()
        val mouseY = y.toInt()

        val bx = 86
        val by = 11
        val size = 10

        // toggle selection
        if (mouseX in leftPos + bx .. leftPos + bx + 150 && mouseY in topPos + by .. topPos + by + 150) {
            val index = (mouseX - bx - leftPos) / size + (mouseY - by - topPos) / size * 15
            if (index in selection.indices && console.columns[index] != null) {
                selection[index] = !selection[index]
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, if (selection[index]) 1F else 0.75F))
                return true
            }
        }

        // clear selection
        if (mouseX in leftPos + 72 .. leftPos + 81 && mouseY in topPos + 70 .. topPos + 79) {
            selection.fill(false)
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.5F))
            return true
        }

        // select all control rods
        if (mouseX in leftPos + 61 .. leftPos + 70 && mouseY in topPos + 70 .. topPos + 79) {
            selection.fill(false)
            for ((index, column) in console.columns.withIndex()) {
                if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL)
                    selection[index] = true
            }
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 1.5F))
            return true
        }

        // select color groups
        for (i in 0..4) {
            if (mouseX in leftPos + 6 + i * 11 .. leftPos + 15 + i * 11 && mouseY in topPos + 70 .. topPos + 79) {
                selection.fill(false)
                for ((index, column) in console.columns.withIndex()) {
                    if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL && column.data.getByte("Color").toInt() == i)
                        selection[index] = true
                }
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.8F + i * 0.1F))
                return true
            }
        }

        // AZ-5
        if (mouseX in leftPos + 30 .. leftPos + 57 && mouseY in topPos + 138 .. topPos + 165) {
            if (az5Lid) {
                az5Lid = false
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(NTechSounds.rbmkAz5Cover.get(), 0.5F))
            } else if (lastEmergyPress + 3000L < System.currentTimeMillis()) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(NTechSounds.rbmkShutdown.get(), 1F))
                lastEmergyPress = System.currentTimeMillis()

                val rotatedColumns = console.columns.copyOf()
                repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                    rotatedColumns.rotate1DSquareMatrixInPlaceClockwise()
                }

                val controlRods = ByteArrayList(rotatedColumns.size / 2)
                for ((index, column) in rotatedColumns.withIndex()) {
                    if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL)
                        controlRods += index.toByte()
                }
                NuclearPacketHandler.sendToServer(SetRBMKConsoleControlRodLevelMessage(0.0, controlRods.toByteArray()))
            }
            return true
        }

        // save control rod setting
        if (mouseX in leftPos + 48 .. leftPos + 59 && mouseY in topPos + 82 .. topPos + 93) {
            var level = (editBox.value.toDoubleOrNull() ?: 0.0).coerceIn(0.0, 100.0)
            level = (level * 10).toInt() / 10.0
            editBox.value = level.toString()
            level *= 0.01

            val selectedRodsCount = selection.count { it }
            val selectedRodsArray = ByteArray(selectedRodsCount)
            val rotatedSelection = selection.copyOf()
            repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                rotatedSelection.rotate1DSquareMatrixInPlaceClockwise()
            }
            var nextIndex = 0
            for (i in rotatedSelection.indices) if (rotatedSelection[i])
                selectedRodsArray[nextIndex++] = i.toByte()

            NuclearPacketHandler.sendToServer(SetRBMKConsoleControlRodLevelMessage(level, selectedRodsArray))
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 1F))
            return true
        }

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j

            // toggle screen display type
            if (mouseX in leftPos + 6 + 40 * j..leftPos + 6 + 40 * j + 17 && mouseY in topPos + 8 + 21 * i..topPos + 8 + 21 * i + 17 && menu.clickMenuButton(minecraft!!.player!!, id)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.5F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, id)
                return true
            }

            // submit selected columns for screen display
            if (mouseX in leftPos + 24 + 40 * j..leftPos + 24 + 40 * j + 17 && mouseY in topPos + 8 + 21 * i..topPos + 8 + 21 * i + 17) {
                val selectedRodsCount = selection.count { it }
                val selectedRodsArray = ByteArray(selectedRodsCount)
                val rotatedSelection = selection.copyOf()
                repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                    rotatedSelection.rotate1DSquareMatrixInPlaceClockwise()
                }
                var nextIndex = 0
                for (k in rotatedSelection.indices) if (rotatedSelection[k])
                    selectedRodsArray[nextIndex++] = k.toByte()

                NuclearPacketHandler.sendToServer(SetRBMKConsoleScreenAssignedColumnsMessage(id, selectedRodsArray))
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.75F))
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

        val console = menu.blockEntity

        if (az5Lid) {
            graphics.blit(texture, leftPos + 30, topPos + 138, 228, 172, 28, 28)
        }

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j
            graphics.blit(texture, leftPos + 6 + 40 * j, topPos + 8 + 21 * i, console.screens[id].type.ordinal * 18, 238, 18, 18)
        }

        val bx = 86
        val by = 11
        val size = 10

        for ((index, column) in console.columns.withIndex()) {
            if (column == null) continue

            val x = bx + size * (index % 15)
            val y = by + size * (index / 15)

            val tx = column.type.ordinal * 10
            val ty = 172

            graphics.blit(texture, leftPos + x, topPos + y, tx, ty, size, size)

            val h = min(ceil((column.data.getDouble("Heat") - 20.0) * 10.0 / column.data.getDouble("MaxHeat")).toInt(), 10)
            graphics.blit(texture, leftPos + x, topPos + y + size - h, 0, 192 - h, 10, h)

            when (column.type) {
                RBMKConsoleBlockEntity.Column.Type.CONTROL -> {
                    val color = column.data.getByte("Color")
                    if (color > -1) graphics.blit(texture, leftPos + x, topPos + y, color * size, 202, 10, 10)
                    val fr = 8 - ceil(column.data.getDouble("RodLevel") * 8.0).toInt()
                    graphics.blit(texture, leftPos + x + 4, topPos + y + 1, 24, 183, 2, fr)
                }
                RBMKConsoleBlockEntity.Column.Type.CONTROL_AUTO -> {
                    val fr = 8 - ceil(column.data.getDouble("RodLevel") * 8.0).toInt()
                    graphics.blit(texture, leftPos + x + 4, topPos + y + 1, 24, 183, 2, fr)
                }
                RBMKConsoleBlockEntity.Column.Type.FUEL, RBMKConsoleBlockEntity.Column.Type.FUEL_REASIM -> {
                    if (column.data.contains("FuelHeat", Tag.TAG_DOUBLE)) {
                        val fh = ceil((column.data.getDouble("FuelHeat") - 20.0) * 8.0 / column.data.getDouble("FuelMaxHeat")).toInt()
                        graphics.blit(texture, leftPos + x + 1, topPos + y + size - fh - 1, 11, 191 - fh, 2, fh)

                        val fe = ceil(column.data.getDouble("Enrichment") * 8.0).toInt()
                        graphics.blit(texture, leftPos + x + 4, topPos + y + size - fe - 1, 14, 191 - fe, 2, fe)
                    }
                }
                RBMKConsoleBlockEntity.Column.Type.BOILER -> {
                    val fw = column.data.getInt("Water") * 8 / column.data.getInt("MaxWater")
                    graphics.blit(texture, leftPos + x + 1, topPos + y + size - fw - 1, 41, 191 - fw, 3, fw)
                    val fs = column.data.getInt("Steam") * 8 / column.data.getInt("MaxSteam")
                    graphics.blit(texture, leftPos + x + 6, topPos + y + size - fs - 1, 46, 191 - fs, 3, fs)

                    val fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation(column.data.getString("SteamType")))
                    if (fluid != null) when {
                        fluid.isSame(NTechFluids.steam.source.get()) -> graphics.blit(texture, leftPos + x + 4, topPos + y + 1, 44, 183, 2, 2)
                        fluid.isSame(NTechFluids.steamHot.source.get()) -> graphics.blit(texture, leftPos + x + 4, topPos + y + 3, 44, 185, 2, 2)
                        fluid.isSame(NTechFluids.steamSuperHot.source.get()) -> graphics.blit(texture, leftPos + x + 4, topPos + y + 5, 44, 187, 2, 2)
                        fluid.isSame(NTechFluids.steamUltraHot.source.get()) -> graphics.blit(texture, leftPos + x + 4, topPos + y + 7, 44, 189, 2, 2)
                    }
                }
                RBMKConsoleBlockEntity.Column.Type.HEATEX -> TODO()
                else -> {}
            }

            if (selection[index]) graphics.blit(texture, leftPos + x, topPos + y, 0, 192, 10, 10)
        }
    }

    override fun renderLabels(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {}

    override fun renderTooltip(graphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        super.renderTooltip(graphics, mouseX, mouseY)

        val console = menu.blockEntity

        val bx = 86
        val by = 11
        val size = 10

        if (mouseX in leftPos + bx .. leftPos + bx + 150 && mouseY in topPos + by .. topPos + by + 11 + 150) {
            val index = (mouseX - bx - leftPos) / size + (mouseY - by - topPos) / size * 15

            if (index in console.columns.indices) {
                val column = console.columns[index]
                if (column != null) {
                    graphics.renderTooltip(font, listOf(Component.literal(column.type.name)) + column.getFormattedStats(), java.util.Optional.empty(), mouseX, mouseY)
                }
            }
        }

        if (isHovering(62, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font, LangKeys.RBMK_CONSOLE_SELECT_CONTROL_RODS.get(), mouseX, mouseY)
        if (isHovering(73, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
            graphics.renderTooltip(font, LangKeys.RBMK_CONSOLE_DESELECT_CONTROL_RODS.get(), mouseX, mouseY)

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j + 1
            if (isHovering(7 + 40 * j, 9 + 21 * i, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
                graphics.renderTooltip(font, console.screens[id - 1].type.translationKey.format(id).yellow(), mouseX, mouseY)
            if (isHovering(25 + 40 * j, 9 + 21 * i, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
                graphics.renderTooltip(font, LangKeys.RBMK_CONSOLE_ASSIGN.format(id), mouseX, mouseY)
        }

        for ((index, color) in RBMKManualControlBlockEntity.Color.values().withIndex()) {
            val offset = index * 11 + 7
            if (isHovering(offset, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
                graphics.renderTooltip(font, LangKeys.RBMK_CONSOLE_SELECT_GROUP.format(color.name.lowercase()), mouseX, mouseY)
        }
    }
}






