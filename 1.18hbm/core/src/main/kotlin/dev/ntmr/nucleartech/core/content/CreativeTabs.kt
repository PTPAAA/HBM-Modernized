/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core.content

import dev.ntmr.nucleartech.core.MODID
import dev.ntmr.nucleartech.core.content.item.NTechItems
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.CreativeModeTab
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.Item
import dev.ntmr.sorcerer.generated.spells.main.net.minecraft.world.item.ItemStack
import dev.ntmr.sorcerer.generated.spells.main.net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

enum class CreativeTabs(val tab: CreativeModeTab) {
    Parts(createTab("parts", NTechItems::uraniumIngot)),
}

private fun createTab(name: String, iconItem: Supplier<out RegistryObject<out Item>>) =
    object : CreativeModeTab("${MODID}_$name") {
        override fun makeIcon() = ItemStack(iconItem.get().get())
    }
