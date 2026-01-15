/*
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = "nucleartech", name = "Nuclear Tech Mod", version = "TODO") // TODO
class NuclearTech {
    @EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        event.modLog.info("Hello World!")
    }
}
