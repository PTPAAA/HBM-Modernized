package dev.ntmr.nucleartech.extensions

import dev.ntmr.nucleartech.content.fluid.FluidUnit
import dev.ntmr.nucleartech.math.formatStorageFilling
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TextComponent
import net.minecraftforge.fluids.capability.templates.FluidTank

fun FluidTank.getTooltip() = listOf(
    fluid.rawFluid.attributes.getDisplayName(fluid),
    TextComponent(FluidUnit.UnitType.MINECRAFT.formatStorageFilling(fluidAmount, capacity))
)

fun FluidTank.writeToNBTRaw(nbt: CompoundTag) = nbt.apply {
    fluid.writeToNBTRaw(nbt)
}
