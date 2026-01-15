/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.fluid.trait

import dev.ntmr.nucleartech.api.fluid.trait.AttachedFluidTrait
import dev.ntmr.nucleartech.api.fluid.trait.FluidTrait
import dev.ntmr.nucleartech.content.NTechRegistries
import net.minecraft.Util
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

open class FluidTraitImpl(protected val styleModifier: Style) : FluidTrait {
    private var _descriptionId: String? = null

    override val descriptionId: String
        get() {
            if (_descriptionId == null)
                _descriptionId = Util.makeDescriptionId("fluid_trait", NTechRegistries.FLUID_TRAITS.get().getKey(this))
            return _descriptionId!!
        }

    override fun getName(data: AttachedFluidTrait<*>): MutableComponent = Component.translatable(descriptionId).withStyle(styleModifier)
}
