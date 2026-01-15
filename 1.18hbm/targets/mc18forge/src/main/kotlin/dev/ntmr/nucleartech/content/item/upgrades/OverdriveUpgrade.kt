package dev.ntmr.nucleartech.content.item.upgrades

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.block.entity.OverdriveUpgradeableMachine
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import dev.ntmr.nucleartech.extensions.darkGray

class OverdriveUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<OverdriveUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_OVERDRIVE.darkGray()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is OverdriveUpgradeableMachine

    override fun apply(machine: OverdriveUpgradeableMachine) {
        machine.overdriveUpgradeLevel = (machine.overdriveUpgradeLevel + tier).coerceAtMost(machine.maxOverdriveUpgradeLevel)
    }
}
