package dev.ntmr.nucleartech.content.item

import dev.ntmr.nucleartech.client.rendering.Overlays
import dev.ntmr.nucleartech.ntm
import net.minecraft.client.model.HumanoidModel
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.IItemRenderProperties
import java.util.function.Consumer

class HazmatMaskItem(material: ArmorMaterial, slot: EquipmentSlot, bonus: FullSetBonus, properties: Properties) : FullSetBonusArmorItem(material, slot, bonus, properties) {
    private val maskBlurLocation = ntm("textures/misc/hazmat_mask_blur.png")

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(object : IItemRenderProperties {
            override fun getArmorModel(entityLiving: LivingEntity, itemStack: ItemStack, armorSlot: EquipmentSlot, _default: HumanoidModel<*>): HumanoidModel<*>? {
                // TODO
                return null
            }

            override fun renderHelmetOverlay(stack: ItemStack, player: Player, width: Int, height: Int, partialTick: Float) {
                Overlays.renderTextureOverlay(maskBlurLocation, 1F, width, height)
            }
        })
    }
}
