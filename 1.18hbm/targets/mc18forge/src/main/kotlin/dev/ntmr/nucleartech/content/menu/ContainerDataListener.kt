/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.menu

import dev.ntmr.nucleartech.content.menu.slots.data.NTechDataSlot
import net.minecraft.world.level.block.entity.BlockEntity

interface ContainerDataListener<T : BlockEntity> {
    fun dataChanged(menu: NTechContainerMenu<T>, data: NTechDataSlot.Data)
}
