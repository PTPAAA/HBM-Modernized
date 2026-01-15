package dev.ntmr.nucleartech.extensions

import dev.ntmr.nucleartech.system.hazard.HazmatValues
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

fun Item.isHazmat(): Boolean = HazmatValues.getResistance(this) > 0F

fun Item.isGasMask(): Boolean = this is dev.ntmr.nucleartech.content.item.HazmatMaskItem

fun ItemStack.isHazmat(): Boolean = item.isHazmat()

fun ItemStack.isGasMask(): Boolean = item.isGasMask()

val Item.armorSlot: EquipmentSlot?
    get() = (this as? ArmorItem)?.equipmentSlot
