package dev.ntmr.nucleartech.content.item.upgrades

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.block.entity.PowerSavingUpgradeableMachine
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import dev.ntmr.nucleartech.extensions.darkBlue

class PowerSavingUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<PowerSavingUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_POWER_SAVING.darkBlue()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is PowerSavingUpgradeableMachine

    override fun apply(machine: PowerSavingUpgradeableMachine) {
        machine.powerSavingUpgradeLevel = (machine.powerSavingUpgradeLevel + tier).coerceAtMost(machine.maxPowerSavingUpgradeLevel)
    }
}
