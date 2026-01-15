/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechMobEffects : NTechRegistry<MobEffect> {
    override val forgeRegistry: DeferredRegister<MobEffect> = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID)

    val radiationResistance = register("radiation_resistance") {
        object : MobEffect(MobEffectCategory.BENEFICIAL, 0x55FF55) { }
            .addAttributeModifier(NTechAttributes.RADIATION_RESISTANCE.get(), "55C66794-C7F3-4C3D-94D5-02196024E480", 2.0, AttributeModifier.Operation.ADDITION)
    }
}
