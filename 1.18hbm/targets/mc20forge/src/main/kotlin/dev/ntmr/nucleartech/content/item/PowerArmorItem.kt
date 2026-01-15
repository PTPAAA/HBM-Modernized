package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.content.NTechArmorMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ICapabilityProvider

class PowerArmorItem(material: NTechArmorMaterials, type: ArmorItem.Type, bonus: FullSetBonus, properties: Properties) : FullSetBonusArmorItem(material, type, bonus, properties) {
    // Energy handling logic will go here (Forge Energy)
    // For now, basic implementation extending FullSetBonusArmorItem
    
    override fun isEnabled(stack: ItemStack): Boolean {
         // Check energy > 0
         return true 
    }
}
