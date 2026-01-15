package dev.ntmr.nucleartech.extensions

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.math.formatStorageFilling
import dev.ntmr.nucleartech.system.energy.EnergyUnit
import net.minecraft.network.chat.TextComponent
import net.minecraftforge.energy.EnergyStorage

fun EnergyStorage.getTooltip() = listOf(
    LangKeys.ENERGY.get(),
    TextComponent(EnergyUnit.UnitType.HBM.formatStorageFilling(energyStored, maxEnergyStored))
)
