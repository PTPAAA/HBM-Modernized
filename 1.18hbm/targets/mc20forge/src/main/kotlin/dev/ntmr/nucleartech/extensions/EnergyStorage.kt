package dev.ntmr.nucleartech.extensions

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.math.formatStorageFilling
import dev.ntmr.nucleartech.system.energy.EnergyUnit
import net.minecraft.network.chat.MutableComponent
import net.minecraftforge.energy.EnergyStorage
import net.minecraft.network.chat.Component

fun EnergyStorage.getTooltip() = listOf(
    LangKeys.ENERGY.get(),
    Component.literal(EnergyUnit.UnitType.HBM.formatStorageFilling(energyStored, maxEnergyStored))
)
