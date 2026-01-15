/*
 SPDX-FileCopyrightText: 2024-2025 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(kotlin("gradle-plugin-api"))
    compileOnly(kotlin("gradle-plugin"))

    compileOnly(libs.kotlin.compiler)

    api(project(":sorcerer-compiler-plugin"))
}

gradlePlugin {
    plugins {
        create("sorcerer") {
            id = "dev.ntmr.sorcerer"
            displayName = "Sorcerer Minecraft Multi-Version Compiler"
            description = project.description
            implementationClass = "dev.ntmr.sorcerer.gradle.SorcererGradlePlugin"
        }
    }
}
