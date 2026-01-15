package dev.ntmr.nucleartech.content.fluid

class SimpleForceableFluidHandler(private val tanks: List<MutableFluidTank>) : ForceableFluidHandler {
    constructor(vararg tanks: MutableFluidTank) : this(tanks.toList())

    override fun getTanks() = tanks.size
    override fun getTankAccess(tank: Int): MutableFluidTank = tanks[tank]
}
