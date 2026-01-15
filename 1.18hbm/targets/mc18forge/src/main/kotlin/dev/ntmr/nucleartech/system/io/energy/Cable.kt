/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.io.energy

import dev.ntmr.nucleartech.content.block.entity.transmitters.CableBlockEntity
import dev.ntmr.nucleartech.extensions.getOrNull
import dev.ntmr.nucleartech.system.io.AbstractTransmitter
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

class Cable(blockEntity: CableBlockEntity) : AbstractTransmitter<Cable, EnergyNetwork, IEnergyStorage>(blockEntity) {
    override fun self() = this
    override fun isNeighboringTransmitter(blockEntity: BlockEntity, side: Direction?) = blockEntity is CableBlockEntity
    override fun isValidTransmitterNeighbour(blockEntity: BlockEntity, side: Direction?) = isNeighboringTransmitter(blockEntity, side)
    override fun isValidNetworkMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(CapabilityEnergy.ENERGY, side.opposite).isPresent
    override fun getMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(CapabilityEnergy.ENERGY, side.opposite).getOrNull()
    override fun createNetworkByMerging(networks: Collection<EnergyNetwork>) = EnergyNetwork(networks)
    override fun compatibleWith(network: EnergyNetwork) = true

    override fun onNetworkChanged(oldNetwork: EnergyNetwork?, newNetwork: EnergyNetwork?) {
        blockEntity as CableBlockEntity
        blockEntity.capability.invalidate()
        blockEntity.capability = blockEntity.createCapability()
    }
}
