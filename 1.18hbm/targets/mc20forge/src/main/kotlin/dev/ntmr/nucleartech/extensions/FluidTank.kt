package dev.ntmr.nucleartech.extensions

import dev.ntmr.nucleartech.content.fluid.FluidUnit
import dev.ntmr.nucleartech.math.formatStorageFilling
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraftforge.fluids.capability.templates.FluidTank
import net.minecraft.network.chat.Component

fun FluidTank.getTooltip() = listOf(
    fluid.displayName,
    Component.literal(FluidUnit.UnitType.MINECRAFT.formatStorageFilling(fluidAmount, capacity))
)

fun FluidTank.writeToNBTRaw(nbt: CompoundTag) = nbt.apply {
    fluid.writeToNBTRaw(nbt)
}
