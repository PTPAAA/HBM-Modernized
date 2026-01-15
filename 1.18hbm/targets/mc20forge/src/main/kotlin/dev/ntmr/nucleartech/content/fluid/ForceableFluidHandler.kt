package dev.ntmr.nucleartech.content.fluid

import dev.ntmr.nucleartech.extensions.getFluids
import dev.ntmr.nucleartech.extensions.isRawFluidEqual
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

/**
 * An extension of the [IFluidHandler] interface that allows for direct and forceful manipulation of fluid tanks.
 *
 * As access to tanks is exposed via [getTankAccess], this interface can provide default implementations for all [IFluidHandler] operations.
 *
 * An [IFluidHandler] that delegates its [fill] and [drain] operations to [forceFill] and [forceDrain] respectively can be obtained with
 * [ForceableFluidHandler.asForcingHandler], useful for example for allowing draining of [FluidInputTank]s or filling of [FluidOutputTank]s.
 */
interface ForceableFluidHandler : IFluidHandler {
    /**
     * Exposes direct access to this handler's fluid tanks.
     *
     * Modification of returned [MutableFluidTank]s is allowed.
     */
    fun getTankAccess(tank: Int): MutableFluidTank

    /**
     * Forcefully fills a specific tank with a certain fluid.
     *
     * This function is modeled after [IFluidHandler.fill], but targets only a specific tank of the fluid handler and bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.isFluidValid] and [net.minecraftforge.fluids.IFluidTank.fill] functions, utilizing [MutableFluidTank.forceFluid].
     * However, this function won't override existing fluids contained in the tank if their content doesn't have raw fluid equality with the provided [resource].
     *
     * @see forceFill
     * @see fill
     * @see FluidStack.isRawFluidEqual
     */
    fun forceFillTank(tank: Int, resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (resource.isEmpty)
            return 0

        val tank = getTankAccess(tank)
        if (!tank.fluid.isEmpty && !tank.fluid.isRawFluidEqual(resource))
            return 0

        val toFill = min(tank.capacity - tank.fluid.amount, resource.amount)

        if (action.execute())
            tank.forceFluid(FluidStack(resource, tank.fluid.amount + toFill))

        return toFill
    }

    /**
     * Forcefully drains a specific tank if its content matches the provided [resource].
     *
     * This function is modeled after [IFluidHandler.drain], but targets only a specific tank of the fluid handler and bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.drain] function, utilizing [MutableFluidTank.forceFluid]. This function will only drain fluid
     * from the tank if its content has raw fluid equality with the provided [resource]. The returned [FluidStack]'s [FluidStack.amount] will not
     * surpass the [resource]'s amount.
     *
     * @see forceDrain
     * @see drain
     * @see FluidStack.isRawFluidEqual
     */
    fun forceDrainTank(tank: Int, resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (resource.isEmpty)
            return FluidStack.EMPTY

        val tank = getTankAccess(tank)
        if (tank.fluid.isEmpty || !tank.fluid.isRawFluidEqual(resource))
            return FluidStack.EMPTY

        val toDrain = min(tank.fluid.amount, resource.amount)
        val returnStack = FluidStack(tank.fluid, toDrain)

        if (action.execute())
            tank.forceFluid(FluidStack(tank.fluid, tank.fluid.amount - toDrain))

        return returnStack
    }

    /**
     * Forcefully drains a specific tank, not matching against any specific fluid.
     *
     * This function is modeled after [IFluidHandler.drain], but targets only a specific tank of the fluid handler and bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.drain] function, utilizing [MutableFluidTank.forceFluid].
     *
     * @see forceDrain
     * @see drain
     */
    fun forceDrainTank(tank: Int, maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        if (maxDrain <= 0)
            return FluidStack.EMPTY

        val tank = getTankAccess(tank)
        if (tank.fluid.isEmpty)
            return FluidStack.EMPTY

        val toDrain = min(tank.fluid.amount, maxDrain)
        val returnStack = FluidStack(tank.fluid, toDrain)

        if (action.execute())
            tank.forceFluid(FluidStack(tank.fluid, tank.fluid.amount - toDrain))

        return returnStack
    }

    /**
     * Forcefully fills the handler's tanks with a certain fluid.
     *
     * This function is modeled after [IFluidHandler.fill], filling each available tank if possible, but bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.isFluidValid] and [net.minecraftforge.fluids.IFluidTank.fill] functions, utilizing [MutableFluidTank.forceFluid].
     * However, this function won't override existing fluids contained in the tanks if their contents don't have raw fluid equality with the provided [resource].
     *
     * @see forceFillTank
     * @see fill
     * @see FluidStack.isRawFluidEqual
     */
    fun forceFill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (resource.isEmpty)
            return 0

        var filled = 0
        for (tankIndex in 0..<tanks) {
            if (resource.amount <= filled)
                break

            val tank = getTankAccess(tankIndex)
            if (!tank.fluid.isEmpty && !tank.fluid.isRawFluidEqual(resource))
                continue
            val toFill = min(tank.capacity - tank.fluid.amount, resource.amount - filled).coerceAtLeast(0)

            if (action.execute())
                tank.forceFluid(FluidStack(resource, tank.fluid.amount + toFill))

            filled += toFill
        }

