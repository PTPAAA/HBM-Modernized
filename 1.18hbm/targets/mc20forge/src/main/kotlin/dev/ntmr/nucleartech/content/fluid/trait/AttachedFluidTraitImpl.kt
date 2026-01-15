/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.NTechRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

class AttachedFluidTraitImpl<out T : FluidTrait> private constructor(
    override val trait: T,
    override val target: AttachedFluidTrait.FluidTarget,
    override val tag: CompoundTag
) : AttachedFluidTrait<T> {
    companion object {
        @JvmStatic fun fromJson(id: ResourceLocation, json: JsonObject): AttachedFluidTrait<*> {
            val trait = GsonHelper.getAsString(json, "trait", null) ?: throw JsonParseException("Fluid trait attachment $id doesn't define a fluid trait to attach")
            val parsedTrait = NTechRegistries.FLUID_TRAITS.get().getValue(ResourceLocation(trait)) ?: throw JsonParseException("Couldn't find fluid trait with id $trait")

            val fluid = GsonHelper.getAsString(json, "fluid", null)
            val tag = GsonHelper.getAsString(json, "tag", null)
            if (fluid == null && tag == null)
                throw JsonParseException("Fluid trait attachment $id doesn't have a fluid id or tag defined")

            val tagTarget = tag?.let { TagTarget(TagKey.create(ForgeRegistries.FLUIDS.registryKey, ResourceLocation(tag.removePrefix("#")))) }
            val idTarget = fluid?.let { IdTarget(ForgeRegistries.FLUIDS.getValue(ResourceLocation(fluid)) ?: throw JsonParseException("Couldn't find fluid with id $fluid")) }
            val targets = CompoundTarget(buildSet {
                if (tagTarget != null) add(tagTarget)
                if (idTarget != null) add(idTarget)
            })

            val data = GsonHelper.getAsJsonObject(json, "data", JsonObject())!!
            val parsedData = parsedTrait.loadAdditionalData(id, data)

            return AttachedFluidTraitImpl(parsedTrait, targets, parsedData)
        }

        fun deserializeFromNetwork(buffer: FriendlyByteBuf): AttachedFluidTrait<*> {
            val traitId = buffer.readResourceLocation()
            val trait = NTechRegistries.FLUID_TRAITS.get().getValue(traitId) 
                ?: throw IllegalStateException("Received unknown fluid trait $traitId from server")
            
            return AttachedFluidTraitImpl(
                trait,
                decodeTargetFromNetworkString(buffer.readUtf()),
                buffer.readNbt() ?: CompoundTag()
            )
        }

        private fun decodeTargetFromNetworkString(netString: String): AttachedFluidTrait.FluidTarget = when {
            netString.startsWith('#') -> TagTarget(TagKey.create(ForgeRegistries.FLUIDS.registryKey, ResourceLocation(netString.removePrefix("#"))))
            netString.startsWith('[') -> CompoundTarget(netString.removeSurrounding("[", "]").split(';').map(::decodeTargetFromNetworkString))
            else -> IdTarget(ForgeRegistries.FLUIDS.getValue(ResourceLocation(netString)) ?: throw IllegalArgumentException("Couldn't find fluid with id $netString"))
        }
    }

    private data class IdTarget(private val fluid: Fluid) : AttachedFluidTrait.FluidTarget {
        override fun test(fluid: FluidStack) = fluid.fluid == this.fluid

        override fun toString() = ForgeRegistries.FLUIDS.getKey(fluid)!!.toString()
    }

    private data class TagTarget(private val tag: TagKey<Fluid>) : AttachedFluidTrait.FluidTarget {
        @Suppress("DEPRECATION")
        override fun test(fluid: FluidStack) = fluid.fluid.`is`(tag)

        override fun toString() = "#${tag.location}"
    }

    private data class CompoundTarget(private val targets: Collection<AttachedFluidTrait.FluidTarget>) : AttachedFluidTrait.FluidTarget {
        override fun test(fluid: FluidStack) = targets.any { it.test(fluid) }

        override fun toString() = targets.joinToString(prefix = "[", postfix = "]", separator = ";")
    }
}

