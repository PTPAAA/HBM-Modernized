/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer.api.registries

interface RegistryCandidate<out T> {

}

val <T> RegistryCandidate<T>.mirroredEntry: AutoGenRegistryEntry<T, *>
    get() = TODO()


