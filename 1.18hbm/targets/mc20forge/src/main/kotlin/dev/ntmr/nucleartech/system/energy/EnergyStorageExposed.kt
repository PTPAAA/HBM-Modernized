/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.energy

import net.minecraftforge.energy.EnergyStorage

class EnergyStorageExposed(
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    energy: Int
) : EnergyStorage(capacity, maxReceive, maxExtract, energy) {
    constructor(capacity: Int) : this(capacity, capacity, capacity, 0)
    constructor(capacity: Int, maxTransfer: Int) : this(capacity, maxTransfer, maxTransfer, 0)
    constructor(capacity: Int, maxReceive: Int, maxExtract: Int) : this(capacity, maxReceive, maxExtract, 0)

    @Suppress("PROPERTY_HIDES_JAVA_FIELD") // Intended
    var energy: Int
        get() = super.energy
        set(value) {
            super.energy = value
        }
}
