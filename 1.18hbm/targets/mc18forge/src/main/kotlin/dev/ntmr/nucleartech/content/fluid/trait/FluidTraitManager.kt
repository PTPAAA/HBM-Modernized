/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mojang.logging.LogUtils
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.api.fluid.trait.FluidTraitManager
import dev.ntmr.nucleartech.packets.resources.ClientSyncedResourceManager
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.GsonHelper
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.conditions.ICondition.IContext
import net.minecraftforge.fluids.FluidStack

object FluidTraitManager : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "${MODID}_fluid_traits"),
    FluidTraitManager, ClientSyncedResourceManager<FluidTraitManager.FluidTraitSyncedData>
{
    @JvmStatic private val LOGGER = LogUtils.getLogger()
    internal var context: IContext = IContext.EMPTY
    private var byTarget = emptyMap<AttachedFluidTrait.FluidTarget, List<AttachedFluidTrait<*>>>()

    override fun apply(definitions: MutableMap<ResourceLocation, JsonElement>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        byTarget = buildMap {
            for ((id, json) in definitions) {
                if (id.path.startsWith('_')) continue

                try {
                    if (json.isJsonObject && !CraftingHelper.processConditions(json.asJsonObject, "conditions", context)) {
                        LOGGER.debug("Skipping loading fluid trait {} as its conditions were not met", id)
                        continue
                    }
                    val attachedTrait = AttachedFluidTraitImpl.fromJson(id, GsonHelper.convertToJsonObject(json, "top element"))
                    getOrPut(attachedTrait.target, ::mutableListOf) += attachedTrait
                } catch (ex: JsonParseException) {
                    LOGGER.error("Couldn't parse fluid trait $id", ex)
                }
            }
        }.mapValues { (_, attachedList) -> attachedList.sortedBy { it.trait.registryName!!.toString() } }

        LOGGER.info("Loaded {} attached fluid traits for {} fluid targets", byTarget.values.sumOf(List<*>::size), byTarget.keys.size)
    }

    // TODO support overrides
    override fun getTraitsForFluidStack(fluidStack: FluidStack) = byTarget.filterKeys { it.test(fluidStack) }.values.flatten()

    override val syncManagerName = "fluid_traits"

    override fun getServerSyncedData() = FluidTraitSyncedData(byTarget.values.flatten())

    override fun deserializeFromNetwork(buffer: FriendlyByteBuf) =
        FluidTraitSyncedData(buffer.readList(AttachedFluidTraitImpl.Companion::deserializeFromNetwork))

    override fun handle(data: FluidTraitSyncedData) {
        byTarget = data.fluidTraits.groupBy(AttachedFluidTrait<*>::target)
        LOGGER.info("Received {} attached fluid traits for {} fluid targets from server", byTarget.values.sumOf(List<*>::size), byTarget.keys.size)
    }

    data class FluidTraitSyncedData(val fluidTraits: List<AttachedFluidTrait<*>>) : ClientSyncedResourceManager.SyncedData {
        override fun serializeToNetwork(buffer: FriendlyByteBuf) {
            buffer.writeCollection(fluidTraits, ::serializeTrait)
        }

        private fun serializeTrait(buffer: FriendlyByteBuf, trait: AttachedFluidTrait<*>) {
            buffer.writeRegistryId(trait.trait)
            buffer.writeUtf(trait.target.toString())
            buffer.writeNbt(trait.tag)
        }
    }
}
