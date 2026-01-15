package dev.ntmr.nucleartech.plugin

import dev.ntmr.nucleartech.api.ModPlugin
import dev.ntmr.nucleartech.api.NuclearTechRuntime
import dev.ntmr.nucleartech.system.explosion.Explosions
import com.mojang.logging.LogUtils
import dev.ntmr.nucleartech.system.hazard.HazmatValues

object PluginEvents {
    private val LOGGER = LogUtils.getLogger()
    private lateinit var plugins: List<ModPlugin>

    fun init() {
        LOGGER.debug("Getting NTM plugins list...")
        plugins = ModPluginLoader.getModPlugins()
        registerExplosionAlgorithms()
        registerHazmatProtectionValues()
        sendRuntime(NtmRuntimeImpl())
    }

    private fun registerExplosionAlgorithms() {
        LOGGER.debug("Registering explosion algorithms...")
        for (plugin in plugins) plugin.registerExplosions(Explosions)
    }

    private fun registerHazmatProtectionValues() {
        LOGGER.debug("Registering hazmat protection values...")
        for (plugin in plugins) plugin.registerHazmatValues(HazmatValues)
    }

    private fun sendRuntime(runtime: NuclearTechRuntime) {
        LOGGER.debug("Sending runtime instance to plugins...")
        for (plugin in plugins) plugin.onRuntime(runtime)
    }
}
