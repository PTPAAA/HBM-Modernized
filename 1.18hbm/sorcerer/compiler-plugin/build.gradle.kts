/*
 SPDX-FileCopyrightText: 2024-2025 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

repositories {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap/")
}

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    compileOnly(libs.kotlin.compiler)
    implementation(project(":sorcerer-api"))
}

kotlin {
    jvmToolchain(8)
}
