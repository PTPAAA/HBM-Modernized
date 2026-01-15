package dev.ntmr.nucleartech

import dev.ntmr.nucleartech.api.ModPlugin
import dev.ntmr.nucleartech.api.NuclearTechPlugin
import dev.ntmr.nucleartech.api.explosion.ExplosionAlgorithmRegistration
import dev.ntmr.nucleartech.api.explosion.ExplosionLargeParams
import dev.ntmr.nucleartech.api.explosion.NuclearExplosionMk4Params
import dev.ntmr.nucleartech.api.hazard.radiation.HazmatRegistry
import dev.ntmr.nucleartech.content.NTechArmorMaterials
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.content.entity.NukeExplosion
import dev.ntmr.nucleartech.system.explosion.ExplosionLarge
import net.minecraft.resources.ResourceLocation
import dev.ntmr.nucleartech.api.explosion.ExplosionAlgorithmRegistration.Defaults as DefaultExplosionAlgorithms
import net.minecraft.world.item.ArmorMaterials as VanillaArmorMaterials

@Suppress("unused")
@NuclearTechPlugin
class MainPlugin : ModPlugin {
    override val id = ResourceLocation(MODID, MODID)

    override fun registerExplosions(explosions: ExplosionAlgorithmRegistration) {
        explosions.register(DefaultExplosionAlgorithms.MK4, NuclearExplosionMk4Params::class.java, NukeExplosion.Companion)
        explosions.register(DefaultExplosionAlgorithms.EXPLOSION_LARGE, ExplosionLargeParams::class.java, ExplosionLarge)
    }

    override fun registerHazmatValues(hazmat: HazmatRegistry): Unit = with(hazmat) {
        registerMaterial(VanillaArmorMaterials.IRON, .0225F)
        registerMaterial(VanillaArmorMaterials.GOLD, .0225F)
        registerMaterial(VanillaArmorMaterials.NETHERITE, .08F)
        registerMaterial(NTechArmorMaterials.steel, .045F)
        registerMaterial(NTechArmorMaterials.titanium, .045F)
        registerMaterial(NTechArmorMaterials.advancedAlloy, .07F)
        registerArmorRepairItem(NTechItems.cobaltIngot, .125F)
        registerMaterial(NTechArmorMaterials.hazmat, .6F)
        registerMaterial(NTechArmorMaterials.advancedHazmat, 1F)
        registerMaterial(NTechArmorMaterials.reinforcedHazmat, 2F)
        registerMaterial(NTechArmorMaterials.paAAlloy, 1.7F)
        registerArmorRepairItem(NTechItems.starmetalIngot, 1F)
        registerMaterial(NTechArmorMaterials.combineSteel, 1.3F)
        registerMaterial(NTechArmorMaterials.schrabidium, 3F)
    }
}
