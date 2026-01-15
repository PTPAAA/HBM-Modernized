/*
SPDX-FileCopyrightText: 2022-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.api

/**
 * Annotate [ModPlugin]s with this so they can be detected.
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class NuclearTechPlugin
