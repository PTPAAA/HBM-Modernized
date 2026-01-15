/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.spells.minecraft.nbt

import java.io.DataOutput

interface Tag {
    fun write(output: DataOutput)
    override fun toString(): String
    fun getId(): Byte
    fun copy(): Tag
    fun getAsString(): String
}
