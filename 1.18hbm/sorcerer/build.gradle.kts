/*
 SPDX-FileCopyrightText: 2024-2025 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.shadow) apply false
}

allprojects {
    group = "dev.ntmr"
    val sorcererVersion: String by project
    version = sorcererVersion

    repositories {
        mavenCentral()
    }
}
