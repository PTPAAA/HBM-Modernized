package dev.ntmr.nucleartech.content.fluid.trait

import com.mojang.datafixers.util.Either
import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.fluid.ForceableFluidHandler
import dev.ntmr.nucleartech.content.fluid.voidAllFluids
import dev.ntmr.nucleartech.extensions.*
import dev.ntmr.nucleartech.system.world.BlockEntityTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.client.event.RenderTooltipEvent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.registries.ForgeRegistries
import kotlin.jvm.optionals.getOrNull
import net.minecraftforge.fluids.capability.IFluidHandlerItem
import net.minecraftforge.fluids.capability.IFluidHandler

object FluidTraitHandler {
    fun addHoverText(event: RenderTooltipEvent.GatherComponents) {
        val stack = event.itemStack
        if (!stack.isEmpty) {
            val capability = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).getOrNull() ?: return
            val tooltip = mutableListOf<Component>()

            for (fluid in capability.getFluids())
                collectTooltip(fluid, tooltip)

            if (event.tooltipElements.size > 1 && tooltip.isNotEmpty()) // if there are already other tooltips, add some space
                tooltip.add(0, Component.empty())

            event.tooltipElements.addAll(tooltip.map { Either.left(it) })
        } else { // if there's no item stack, try to understand whether and what fluid we have as good as we can
            var changed = false
            for (entry in event.tooltipElements.map { it.left() }) {
                val component = entry.getOrNull() as? MutableComponent ?: continue
                val contents = component.contents as? net.minecraft.network.chat.contents.TranslatableContents
                val args = contents?.args?.toList() ?: emptyList()
                val key = contents?.key ?: ""
                val fluid = (args.flatMap(::flattenArgs) + key)
                    .firstNotNullOfOrNull { ForgeRegistries.FLUIDS.firstOrNull { fluid -> fluid.fluidType.descriptionId == it } } ?: continue
                if (fluid.isSame(Fluids.EMPTY)) continue
                val fakeFluidStack = FluidStack(fluid, 1000)

                val tooltip = mutableListOf<Component>()
                collectTooltip(fakeFluidStack, tooltip)
                if (!changed && event.tooltipElements.size > 1 && tooltip.isNotEmpty())
                    tooltip.add(0, Component.empty())

                changed = event.tooltipElements.addAll(tooltip.map { Either.left(it) })
            }
        }
    }

    private fun collectTooltip(fluid: FluidStack, tooltip: MutableList<Component>) {
        val level = Minecraft.getInstance().level
        val flag = if (Minecraft.getInstance().options.advancedItemTooltips || Screen.hasShiftDown()) TooltipFlag.Default.ADVANCED else TooltipFlag.Default.NORMAL

        if (flag.isAdvanced) tooltip += Component.translatable(fluid.fluid.fluidType.descriptionId).gray()

        val traits = FluidTraitManager.getTraitsForFluidStack(fluid)
        for (trait in traits) {
            trait.appendHoverText(level, fluid, tooltip, flag)
        }
        if (!flag.isAdvanced && traits.any { it.trait.isTooltipFlagReactive })
            tooltip += LangKeys.INFO_HOLD_KEY_FOR_INFO.format(Component.literal("Shift").yellow().italic()).darkGray().italic()
    }

    private fun flattenArgs(arg: Any, passOn: MutableList<String> = mutableListOf()): Collection<String> {
        if (arg is MutableComponent && arg.contents is net.minecraft.network.chat.contents.TranslatableContents) {
            val contents = arg.contents as net.minecraft.network.chat.contents.TranslatableContents
            passOn += contents.key
            contents.args.forEach { flattenArgs(it, passOn) }
        } else {
            passOn += arg.toString()
        }
        return passOn
    }

    object BlockEntityFluidTraitListener : BlockEntityTracker.BlockEntityListener {
        private val releasedFluids = mutableListOf<FluidStack>()

        override fun onBlockEntityRemoved(level: Level, pos: BlockPos, blockEntity: BlockEntity) {
            if (blockEntity.isRemoved) return // Chunk is unloading

            for (cap in getAllFluidCaps(blockEntity)) cap.ifPresentInline { handler ->
                val fluids = if (handler is ForceableFluidHandler)
                    handler.voidAllFluids()
                else
                    handler.getFluids()

                for (fluid in fluids) {
                    if (fluid.isEmpty || fluid.amount == Int.MAX_VALUE)
                        break

                    if (handler !is ForceableFluidHandler && releasedFluids.any { it.isFluidStackIdentical(fluid) }) {
                        break // We already released this fluid before, it's likely it's coming from the same tank
                    }

                    for (trait in FluidTraitManager.getTraitsForFluidStack(fluid)) {
                        trait.releaseFluidInWorld(level, pos, fluid, FluidTrait.FluidReleaseType.Spill)
                    }
                }

                releasedFluids += fluids.map(FluidStack::copy)
            }
        }

        fun clearUnelegantlyReleasedFluids() {
            releasedFluids.clear()
        }

        private fun getAllFluidCaps(blockEntity: BlockEntity) = buildList {
            add(blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER))
            for (direction in Direction.entries)
                add(blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction))
        }
    }
}
