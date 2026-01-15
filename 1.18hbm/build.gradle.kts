/*
 SPDX-FileCopyrightText: 2024-2025 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.sorcerer) apply false
    alias(libs.plugins.forge.gradle) apply false
    alias(libs.plugins.shadow) apply false
}

apply(from = ".scripts/git-hashes.gradle.kts")
apply(from = ".scripts/properties-reader.gradle.kts")

val reproducible: Boolean by extra { project.hasProperty("release") && !project.hasProperty("norepro") }
val isGitAvailable: Boolean by extra { false }
val gitSrcHashes: MutableMap<File, String?> by extra { mutableMapOf() }

allprojects {
    group = "dev.ntmr"

    repositories {
        mavenCentral()
    }

    if (reproducible)
        tasks.withType<AbstractArchiveTask>().configureEach {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }

    if (isGitAvailable) {
        val srcDir = project.file("src")
        if (srcDir.exists()) {
            gitSrcHashes[srcDir] = null
        }
    }
}

if (isGitAvailable) {
    val getDirHashes: (dirs: Array<File>) -> List<String> by extra
    val gitHashData: (input: InputStream) -> String by extra

    val hashes = getDirHashes(gitSrcHashes.keys.toTypedArray())
    gitSrcHashes.keys.forEachIndexed { index, srcDir -> gitSrcHashes[srcDir] = hashes[index] }

    val combinedHashStream = ByteArrayOutputStream()
    for (hash in hashes) {
        combinedHashStream.write(hash.toByteArray())
    }
    val combinedHash = gitHashData(ByteArrayInputStream(combinedHashStream.toByteArray()))
    project.extra["gitSrcHash"] = combinedHash
}

allprojects {
    val modVersionMajor: String by project
    val modVersionApi: String by project
    val modVersionMinor: String by project
    val modVersionPatch: String by project

    var modVersion: String by extra

    if (project.hasProperty("release"))
        modVersion = "$modVersionMajor.$modVersionApi.$modVersionMinor.$modVersionPatch"
    else {
        val getCurrentCommitHash: () -> String by rootProject.extra
        val isWorkingTreeModified: () -> Boolean by rootProject.extra
        val gitSrcHash: String by rootProject.extra

        // snapshots are always one patch higher than the current release
        val snapshotBaseVersion = "$modVersionMajor.$modVersionApi.$modVersionMinor.${modVersionPatch.toInt() + 1}"

        val date = SimpleDateFormat("yyyyMMdd").format(Date())

        modVersion = if (isGitAvailable)
            "$snapshotBaseVersion-snapshot$date+commit-${getCurrentCommitHash()}${if (isWorkingTreeModified()) "-mod.src-${gitSrcHash.substring(0, 12)}" else ""}"
        else
            "$snapshotBaseVersion-snapshot$date"
    }
}

println("Mod version: ${extra["modVersion"]}")
