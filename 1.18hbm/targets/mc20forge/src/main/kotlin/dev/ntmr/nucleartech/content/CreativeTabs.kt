/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-FileCopyrightText: 2015-2024 HbmMods (The Bobcat) <hbmmods@gmail.com>
SPDX-FileCopyrightText: 2017-2024 The Contributors of the Original Project
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object CreativeTabRegistry {
    val REGISTRY: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID)
    
    // Main Nuclear Tech tab - will contain all items
    val NUCLEAR_TECH = REGISTRY.register("nucleartech") {
        CreativeModeTab.builder()
            .icon { ItemStack(NTechItems.uraniumIngot.get()) }
            .title(Component.translatable("itemGroup.${MODID}"))
            .build()
    }
}

// Event handler to populate creative tab with all mod items
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object CreativeTabPopulator {
    @SubscribeEvent
    @JvmStatic
    fun onBuildCreativeTabContents(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == CreativeTabRegistry.NUCLEAR_TECH.key) {
            // Add all items from NTechItems
            NTechItems.forgeRegistry.entries.forEach { item ->
                event.accept(item.get())
            }
            // Add all block items from NTechBlockItems
            NTechBlockItems.forgeRegistry.entries.forEach { item ->

                event.accept(item.get())
            }
        }
    }
}

// For backwards compatibility with existing code that uses CreativeTabs enum
enum class CreativeTabs(val tab: RegistryObject<CreativeModeTab>) {
    Parts(CreativeTabRegistry.NUCLEAR_TECH),
    Items(CreativeTabRegistry.NUCLEAR_TECH),
    Templates(CreativeTabRegistry.NUCLEAR_TECH),
    Blocks(CreativeTabRegistry.NUCLEAR_TECH),
    Machines(CreativeTabRegistry.NUCLEAR_TECH),
    Bombs(CreativeTabRegistry.NUCLEAR_TECH),
    Rocketry(CreativeTabRegistry.NUCLEAR_TECH),
    Consumables(CreativeTabRegistry.NUCLEAR_TECH),
    Miscellaneous(CreativeTabRegistry.NUCLEAR_TECH);
}

// Extension function for backwards compatibility - no longer used for tab assignment
fun Item.Properties.tab(tab: CreativeTabs): Item.Properties {
    return this
}

// Utility function for backwards compatibility
fun registerToTab(tab: CreativeTabs, item: Supplier<out Item>) {
    // No-op - items are now added via BuildCreativeModeTabContentsEvent
}