        return filled
    }

    /**
     * Forcefully drains the handler's tanks if their contents match the provided [resource].
     *
     * This function is modeled after [IFluidHandler.drain], draining from each available tank if possible, and bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.drain] function, utilizing [MutableFluidTank.forceFluid]. This function will only drain fluid
     * from the tank if its content has raw fluid equality with the provided [resource]. The returned [FluidStack]'s [FluidStack.amount] will not
     * surpass the [resource]'s amount.
     *
     * Tanks will be drained in the order provided by [getTankAccess].
     *
     * @see forceDrainTank
     * @see drain
     * @see FluidStack.isRawFluidEqual
     */
    fun forceDrain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (resource.isEmpty)
            return FluidStack.EMPTY

        val stack = FluidStack(resource, 0)
        for (tankIndex in 0..<tanks) {
            if (stack.amount >= resource.amount)
                break

            val tank = getTankAccess(tankIndex)
            if (tank.fluid.isEmpty || !tank.fluid.isRawFluidEqual(resource))
                continue
            val toDrain = min(tank.fluid.amount, resource.amount - stack.amount)

            if (action.execute())
                tank.forceFluid(FluidStack(tank.fluid, tank.fluid.amount - toDrain))

            stack.amount += toDrain
        }

        return stack
    }

    /**
     * Forcefully drains the handler's tanks, not matching against any specific fluid.
     *
     * This function is modeled after [IFluidHandler.drain], draining from each tank containing equal fluids, and bypasses the underlying
     * tank's [net.minecraftforge.fluids.IFluidTank.drain] function, utilizing [MutableFluidTank.forceFluid].
     *
     * Tanks will be drained in the order provided by [getTankAccess], and the first non-empty tank will set the type of fluid to be drained
     * for the rest of the operation, ensuring each drained tank's fluid has raw fluid equality with the returned [FluidStack].
     *
     * @see forceDrain
     * @see drain
     * @see FluidStack.isRawFluidEqual
     */
    fun forceDrain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        if (maxDrain <= 0)
            return FluidStack.EMPTY

        var stack = FluidStack.EMPTY
        for (tankIndex in 0..<tanks) {
            if (stack.amount >= maxDrain)
                break

            val tank = getTankAccess(tankIndex)
            if (tank.fluid.isEmpty)
                continue
            if (stack.isEmpty)
                stack = FluidStack(tank.fluid, 0)
            if (stack.rawFluid == Fluids.EMPTY || !tank.fluid.isRawFluidEqual(stack))
                continue
            val toDrain = min(tank.fluid.amount, stack.amount - maxDrain)

            if (action.execute())
                tank.forceFluid(FluidStack(tank.fluid, tank.fluid.amount - toDrain))

            stack.amount += toDrain
        }

        return stack
    }

    override fun getTankCapacity(tank: Int): Int =
        if (tank in 0..<tanks) getTankAccess(tank).capacity else 0

    override fun getFluidInTank(tank: Int): FluidStack =
        if (tank in 0..<tanks) getTankAccess(tank).fluid.copy() else FluidStack.EMPTY

    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean =
        if (tank in 0..<tanks) getTankAccess(tank).isFluidValid(stack) else false

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (resource.isEmpty)
            return 0

        var filled = 0
        for (tankIndex in 0..<tanks) {
            if (resource.amount <= filled)
                break

            filled += getTankAccess(tankIndex).fill(FluidStack(resource, resource.amount - filled), action)
        }

        return filled
    }

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (resource.isEmpty)
            return FluidStack.EMPTY

        val stack = FluidStack(resource, 0)
        for (tankIndex in 0..<tanks) {
            if (stack.amount >= resource.amount)
                break

            val tank = getTankAccess(tankIndex)
            if (tank.fluid.isEmpty || !tank.fluid.isRawFluidEqual(resource))
                continue

            stack.amount += tank.drain(FluidStack(resource, resource.amount - stack.amount), action).amount
        }

        return stack
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        if (maxDrain <= 0)
            return FluidStack.EMPTY

        var stack = FluidStack.EMPTY
        for (tankIndex in 0..<tanks) {
            if (stack.amount >= maxDrain)
                break

            val tank = getTankAccess(tankIndex)
            if (tank.fluid.isEmpty)
                continue
            if (stack.isEmpty)
                stack = FluidStack(tank.fluid, 0)
            if (stack.rawFluid == Fluids.EMPTY || !tank.fluid.isRawFluidEqual(stack))
                continue

            stack.amount += tank.drain(FluidStack(stack.rawFluid, maxDrain - stack.amount), action).amount
        }

        return stack
    }
}

fun ForceableFluidHandler.voidAllFluids(): List<FluidStack> = buildList {
    for (tankIndex in 0..<tanks) {
        val fluid = forceDrainTank(tankIndex, Int.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE)
        if (!fluid.isEmpty)
            add(fluid)
    }
}

fun ForceableFluidHandler.asForcingHandler() = object : IFluidHandler by this {
    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        return forceFill(resource, action)
    }

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        return forceDrain(resource, action)
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        return forceDrain(maxDrain, action)
    }
}

fun MutableFluidTank.asForcingHandler() = SimpleForceableFluidHandler(this).asForcingHandler()

fun ForceableFluidHandler.forceFillFluids(fluids: Collection<FluidStack>, simulate: Boolean): List<FluidStack> {
    if (fluids.all(FluidStack::isEmpty)) return emptyList()

    val handler = if (simulate) copy() else this

    val remainingStacks = mutableListOf<FluidStack>()
    for (fluid in fluids) {
        val remainder = fluid.amount - handler.forceFill(fluid, IFluidHandler.FluidAction.EXECUTE)
        if (remainder > 0) remainingStacks += FluidStack(fluid, remainder)
    }
    return remainingStacks
}

// won't copy the validator
fun ForceableFluidHandler.copy(): ForceableFluidHandler = SimpleForceableFluidHandler(getFluids().mapIndexed { index, fluid -> NTechFluidTank(getTankCapacity(index)).apply { setFluid(fluid) } })

