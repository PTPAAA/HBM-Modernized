/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer.api

open class SorcererException : Exception {
    constructor() : super()
    constructor(msg: String) : super(msg)
}

class MissingApiVersionLinkage(apiVersion: String, className: String) :
    SorcererException("Missing @Linkage annotation for API version '$apiVersion' on class '$className'")

class NoQualifiedNameException(symbolName: String) :
    SorcererException("Couldn't get fully qualified-name for symbol $symbolName")
