/*
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech

import com.mojang.logging.LogUtils
import dev.ntmr.nucleartech.system.config.NuclearConfig
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.content.NTechRegistries
import dev.ntmr.nucleartech.packets.NuclearPacketHandler
import dev.ntmr.nucleartech.packets.filter.NTechNetFilters
import dev.ntmr.nucleartech.plugin.PluginEvents
import dev.ntmr.nucleartech.system.hazard.HazardRegistry
import dev.ntmr.nucleartech.system.world.ChunkLoadingValidationCallback
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.world.ForgeChunkManager
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.GenericEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.VersionChecker
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.forgespi.language.IModInfo
import kotlin.jvm.optionals.getOrNull

@Mod(MODID)
class NuclearTech {

    init {
        bus = FMLJavaModLoadingContext.get().modEventBus
        submitListener(::commonSetup)
        submitListener(::serverSetup)
        NTechRegistries.register(bus)
        NuclearConfig.registerConfigs(ModLoadingContext.get().activeContainer)
        MinecraftForge.EVENT_BUS.register(this)
        NuclearPacketHandler.initialize()
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("Hello World!")

        if (isSnapshot) {
            LOGGER.warn("Running a bootleg snapshot version!")
        }

        PluginEvents.init()
        HazardRegistry.registerItems()
        NTechFluids.registerDispenserBehaviour()
        NTechNetFilters.performTheFunny()

        event.enqueueWork {
            LOGGER.debug("Setting forced chunk loading callback")
            ForgeChunkManager.setForcedChunkLoadingCallback(MODID, ChunkLoadingValidationCallback)
        }
    }

    private fun serverSetup(event: FMLDedicatedServerSetupEvent) {
        val versionCheckResult = NuclearTech.versionCheckResult
        when (versionCheckResult?.status) {
            VersionChecker.Status.PENDING, VersionChecker.Status.FAILED, VersionChecker.Status.UP_TO_DATE, null -> {}
            VersionChecker.Status.BETA, VersionChecker.Status.AHEAD -> {
                NuclearTech.LOGGER.info(if (NuclearTech.isSnapshot) "Running on the bleeding edge!" else "Running on the cutting edge of beta!")
            }
            VersionChecker.Status.OUTDATED, VersionChecker.Status.BETA_OUTDATED -> {
                val updateMessage = StringBuilder()
                updateMessage.appendLine("Hello server administrator! There's a newer version of NTM available: ${versionCheckResult.target}")
                if (versionCheckResult.changes.isNotEmpty()) updateMessage.appendLine("List of changes:")
                for (change in versionCheckResult.changes.values.flatMap { it.split("\r\n", "\n", "\r") }) updateMessage.appendLine(change.prependIndent())
                NuclearTech.LOGGER.info(updateMessage.toString())
            }
        }
    }

    companion object {
        @PublishedApi
        internal lateinit var bus: IEventBus

        inline fun <reified T : Event> submitListener(noinline listener: (T) -> Unit) {
            bus.addListener(EventPriority.NORMAL, false, T::class.java, listener)
        }

        inline fun <reified T : Event> submitListener(priority: EventPriority, noinline listener: (T) -> Unit) {
            bus.addListener(priority, false, T::class.java, listener)
        }

        inline fun <reified F, reified T : GenericEvent<F>> submitGenericListener(noinline listener: (T) -> Unit) {
            bus.addGenericListener(F::class.java, EventPriority.NORMAL, false, T::class.java, listener)
        }

        val LOGGER = LogUtils.getLogger()

        val polaroidID: Int get() = NTechItems.polaroid.get().id + 1
        val polaroidBroken: Boolean get() = polaroidID == 11

        val modInfo: IModInfo? by lazy { ModList.get().getModContainerById(MODID).getOrNull()?.modInfo }
        val currentVersion: String? by lazy { modInfo?.version?.toString() }
        val isSnapshot: Boolean by lazy { currentVersion?.contains("snapshot") == true }
        val versionCheckResult: VersionChecker.CheckResult? by lazy { modInfo?.let{ VersionChecker.getResult(it) }}
    }
}
