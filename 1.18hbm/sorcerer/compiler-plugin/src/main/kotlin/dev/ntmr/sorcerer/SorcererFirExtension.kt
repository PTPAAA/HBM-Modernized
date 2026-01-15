/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer

import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class SorcererFirExtension : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::SorcererGenerationExtension
    }
}
