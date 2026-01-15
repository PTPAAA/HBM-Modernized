/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.menu.AnvilMenu
import dev.ntmr.nucleartech.content.menu.AssemblerMenu
import dev.ntmr.nucleartech.content.menu.BlastFurnaceMenu
import dev.ntmr.nucleartech.content.menu.CentrifugeMenu
import dev.ntmr.nucleartech.content.menu.ChemPlantMenu
import dev.ntmr.nucleartech.content.menu.CombustionGeneratorMenu
import dev.ntmr.nucleartech.content.menu.ElectricFurnaceMenu
import dev.ntmr.nucleartech.content.menu.FatManMenu
import dev.ntmr.nucleartech.content.menu.LaunchPadMenu
import dev.ntmr.nucleartech.content.menu.LittleBoyMenu
import dev.ntmr.nucleartech.content.menu.OilWellMenu
import dev.ntmr.nucleartech.content.menu.PressMenu
import dev.ntmr.nucleartech.content.menu.SafeMenu
import dev.ntmr.nucleartech.content.menu.ShredderMenu
import dev.ntmr.nucleartech.content.menu.SirenMenu
import dev.ntmr.nucleartech.content.menu.TurbineMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKAutoControlMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKBoilerMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKConsoleMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKManualControlMenu
import dev.ntmr.nucleartech.content.menu.rbmk.RBMKRodMenu
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object NTechMenus : NTechRegistry<MenuType<*>> {
    override val forgeRegistry: DeferredRegister<MenuType<*>> = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID)

    val safeMenu = register("safe") { IForgeMenuType.create(SafeMenu::fromNetwork) }
    val sirenMenu = register("siren") { IForgeMenuType.create(SirenMenu::fromNetwork) }
    val anvilMenu = register("anvil") { IForgeMenuType.create(AnvilMenu::fromNetwork) }
    val steamPressMenu = register("steam_press") { IForgeMenuType.create(PressMenu::fromNetwork) }
    val blastFurnaceMenu = register("blast_furnace") { IForgeMenuType.create(BlastFurnaceMenu::fromNetwork) }
    val combustionGeneratorMenu = register("combustion_generator") { IForgeMenuType.create(CombustionGeneratorMenu::fromNetwork) }
    val dieselGeneratorMenu = register("diesel_generator") { IForgeMenuType.create(DieselGeneratorMenu::fromNetwork) }
    val batteryBoxMenu = register("battery_box") { IForgeMenuType.create(BatteryBoxMenu::fromNetwork) }
    val electricFurnaceMenu = register("electric_furnace") { IForgeMenuType.create(ElectricFurnaceMenu::fromNetwork) }
    val electricPressMenu = register("electric_press") { IForgeMenuType.create(ElectricPressMenu::fromNetwork) }
    val fractionatingColumnMenu = register("fractionating_column") { IForgeMenuType.create(FractionatingColumnMenu::fromNetwork) }
    val shredderMenu = register("shredder") { IForgeMenuType.create(ShredderMenu::fromNetwork) }
    val assemblerMenu = register("assembler") { IForgeMenuType.create(AssemblerMenu::fromNetwork) }
    val chemPlantMenu = register("chem_plant") { IForgeMenuType.create(ChemPlantMenu::fromNetwork) }
    val turbineMenu = register("turbine") { IForgeMenuType.create(TurbineMenu::fromNetwork) }
    val oilWellMenu = register("oil_well") { IForgeMenuType.create(OilWellMenu::fromNetwork) }
    val centrifugeMenu = register("centrifuge") { IForgeMenuType.create(CentrifugeMenu::fromNetwork) }
    val rbmkRodMenu = register("rbmk_rod") { IForgeMenuType.create(RBMKRodMenu::fromNetwork) }
    val rbmkBoilerMenu = register("rbmk_boiler") { IForgeMenuType.create(RBMKBoilerMenu::fromNetwork) }
    val rbmkManualControlMenu = register("rbmk_manual_control") { IForgeMenuType.create(RBMKManualControlMenu::fromNetwork) }
    val rbmkAutoControlMenu = register("rbmk_auto_control") { IForgeMenuType.create(RBMKAutoControlMenu::fromNetwork) }
    val rbmkConsoleMenu = register("rbmk_console") { IForgeMenuType.create(RBMKConsoleMenu::fromNetwork) }
    val littleBoyMenu = register("little_boy") { IForgeMenuType.create(LittleBoyMenu::fromNetwork) }
    val fatManMenu = register("fat_man") { IForgeMenuType.create(FatManMenu::fromNetwork) }
    val launchPadMenu = register("launch_pad") { IForgeMenuType.create(LaunchPadMenu::fromNetwork) }
}
