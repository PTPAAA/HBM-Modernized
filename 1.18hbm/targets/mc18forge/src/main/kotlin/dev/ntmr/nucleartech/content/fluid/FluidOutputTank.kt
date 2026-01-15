package dev.ntmr.nucleartech.content.fluid

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidOutputTank(capacity: Int, validator: (FluidStack) -> Boolean = { true }) : NTechFluidTank(capacity, validator) {
    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = 0
}
