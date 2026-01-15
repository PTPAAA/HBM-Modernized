package dev.ntmr.nucleartech.content.item.upgrades

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.block.entity.AfterBurnUpgradeableMachine
import dev.ntmr.nucleartech.content.block.entity.UpgradeableMachine
import dev.ntmr.nucleartech.extensions.lightPurple

class AfterBurnUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<AfterBurnUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_AFTER_BURNER.lightPurple()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is AfterBurnUpgradeableMachine

    override fun apply(machine: AfterBurnUpgradeableMachine) {
        machine.afterBurnUpgradeLevel = (machine.afterBurnUpgradeLevel + tier).coerceAtMost(machine.maxAfterBurnUpgradeLevel)
    }
}
