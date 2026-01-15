package dev.ntmr.nucleartech.api.fluid.trait

import com.google.gson.JsonObject
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.IForgeRegistryEntry

public interface FluidTrait : IForgeRegistryEntry<FluidTrait> {
    public val descriptionId: String
    public fun getName(data: AttachedFluidTrait<*>): MutableComponent

    public val isTooltipFlagReactive: Boolean get() = false
    public fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait<*>, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += data.getDisplayName()
    }

    public fun releaseFluidInWorld(level: Level, pos: BlockPos, fluid: FluidStack, releaseType: FluidReleaseType, data: AttachedFluidTrait<*>) {}

    public enum class FluidReleaseType {
        /** Fluid is deleted entirely (shouldn't be used normally) */
        Void,
        /** Fluid is burned or combusted */
        Burn,
        /** Fluid is spilled via leakage or the container breaking */
        Spill,
    }

    public fun loadAdditionalData(id: ResourceLocation, json: JsonObject): CompoundTag = CompoundTag()
}
