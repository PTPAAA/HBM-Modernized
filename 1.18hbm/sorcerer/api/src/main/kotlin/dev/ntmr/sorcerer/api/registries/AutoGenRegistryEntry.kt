/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer.api.registries

interface AutoGenRegistryEntry<E, M> {

}

@Suppress("UNCHECKED_CAST")
fun <T> AutoGenRegistryEntry<*, *>.cast() = this as T
