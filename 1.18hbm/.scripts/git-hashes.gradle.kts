/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.inject.Inject

// Checking for Git

interface InjectedExecOps {
    @get:Inject val execOps: ExecOperations
}

fun getExecOps(): ExecOperations =
    project.objects.newInstance<InjectedExecOps>().execOps

fun getGitAvailability(): Boolean {
    val output = getExecOps().exec {
        setIgnoreExitValue(true)
        commandLine("git", "rev-parse", "--is-inside-work-tree")
        standardOutput = object : OutputStream() {
            override fun write(byte: Int) {}
        }
    }

    return output.getExitValue() == 0
}

val isGitAvailable: Boolean by extra { getGitAvailability() }

fun requireGit() {
    if (!isGitAvailable)
        throw IllegalStateException("Git is not available")
}

// Locating .git

fun getGitDir(): File {
    requireGit()

    val output = ByteArrayOutputStream()
    getExecOps().exec {
        commandLine("git", "rev-parse", "--git-dir")
        standardOutput = output
    }

    return project.file(output.toString("UTF-8").trim())
}

// Working tree status

fun isWorkingTreeModified(): Boolean {
    requireGit()

    val output = ByteArrayOutputStream()
    getExecOps().exec {
        commandLine("git", "status", "--porcelain")
        standardOutput = output
    }

    return !output.toString().isEmpty()
}

// Getting various hashes

fun getCurrentCommitHash(): String {
    requireGit()

    val output = ByteArrayOutputStream()
    getExecOps().exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = output
    }

    return output.toString("UTF-8").trim()
}

fun getDirHash(dir: File): String {
    requireGit()

    val tempGitIndex = File.createTempFile("git-index", null)
    Files.copy(getGitDir().toPath().resolve("index"), tempGitIndex.toPath(), StandardCopyOption.REPLACE_EXISTING)
    tempGitIndex.deleteOnExit()

    getExecOps().exec {
        environment("GIT_INDEX_FILE", tempGitIndex)
        commandLine("git", "add", "-A")
    }

    val output = ByteArrayOutputStream()
    getExecOps().exec {
        environment("GIT_INDEX_FILE", tempGitIndex)
        commandLine("git", "write-tree", "--prefix=${dir.relativeTo(getRootDir())}")
        standardOutput = output
    }

    return output.toString("UTF-8").trim()
}

fun getDirHashes(vararg dirs: File): List<String> {
    requireGit()

    val tempGitIndex = File.createTempFile("git-index", null)
    Files.copy(getGitDir().toPath().resolve("index"), tempGitIndex.toPath(), StandardCopyOption.REPLACE_EXISTING)
    tempGitIndex.deleteOnExit()

    getExecOps().exec {
        environment("GIT_INDEX_FILE", tempGitIndex)
        commandLine("git", "add", "-A")
    }

    val rootDir = getRootDir()
    val output = ByteArrayOutputStream()
    for (srcDir in dirs) {
        getExecOps().exec {
            environment("GIT_INDEX_FILE", tempGitIndex)
            commandLine("git", "write-tree", "--prefix=${srcDir.relativeTo(rootDir)}")
            standardOutput = output
        }
    }

    return output.toString("UTF-8").lines()
}

fun gitHashData(input: InputStream): String {
    requireGit()

    val output = ByteArrayOutputStream()
    getExecOps().exec {
        commandLine("git", "hash-object", "--stdin")
        standardInput = input
        standardOutput = output
    }

    return output.toString("UTF-8")
}

val getGitAvailability: () -> Boolean by extra { ::getGitAvailability }
val getGitDir: () -> File by extra { ::getGitDir }
val isWorkingTreeModified: () -> Boolean by extra { ::isWorkingTreeModified }
val getCurrentCommitHash: () -> String by extra { ::getCurrentCommitHash }
val getDirHash: (dir: File) -> String by extra { ::getDirHash }
val getDirHashes: (dirs: Array<File>) -> List<String> by extra { ::getDirHashes }
val gitHashData: (input: InputStream) -> String by extra { ::gitHashData }

