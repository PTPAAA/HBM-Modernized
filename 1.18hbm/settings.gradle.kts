/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
*/

@file:Suppress("ERROR_SUPPRESSION")

pluginManagement {
    includeBuild("sorcerer")
    repositories {
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases")
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    val foojayVersion: String by settings
    id("org.gradle.toolchains.foojay-resolver-convention") version foojayVersion // JDK auto-provisioning
}

rootProject.name = "nuclear-tech-mod-remake"

includeBuild("sorcerer")
include(
//    "spells",
//    "core",
    "targets:mc12forge",
    "targets:mc18forge",
    "targets:mc20forge",
)
