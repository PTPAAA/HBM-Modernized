/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.ntm
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

enum class CreativeTabs(val tab: CreativeModeTab) {
    Parts(createTab("parts", NTechItems::uraniumIngot)),
    Items(createTab("items", NTechItems::stoneFlatStamp)), // TODO
    Templates(createTab("templates", NTechItems::machineTemplateFolder)),
    Blocks(createTab("blocks", NTechBlockItems::uraniumOre)),
    Machines(createTab("machines", NTechBlockItems::safe)), // TODO
    Bombs(createTab("bombs", NTechBlockItems::fatMan).setBackgroundImage(ntm("textures/gui/creative_inventory/tab_nuke.png"))),
    Rocketry(createTab("rocketry", NTechItems::nuclearMissile)),
    Consumables(createTab("consumables", NTechItems::oilDetector)),
    Miscellaneous(createTab("miscellaneous", NTechItems::nuclearCreeperSpawnEgg)),
}

private fun createTab(name: String, iconItem: Supplier<out RegistryObject<out Item>>) =
    object : CreativeModeTab("${MODID}_$name") {
        override fun makeIcon() = ItemStack(iconItem.get().get())
    }
