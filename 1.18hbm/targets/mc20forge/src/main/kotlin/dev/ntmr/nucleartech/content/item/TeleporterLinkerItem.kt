/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.content.block.entity.machine.TeleporterBlockEntity
import dev.ntmr.nucleartech.content.block.machine.TeleporterBlock
import dev.ntmr.nucleartech.extensions.*
import dev.ntmr.nucleartech.math.component1
import dev.ntmr.nucleartech.math.component2
import dev.ntmr.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class TeleporterLinkerItem(properties: Properties) : TooltipItem(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL
        val level = context.level
        val clickedPos = context.clickedPos
        val blockState = level.getBlockState(clickedPos)
        val block = blockState.block
        val item = context.itemInHand
        val tags = item.orCreateTag

        if (block is TeleporterBlock) {
            if (player.isShiftKeyDown) {
                // Save Position
                setTarget(tags, clickedPos)
                if (level.isClientSide) player.sendSystemMessage(Component.translatable("message.nucleartech.teleporter_linker.saved", clickedPos.toShortString()).yellow())
                return InteractionResult.sidedSuccess(level.isClientSide)
            } else {
                // Set Target
                if (hasTags(tags)) {
                    val targetPos = getTarget(tags)
                    val be = level.getBlockEntity(clickedPos)
                    if (be is TeleporterBlockEntity) {
                        be.targetX = targetPos.x
                        be.targetY = targetPos.y
                        be.targetZ = targetPos.z
                        be.hasTarget = true
                        be.setChanged()
                        if (level.isClientSide) player.sendSystemMessage(Component.translatable("message.nucleartech.teleporter_linker.linked", targetPos.toShortString()).green())
                        return InteractionResult.sidedSuccess(level.isClientSide)
                    }
                } else {
                    if (level.isClientSide) player.sendSystemMessage(Component.translatable("message.nucleartech.teleporter_linker.no_target").red())
                    return InteractionResult.FAIL
                }
            }
        }
        
        return InteractionResult.PASS
    }

    private fun hasTags(tag: CompoundTag) = tag.contains("TargetX", 3) && tag.contains("TargetY", 3) && tag.contains("TargetZ", 3)
    private fun getTarget(tag: CompoundTag) = BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"))
    private fun setTarget(tag: CompoundTag, pos: BlockPos) {
        tag.putInt("TargetX", pos.x)
        tag.putInt("TargetY", pos.y)
        tag.putInt("TargetZ", pos.z)
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(stack, level, tooltip, flag)

        tooltip += if (!stack.hasTag() || !hasTags(stack.tag!!))
            Component.translatable("tooltip.nucleartech.teleporter_linker.no_target").darkRed()
        else {
            val (x, y, z) = getTarget(stack.tag!!)
            Component.translatable("tooltip.nucleartech.teleporter_linker.target", x, y, z).gray()
        }
    }
}
