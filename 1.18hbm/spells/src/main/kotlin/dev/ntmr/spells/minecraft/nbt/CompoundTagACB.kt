/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

@file:Suppress("unused")

package dev.ntmr.spells.minecraft.nbt

import dev.ntmr.sorcerer.api.Linkage
import dev.ntmr.sorcerer.api.Linkage.CompatibleVersions.MC18
import dev.ntmr.sorcerer.api.MirrorBlueprint
import java.io.DataOutput
import java.util.*

@Linkage(MC18, "net.minecraft.nbt.CompoundTag")
@MirrorBlueprint
interface CompoundTagACB : Tag {
    override fun write(output: DataOutput)
    override fun getId(): Byte
    fun size(): Int

    fun put(key: String, tag: Tag): Tag?
    fun get(key: String): Tag?
    fun getTagType(key: String): Byte
    fun contains(key: String): Boolean
    fun contains(key: String, type: Int): Boolean
    fun remove(key: String)
    fun isEmpty(): Boolean
    override fun copy(): Tag
    fun merge(other: CompoundTagACB): CompoundTagACB

    fun putBoolean(key: String, value: Boolean)
    fun putByte(key: String, value: Byte)
    fun putShort(key: String, value: Short)
    fun putInt(key: String, value: Int)
    fun putLong(key: String, value: Long)
    fun putFloat(key: String, value: Float)
    fun putDouble(key: String, value: Double)
    fun putString(key: String, value: String)
    fun putByteArray(key: String, value: ByteArray)
    fun putByteArray(key: String, value: List<Byte>)
    fun putIntArray(key: String, value: IntArray)
    fun putIntArray(key: String, value: List<Int>)
    fun putLongArray(key: String, value: LongArray)
    fun putLongArray(key: String, value: List<Long>)
    fun putUUID(key: String, value: UUID)

    fun getBoolean(key: String): Boolean
    fun getByte(key: String): Byte
    fun getShort(key: String): Short
    fun getInt(key: String): Int
    fun getLong(key: String): Long
    fun getFloat(key: String): Float
    fun getDouble(key: String): Double
    fun getString(key: String): String
    fun getByteArray(key: String): ByteArray
    fun getIntArray(key: String): IntArray
    fun getLongArray(key: String): LongArray
    fun getCompound(key: String): CompoundTagACB
    fun getUUID(key: String): UUID

    fun hasUUID(key: String): Boolean
}
