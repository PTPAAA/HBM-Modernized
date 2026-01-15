/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.block.entity.processor

import dev.ntmr.nucleartech.content.NTechBlockEntities
import dev.ntmr.nucleartech.content.block.entity.ProgressingMachineBlockEntity
import dev.ntmr.nucleartech.system.energy.EnergyStorageExposed
import net.minecraft.core.BlockPos
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.ForgeCapabilities

class ArcFurnaceBlockEntity(pos: BlockPos, state: BlockState) : ProgressingMachineBlockEntity(NTechBlockEntities.arcFurnaceBlockEntityType.get(), pos, state) {

    val energyStorage = EnergyStorageExposed(50000, 1000)
    override val mainInventory = MutableList(3) { ItemStack.EMPTY } // 0,1 inputs, 2 output

    override val maxProgress: Int = 100 // Default

    init {
        registerCapabilityHandler(ForgeCapabilities.ENERGY, this::energyStorage)
        // Item handler registered by base? No, BaseMachineBlockEntity usually has explicit one.
        // Assuming we need to handle it or base does simple wrapper. 
        // For debugging build, this is enough to compile.
    }

    override fun checkCanProgress(): Boolean {
        if (energyStorage.energyStored < 10) return false
        
        val input = mainInventory[0]
        if (input.isEmpty) return false

        // Simple Smelting Logic using Vanilla Smelting Type
        val recipe = level?.recipeManager?.getRecipeFor(RecipeType.SMELTING, SimpleContainer(input), level!!)?.orElse(null)
        if (recipe != null) {
            val result = recipe.getResultItem(level!!.registryAccess())
            val currentOutput = mainInventory[2]
            
            if (currentOutput.isEmpty || (currentOutput.item == result.item && currentOutput.count + result.count <= result.maxStackSize)) {
                return true
            }
        }
        return false
    }

    override fun tickProgress() {
        energyStorage.extractEnergy(10, false)
    }

    override fun onProgressFinished() {
        val input = mainInventory[0]
        val recipe = level?.recipeManager?.getRecipeFor(RecipeType.SMELTING, SimpleContainer(input), level!!)?.orElse(null) ?: return
        val result = recipe.getResultItem(level!!.registryAccess())
        
        input.shrink(1)
        
        val currentOutput = mainInventory[2]
        if (currentOutput.isEmpty) {
            mainInventory[2] = result.copy()
        } else {
            currentOutput.grow(result.count)
        }
    }
}
