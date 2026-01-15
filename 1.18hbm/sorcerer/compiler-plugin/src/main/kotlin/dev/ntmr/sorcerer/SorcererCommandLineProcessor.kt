/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class SorcererCommandLineProcessor : CommandLineProcessor {
    override val pluginId = PLUGIN_ID
    override val pluginOptions = emptyList<AbstractCliOption>()

    companion object {
        const val PLUGIN_ID = "dev.ntmr.sorcerer.compiler-plugin"
    }
}
