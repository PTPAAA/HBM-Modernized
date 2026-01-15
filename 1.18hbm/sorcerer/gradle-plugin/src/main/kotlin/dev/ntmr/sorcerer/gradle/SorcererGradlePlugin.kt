/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer.gradle

import dev.ntmr.sorcerer.SorcererCommandLineProcessor
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.*

class SorcererGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { emptyList() }
    }

    override fun getCompilerPluginId() = SorcererCommandLineProcessor.PLUGIN_ID

    private val artifact = SubpluginArtifact(
        groupId = "dev.ntmr",
        artifactId = "sorcerer-compiler-plugin-embeddable",
        version = "0.1.0", // TODO dynamic
    )

    override fun getPluginArtifact() = artifact

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) =
        kotlinCompilation.target.project.plugins.hasPlugin(SorcererGradlePlugin::class.java)
            && kotlinCompilation.platformType == KotlinPlatformType.jvm
}
