/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.extensions

import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.ChatFormatting
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.BaseComponent
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.Component
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.MutableComponent
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.network.chat.TextComponent
import java.util.function.Supplier

fun MutableComponent.black() = withStyle(ChatFormatting.BLACK)
fun MutableComponent.darkBlue() = withStyle(ChatFormatting.DARK_BLUE)
fun MutableComponent.darkGreen() = withStyle(ChatFormatting.DARK_GREEN)
fun MutableComponent.darkAqua() = withStyle(ChatFormatting.DARK_AQUA)
fun MutableComponent.darkRed() = withStyle(ChatFormatting.DARK_RED)
fun MutableComponent.darkPurple() = withStyle(ChatFormatting.DARK_PURPLE)
fun MutableComponent.gold() = withStyle(ChatFormatting.GOLD)
fun MutableComponent.gray() = withStyle(ChatFormatting.GRAY)
fun MutableComponent.darkGray() = withStyle(ChatFormatting.DARK_GRAY)
fun MutableComponent.blue() = withStyle(ChatFormatting.BLUE)
fun MutableComponent.green() = withStyle(ChatFormatting.GREEN)
fun MutableComponent.aqua() = withStyle(ChatFormatting.AQUA)
fun MutableComponent.red() = withStyle(ChatFormatting.RED)
fun MutableComponent.lightPurple() = withStyle(ChatFormatting.LIGHT_PURPLE)
fun MutableComponent.yellow() = withStyle(ChatFormatting.YELLOW)
fun MutableComponent.white() = withStyle(ChatFormatting.WHITE)
fun MutableComponent.obfuscated() = withStyle(ChatFormatting.OBFUSCATED)
fun MutableComponent.bold() = withStyle(ChatFormatting.BOLD)
fun MutableComponent.strikethrough() = withStyle(ChatFormatting.STRIKETHROUGH)
fun MutableComponent.underline() = withStyle(ChatFormatting.UNDERLINE)
fun MutableComponent.italic() = withStyle(ChatFormatting.ITALIC)
fun MutableComponent.reset() = withStyle(ChatFormatting.RESET)

fun Supplier<out MutableComponent>.black() = get().black()
fun Supplier<out MutableComponent>.darkBlue() = get().darkBlue()
fun Supplier<out MutableComponent>.darkGreen() = get().darkGreen()
fun Supplier<out MutableComponent>.darkAqua() = get().darkAqua()
fun Supplier<out MutableComponent>.darkRed() = get().darkRed()
fun Supplier<out MutableComponent>.darkPurple() = get().darkPurple()
fun Supplier<out MutableComponent>.gold() = get().gold()
fun Supplier<out MutableComponent>.gray() = get().gray()
fun Supplier<out MutableComponent>.darkGray() = get().darkGray()
fun Supplier<out MutableComponent>.blue() = get().blue()
fun Supplier<out MutableComponent>.green() = get().green()
fun Supplier<out MutableComponent>.aqua() = get().aqua()
fun Supplier<out MutableComponent>.red() = get().red()
fun Supplier<out MutableComponent>.lightPurple() = get().lightPurple()
fun Supplier<out MutableComponent>.yellow() = get().yellow()
fun Supplier<out MutableComponent>.white() = get().white()
fun Supplier<out MutableComponent>.obfuscated() = get().obfuscated()
fun Supplier<out MutableComponent>.bold() = get().bold()
fun Supplier<out MutableComponent>.strikethrough() = get().strikethrough()
fun Supplier<out MutableComponent>.underline() = get().underline()
fun Supplier<out MutableComponent>.italic() = get().italic()
fun Supplier<out MutableComponent>.reset() = get().reset()

fun Supplier<out BaseComponent>.append(component: Component): MutableComponent = get().append(component)
fun BaseComponent.append(component: Supplier<out Component>): MutableComponent = append(component.get())

fun Component.prependIntent(indent: String = "  ") = TextComponent(indent).append(this)
