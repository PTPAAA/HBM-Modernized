/*
 SPDX-FileCopyrightText: 2024-2025 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    val foojayVersion: String by settings
    id("org.gradle.toolchains.foojay-resolver-convention") version foojayVersion // JDK auto-provisioning
}

rootProject.name = "sorcerer"

val projectPaths = listOf(
    "api",
    "compiler-plugin",
    "compiler-plugin-embeddable",
    "gradle-plugin",
    "ide-plugin",
)

for (path in projectPaths) {
    include(path)
    project(":$path").name = "${rootProject.name}-$path"
}
