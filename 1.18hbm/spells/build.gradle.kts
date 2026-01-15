/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

plugins {
    kotlin("jvm")
    id("dev.ntmr.sorcerer")
}

dependencies {
    val sorcererVersion: String by project
    implementation("dev.ntmr:sorcerer-api:$sorcererVersion")
}

kotlin {
    jvmToolchain(8)
}

val spells by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    extendsFrom(configurations.implementation.get(), configurations.runtimeOnly.get())
}
val spawns by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    extendsFrom(configurations.implementation.get(), configurations.runtimeOnly.get())
}
val spellsImplementation by configurations.creating {
    extendsFrom(configurations.implementation.get())
}
val spawnsImplementation by configurations.creating {
    extendsFrom(configurations.implementation.get())
}

tasks.register<Jar>("spawnsJar") {
    archiveClassifier.set("spawns")
    from(sourceSets.main.get().output) {
        exclude("**/*ACB.class") // TODO temporary workaround
    }
}

tasks.register<Jar>("spellsJar") {
    from(sourceSets.main.get().output)
}

artifacts {
    add("spawns", tasks.named("spawnsJar"))
    add("spells", tasks.named("spellsJar"))
}
