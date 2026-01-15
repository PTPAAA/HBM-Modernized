package dev.ntmr.nucleartech.extensions

import net.minecraft.nbt.CompoundTag
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

fun FluidStack.isRawFluidEqual(other: FluidStack) = rawFluid == other.rawFluid && FluidStack.areFluidStackTagsEqual(this, other)

fun FluidStack.isRawFluidStackIdentical(other: FluidStack) = rawFluid == other.rawFluid && FluidStack.areFluidStackTagsEqual(this, other) && amount == other.amount

fun FluidStack.writeToNBTRaw(nbt: CompoundTag) = nbt.apply {
    putString("FluidName", ForgeRegistries.FLUIDS.getKey(rawFluid)!!.toString())
    putInt("Amount", amount)

    if (tag != null) {
        put("Tag", tag)
    }
}
