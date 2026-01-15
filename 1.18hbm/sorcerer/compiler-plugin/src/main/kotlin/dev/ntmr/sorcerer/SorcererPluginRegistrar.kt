/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer

import org.jetbrains.kotlin.cli.common.messages.getLogger
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.util.Logger

lateinit var LOGGER: Logger

@OptIn(ExperimentalCompilerApi::class)
class SorcererPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2 get() = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        LOGGER = SorcererLogger(configuration.getLogger())
        FirExtensionRegistrarAdapter.registerExtension(SorcererFirExtension())
    }

    private class SorcererLogger(val logger: Logger) : Logger {
        private fun prefixSorcererMessage(message: String) = "[sorcerer] $message"

        override fun log(message: String) = logger.log(prefixSorcererMessage(message))
        override fun warning(message: String) = logger.warning(prefixSorcererMessage(message))
        override fun error(message: String) = logger.error(prefixSorcererMessage(message))

        @Deprecated(Logger.FATAL_DEPRECATION_MESSAGE, ReplaceWith(Logger.FATAL_REPLACEMENT))
        override fun fatal(message: String): Nothing =
            @Suppress("DEPRECATION")
            logger.fatal(prefixSorcererMessage(message))
    }
}
