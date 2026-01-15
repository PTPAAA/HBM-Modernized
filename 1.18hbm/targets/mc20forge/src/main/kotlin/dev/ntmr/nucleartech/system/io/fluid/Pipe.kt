/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.system.io.fluid

import dev.ntmr.nucleartech.content.block.entity.transmitters.FluidPipeBlockEntity
import dev.ntmr.nucleartech.extensions.getOrNull
import dev.ntmr.nucleartech.system.io.AbstractTransmitter
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.capability.IFluidHandler

class Pipe(blockEntity: FluidPipeBlockEntity) : AbstractTransmitter<Pipe, FluidNetwork, IFluidHandler>(blockEntity) {
    val fluid get() = (this.blockEntity as FluidPipeBlockEntity).fluid

    override fun self() = this
    override fun isNeighboringTransmitter(blockEntity: BlockEntity, side: Direction?) = blockEntity is FluidPipeBlockEntity
    override fun isValidTransmitterNeighbour(blockEntity: BlockEntity, side: Direction?) = blockEntity is FluidPipeBlockEntity && blockEntity.fluid.isSame(fluid)
    override fun isValidNetworkMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, side.opposite).isPresent
    override fun getMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, side.opposite).orElse(null)
    override fun createNetworkByMerging(networks: Collection<FluidNetwork>) = FluidNetwork(networks)
    override fun compatibleWith(network: FluidNetwork) = network.fluid.isSame(fluid)

    override fun onNetworkChanged(oldNetwork: FluidNetwork?, newNetwork: FluidNetwork?) {
        super.onNetworkChanged(oldNetwork, newNetwork)

        blockEntity as FluidPipeBlockEntity
        blockEntity.capability.invalidate()
        blockEntity.capability = blockEntity.createCapability()
    }
}
