/*
SPDX-FileCopyrightText: 2017 Ronald Stevanus
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: CC0-1.0
 */

// https://gist.github.com/ronaldstevanus/b4fab9802b2b0979db97f45cb6207caf
// Ported to Kotlin by MartinTheDragon

import java.util.Properties

/**
 * Load all local properties defined on local.properties files in all projects (root and sub projects).
 * How to use:
 * - add `apply(from = "properties-reader.gradle.kts")` on the root project's build.gradle.kts
 * - retrieve local properties like so:
 *   ```kotlin
 *   val localProperties: Properties? by extra
 *   ```
 */

private val LOCAL_PROPERTIES_FILENAME = "local.properties"

private fun getLocalProperties(dir: File): Properties? {
    val file = dir.resolve(LOCAL_PROPERTIES_FILENAME)
    if (!file.exists()) return null
    return Properties().apply { load(file.inputStream()) }
}

project.extra["localProperties"] = getLocalProperties(rootDir)

allprojects {
    beforeEvaluate {
        val properties = getLocalProperties(project.projectDir)
        val localProperties: Properties? by rootProject.extra // TODO combine properties from parent projects
        if (properties != null && localProperties != null)
            localProperties!!.putAll(properties)
        project.extra["localProperties"] = localProperties ?: properties
    }
}
