
/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block

import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour

class VolcanicGemDropExperienceBlock(properties: BlockBehaviour.Properties) : DropExperienceBlock(properties, ConstantInt.of(1))
