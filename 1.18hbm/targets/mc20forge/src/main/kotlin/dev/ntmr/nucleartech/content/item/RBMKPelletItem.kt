package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.TranslationKey
import dev.ntmr.nucleartech.extensions.*
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import kotlin.math.abs

open class RBMKPelletItem(properties: Properties, val fullName: TranslationKey, val canHaveXenon: Boolean = true) : NuclearWasteItem(properties) {


    fun setDepletion(stack: ItemStack, depletion: Int) {
        stack.orCreateTag.putInt("Depletion", depletion.coerceIn(0, 4))
    }

    fun getDepletion(stack: ItemStack) = abs(stack.tag?.getInt("Depletion") ?: 0) % 5

    fun setXenon(stack: ItemStack, xenon: Boolean) {
        stack.orCreateTag.putBoolean("Xenon", xenon)
    }

    fun hasXenon(stack: ItemStack) = stack.tag?.getBoolean("Xenon") == true

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        with(tooltip) {
            add(fullName.italic().gray())
            add(LangKeys.RBMK_PELLET_RECYCLE.italic().darkGray())

            when (getDepletion(stack)) {
                0 -> add(LangKeys.RBMK_PELLET_DEPLETION_NEW.gold())
                1 -> add(LangKeys.RBMK_PELLET_DEPLETION_SLIGHT.yellow())
                2 -> add(LangKeys.RBMK_PELLET_DEPLETION_MODERATE.green())
                3 -> add(LangKeys.RBMK_PELLET_DEPLETION_HIGH.darkGreen())
                4 -> add(LangKeys.RBMK_PELLET_DEPLETION_FULL.darkGray())
                else -> throw IllegalStateException("Invalid pellet depletion value")
            }

            if (hasXenon(stack))
                add(LangKeys.RBMK_PELLET_XENON_POISONED.darkPurple())
        }
    }
}
