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
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.client.event.RenderTooltipEvent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.registries.ForgeRegistries
import kotlin.jvm.optionals.getOrNull

object FluidTraitHandler {
    fun addHoverText(event: RenderTooltipEvent.GatherComponents) {
        val stack = event.itemStack
        if (!stack.isEmpty) {
            val capability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).getOrNull() ?: return
            val tooltip = mutableListOf<Component>()

            for (fluid in capability.getFluids())
                collectTooltip(fluid, tooltip)

            if (event.tooltipElements.size > 1 && tooltip.isNotEmpty()) // if there are already other tooltips, add some space
                tooltip.add(0, TextComponent.EMPTY)

            event.tooltipElements.addAll(tooltip.map { Either.left(it) })
        } else { // if there's no item stack, try to understand whether and what fluid we have as good as we can
            var changed = false
            for (entry in event.tooltipElements.map { it.left() }) {
                val component = entry.getOrNull() as? TranslatableComponent ?: continue
                val fluid = (component.args.flatMap(::flattenArgs) + component.key)
                    .firstNotNullOfOrNull { ForgeRegistries.FLUIDS.firstOrNull { fluid -> fluid.attributes.translationKey == it } } ?: continue
                if (fluid.isSame(Fluids.EMPTY)) continue
                val fakeFluidStack = FluidStack(fluid, 1000)

                val tooltip = mutableListOf<Component>()
                collectTooltip(fakeFluidStack, tooltip)
                if (!changed && event.tooltipElements.size > 1 && tooltip.isNotEmpty())
                    tooltip.add(0, TextComponent.EMPTY)

                changed = event.tooltipElements.addAll(tooltip.map { Either.left(it) })
            }
        }
    }

    private fun collectTooltip(fluid: FluidStack, tooltip: MutableList<Component>) {
        val level = Minecraft.getInstance().level
        val flag = if (Minecraft.getInstance().options.advancedItemTooltips || Screen.hasShiftDown()) TooltipFlag.Default.ADVANCED else TooltipFlag.Default.NORMAL

        if (flag.isAdvanced) tooltip += TranslatableComponent(fluid.translationKey).gray()

        val traits = FluidTraitManager.getTraitsForFluidStack(fluid)
        for (trait in traits) {
            trait.appendHoverText(level, fluid, tooltip, flag)
        }
        if (!flag.isAdvanced && traits.any { it.trait.isTooltipFlagReactive })
            tooltip += LangKeys.INFO_HOLD_KEY_FOR_INFO.format(TextComponent("Shift").yellow().italic()).darkGray().italic()
    }

    private fun flattenArgs(arg: Any, passOn: MutableList<String> = mutableListOf()): Collection<String> {
        if (arg is TranslatableComponent) {
            passOn += arg.key
            arg.args.forEach { flattenArgs(it, passOn) }
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
            add(blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY))
            for (direction in Direction.entries)
                add(blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction))
        }
    }
}
