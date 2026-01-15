/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object SorcererConfigurationKeys {
    @JvmStatic val ENABLE_FOR_MODULE = CompilerConfigurationKey<String>("enableForModule")
}
