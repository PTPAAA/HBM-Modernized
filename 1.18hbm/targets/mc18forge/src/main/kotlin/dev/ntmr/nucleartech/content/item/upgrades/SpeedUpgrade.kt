package dev.ntmr.nucleartech.content.item.upgrades

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.block.entity.SpeedUpgradeableMachine
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import dev.ntmr.nucleartech.extensions.darkRed

class SpeedUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<SpeedUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_SPEED.darkRed()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is SpeedUpgradeableMachine

    override fun apply(machine: SpeedUpgradeableMachine) {
        machine.speedUpgradeLevel = (machine.speedUpgradeLevel + tier).coerceAtMost(machine.maxSpeedUpgradeLevel)
    }
}
