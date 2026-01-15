@file:Suppress("DEPRECATION")

package dev.ntmr.nucleartech

import dev.ntmr.nucleartech.client.model.RandomModelLoader
import dev.ntmr.nucleartech.client.rendering.*
import dev.ntmr.nucleartech.client.screen.*
import dev.ntmr.nucleartech.client.screen.rbmk.*
import dev.ntmr.nucleartech.content.*
import dev.ntmr.nucleartech.content.block.BlockTints
import dev.ntmr.nucleartech.content.block.entity.renderer.*
import dev.ntmr.nucleartech.content.block.entity.renderer.rbmk.*
import dev.ntmr.nucleartech.content.entity.renderer.*
import dev.ntmr.nucleartech.content.item.*
import dev.ntmr.nucleartech.content.particle.*
import dev.ntmr.nucleartech.extensions.getAverageColor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.client.renderer.item.ItemPropertyFunction
import net.minecraft.util.Mth
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        NuclearTech.LOGGER.debug("Registering screens")
        event.enqueueWork {
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.anvilMenu.get(), ::AnvilScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.assemblerMenu.get(), ::AssemblerScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.blastFurnaceMenu.get(), ::BlastFurnaceScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.chemPlantMenu.get(), ::ChemPlantScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.centrifugeMenu.get(), ::CentrifugeScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.combustionGeneratorMenu.get(), ::CombustionGeneratorScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.dieselGeneratorMenu.get(), ::DieselGeneratorScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.batteryBoxMenu.get(), ::BatteryBoxScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.electricFurnaceMenu.get(), ::ElectricFurnaceScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.electricPressMenu.get(), ::ElectricPressScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.fractionatingColumnMenu.get(), ::FractionatingColumnScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.shredderMenu.get(), ::ShredderScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.fatManMenu.get(), ::FatManScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.launchPadMenu.get(), ::LaunchPadScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.littleBoyMenu.get(), ::LittleBoyScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.oilWellMenu.get(), ::OilWellScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.rbmkAutoControlMenu.get(), ::RBMKAutoControlScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.rbmkBoilerMenu.get(), ::RBMKBoilerScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.rbmkConsoleMenu.get(), ::RBMKConsoleScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.rbmkManualControlMenu.get(), ::RBMKManualControlScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.rbmkRodMenu.get(), ::RBMKRodScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.safeMenu.get(), ::SafeScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.shredderMenu.get(), ::ShredderScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.sirenMenu.get(), ::SirenScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.steamPressMenu.get(), ::SteamPressScreen)
            net.minecraft.client.gui.screens.MenuScreens.register(NTechMenus.turbineMenu.get(), ::TurbineScreen)
        }
        
        NuclearTech.LOGGER.debug("Setting HUD overlays")
        Overlays.registerOverlays()

        NuclearTech.LOGGER.debug("Registering item properties")
        event.enqueueWork {
            run {
                @Suppress("DEPRECATION") val pelletDepletion = ItemPropertyFunction { stack, _, _, _ -> val item = stack.item; if (item is RBMKPelletItem) item.getDepletion(stack).toFloat() else 0F }
                @Suppress("DEPRECATION") val pelletXenon = ItemPropertyFunction { stack, _, _, _ -> val item = stack.item; if (item is RBMKPelletItem && item.canHaveXenon && item.hasXenon(stack)) 1F else 0F }

                for (itemRegistryObject in NTechItems.forgeRegistry.entries) {
                    val item = itemRegistryObject.get()
                    if (item is RBMKPelletItem) {
                        ItemProperties.register(item, ntm("depletion"), pelletDepletion)
                        if (item.canHaveXenon)
                            ItemProperties.register(item, ntm("xenon"), pelletXenon)
                    }
                }
            }
            ItemProperties.register(NTechItems.assemblyTemplate.get(), ntm("shift")) { _, _, _, _ -> if (Screen.hasShiftDown()) 1F else 0F }
            ItemProperties.register(NTechItems.chemTemplate.get(), ntm("shift")) { _, _, _, _ -> if (Screen.hasShiftDown()) 1F else 0F }
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        NuclearTech.LOGGER.debug("Registering BERs")
        with(event) {
            registerBlockEntityRenderer(NTechBlockEntities.assemblerBlockEntityType.get(), ::AssemblerRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.chemPlantBlockEntityType.get(), ::ChemPlantRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.centrifugeBlockEntityType.get(), ::CentrifugeRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.fatManBlockEntityType.get(), ::FatManRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.largeCoolingTowerBlockEntityType.get(), ::LargeCoolingTowerRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.launchPadBlockEntityType.get(), ::LaunchPadRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.littleBoyBlockEntityType.get(), ::LittleBoyRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.oilDerrickBlockEntityType.get(), ::OilDerrickRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.pumpjackBlockEntityType.get(), ::PumpjackRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkAbsorberBlockEntityType.get(), ::RBMKAbsorberRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkAutoControlBlockEntityType.get(), ::RBMKAutoControlRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkBlankBlockEntityType.get(), ::RBMKBlankRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkBoilerBlockEntityType.get(), ::RBMKBoilerRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkConsoleBlockEntityType.get(), ::RBMKConsoleRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkManualControlBlockEntityType.get(), ::RBMKManualControlRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkModeratedControlBlockEntityType.get(), ::RBMKModeratedControlRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkModeratedReaSimRodBlockEntityType.get(), ::RBMKModeratedReaSimRodRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkModeratedRodBlockEntityType.get(), ::RBMKModeratedRodRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkModeratorBlockEntityType.get(), ::RBMKModeratorRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkReaSimRodBlockEntityType.get(), ::RBMKReaSimRodRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkReflectorBlockEntityType.get(), ::RBMKReflectorRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.rbmkRodBlockEntityType.get(), ::RBMKRodRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.smallCoolingTowerBlockEntityType.get(), ::SmallCoolingTowerRenderer)
            registerBlockEntityRenderer(NTechBlockEntities.steamPressHeadBlockEntityType.get(), ::SteamPressRenderer)
        }

        NuclearTech.LOGGER.debug("Registering Entity Renderers")
        with(event) {
            registerEntityRenderer(NTechEntities.nuclearExplosion.get(), ::NoopRenderer)
            registerEntityRenderer(NTechEntities.mushroomCloud.get(), ::MushroomCloudRenderer)
            registerEntityRenderer(NTechEntities.falloutRain.get(), ::NoopRenderer)
            registerEntityRenderer(NTechEntities.nuclearCreeper.get(), ::NuclearCreeperRenderer)

            registerEntityRenderer(NTechEntities.missileHE.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileIncendiary.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileCluster.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileBunkerBuster.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileHEStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileIncendiaryStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileClusterStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileBunkerBusterStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileBurst.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileInferno.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileRain.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileDrill.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileNuclear.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(NTechEntities.missileTectonic.get(), ::SimpleMissileRenderer)

            registerEntityRenderer(NTechEntities.oilSpill.get(), ::NoopRenderer)

            registerEntityRenderer(NTechEntities.clusterFragment.get(), ::ThrownItemRenderer)
            registerEntityRenderer(NTechEntities.shrapnel.get(), ::ShrapnelRenderer)
            registerEntityRenderer(NTechEntities.volcanicShrapnel.get(), ::VolcanicShrapnelRenderer)

            registerEntityRenderer(NTechEntities.rbmkDebris.get(), ::RBMKDebrisRenderer)

            registerEntityRenderer(NTechEntities.meteor.get(), ::MeteorRenderer)

            registerEntityRenderer(NTechEntities.wasteItemEntity.get(), ::ItemEntityRenderer)

            registerEntityRenderer(NTechEntities.bullet.get(), ::NoopRenderer)
            
            registerEntityRenderer(NTechEntities.gunTurret.get(), ::NoopRenderer)
            registerEntityRenderer(NTechEntities.missileTurret.get(), ::NoopRenderer)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        with(event) {
            registerLayerDefinition(NuclearModelLayers.METEOR, MeteorRenderer.MeteorModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.RUBBLE, RubbleParticle.RubbleModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.SHRAPNEL, ShrapnelRenderer.ShrapnelModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.STEAM_PRESS, SteamPressRenderer::createLayerDefinition)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        NuclearTech.LOGGER.debug("Registering block colors")
        event.register(
            { _, level, pos, _ ->
                if (level == null || pos == null) -1
                else level.getBlockTint(pos, BlockTints.FLUID_DUCT_COLOR_RESOLVER)
            }, NTechBlocks.coatedUniversalFluidDuct.get()
        )
    }

    @SubscribeEvent @JvmStatic
    fun registerItemColors(event: RegisterColorHandlersEvent.Item) {
        NuclearTech.LOGGER.debug("Registering item colors")
        event.register({ stack, layer ->
            if (layer == 0) -1 else {
                val fluid = FluidIdentifierItem.getFluid(stack)
                if (fluid.isSame(Fluids.EMPTY)) return@register -1
                try {
                    val clientExtensions = net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions.of(fluid)
                    val tintColor = clientExtensions.tintColor
                    if (tintColor != -1) tintColor else {
                        val texture = clientExtensions.stillTexture
                        if (texture != null) {
                            val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture)
                            sprite.getAverageColor(0, 0, 0, 15, 15) and 0xFFFFFF
                        } else -1
                    }
                } catch (ex: Exception) {
                    NuclearTech.LOGGER.error("Couldn't sample fluid texture for tinting fluid identifier", ex)
                    -1
                }
            }
        }, NTechItems.fluidIdentifier.get())
        for (bombKit in BombKitItem.allKits) {
            event.register({ _, layer -> if (layer == 0) -1 else bombKit.color }, bombKit)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerGeometryLoaders(event: ModelEvent.RegisterGeometryLoaders) {
        NuclearTech.LOGGER.debug("Registering custom model loaders")
        event.register("random", RandomModelLoader)
    }

    @SubscribeEvent @JvmStatic
    fun onRegisterAdditionalModels(event: ModelEvent.RegisterAdditional) {
        NuclearTech.LOGGER.debug("Registering additional models")
        SpecialModels.registerAdditionalModels(event)
    }

    @SubscribeEvent @JvmStatic
    fun onModelsLoaded(event: ModelEvent.ModifyBakingResult) {
        NuclearTech.LOGGER.debug("Baking additional models")
        SpecialModels.onModelBakingCompleted(event)
        // ChemPlantTemplateItem.generateTemplateIcons requires updated API
    }

    @SubscribeEvent @JvmStatic
    fun registerResourceReloadListeners(event: RegisterClientReloadListenersEvent) {
        event.registerReloadListener(SpecialModels)

        val capeTextures = NTechCapes.CapeTextureManager(Minecraft.getInstance().textureManager)
        NTechCapes.capeTextures = capeTextures
        event.registerReloadListener(capeTextures)
    }

    @SubscribeEvent @JvmStatic
    fun registerParticleProviders(event: RegisterParticleProvidersEvent) {
        NuclearTech.LOGGER.debug("Registering particle providers")
        event.registerSpriteSet(NTechParticles.CONTRAIL.get(), ContrailParticle::Provider)
        event.registerSpriteSet(NTechParticles.COOLING_TOWER_CLOUD.get(), CoolingTowerCloudParticle::Provider)
        event.registerSpriteSet(NTechParticles.MINI_NUKE_CLOUD.get(), MiniNukeCloudParticle::Provider)
        event.registerSpriteSet(NTechParticles.MINI_NUKE_CLOUD_BALEFIRE.get(), MiniNukeCloudParticle::Provider)
        event.registerSpriteSet(NTechParticles.MINI_NUKE_FLASH.get(), MiniNukeFlashParticle::Provider)
        event.registerSpriteSet(NTechParticles.OIL_SPILL.get(), OilSpillParticle::Provider)
        event.registerSpriteSet(NTechParticles.RBMK_FIRE.get(), RBMKFireParticle::Provider)
        event.registerSpriteSet(NTechParticles.RBMK_MUSH.get(), RBMKMushParticle::Provider)
        event.registerSpriteSet(NTechParticles.ROCKET_FLAME.get(), RocketFlameParticle::Provider)
        event.registerSpriteSet(NTechParticles.SHOCKWAVE.get(), ShockwaveParticle::Provider)
        event.registerSpriteSet(NTechParticles.SMOKE.get(), SmokeParticle::Provider)
        event.registerSpriteSet(NTechParticles.VOLCANO_SMOKE.get(), VolcanoSmokeParticle::Provider)
        // Rubble particle uses special registration
        event.registerSpecial(NTechParticles.RUBBLE.get(), RubbleParticle.Provider())
    }

    @Mod.EventBusSubscriber(modid = MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
    object Shaders {
        lateinit var rendertypeMushroomCloudShader: ShaderInstance private set

        @SubscribeEvent @JvmStatic
        fun registerShaders(event: RegisterShadersEvent) {
            event.registerShader(ShaderInstance(event.resourceProvider, ntm("rendertype_mushroom_cloud"), NuclearRenderTypes.VertexFormats.POSITION_COLOR_TEX_NORMAL)) { rendertypeMushroomCloudShader = it }
        }
    }
}
