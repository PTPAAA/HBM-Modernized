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
import dev.ntmr.nucleartech.content.recipe.anvil.AnvilConstructingRecipe
import dev.ntmr.nucleartech.extensions.getAverageColor
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.client.renderer.item.ItemPropertyFunction
import net.minecraft.client.searchtree.ReloadableSearchTree
import net.minecraft.util.Mth
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.*
import net.minecraftforge.client.model.ModelLoaderRegistry
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.stream.Stream

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent(priority = EventPriority.LOWEST) // so that the actual container types get registered before this
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<MenuType<*>>) {
        NuclearTech.LOGGER.debug("Registering screens")
        MenuScreens.register(NTechMenus.anvilMenu.get(), ::AnvilScreen)
        MenuScreens.register(NTechMenus.assemblerMenu.get(), ::AssemblerScreen)
        MenuScreens.register(NTechMenus.blastFurnaceMenu.get(), ::BlastFurnaceScreen)
        MenuScreens.register(NTechMenus.chemPlantMenu.get(), ::ChemPlantScreen)
        MenuScreens.register(NTechMenus.centrifugeMenu.get(), ::CentrifugeScreen)
        MenuScreens.register(NTechMenus.combustionGeneratorMenu.get(), ::CombustionGeneratorScreen)
        MenuScreens.register(NTechMenus.electricFurnaceMenu.get(), ::ElectricFurnaceScreen)
        MenuScreens.register(NTechMenus.fatManMenu.get(), ::FatManScreen)
        MenuScreens.register(NTechMenus.launchPadMenu.get(), ::LaunchPadScreen)
        MenuScreens.register(NTechMenus.littleBoyMenu.get(), ::LittleBoyScreen)
        MenuScreens.register(NTechMenus.oilWellMenu.get(), ::OilWellScreen)
        MenuScreens.register(NTechMenus.rbmkAutoControlMenu.get(), ::RBMKAutoControlScreen)
        MenuScreens.register(NTechMenus.rbmkBoilerMenu.get(), ::RBMKBoilerScreen)
        MenuScreens.register(NTechMenus.rbmkConsoleMenu.get(), ::RBMKConsoleScreen)
        MenuScreens.register(NTechMenus.rbmkManualControlMenu.get(), ::RBMKManualControlScreen)
        MenuScreens.register(NTechMenus.rbmkRodMenu.get(), ::RBMKRodScreen)
        MenuScreens.register(NTechMenus.safeMenu.get(), ::SafeScreen)
        MenuScreens.register(NTechMenus.shredderMenu.get(), ::ShredderScreen)
        MenuScreens.register(NTechMenus.sirenMenu.get(), ::SirenScreen)
        MenuScreens.register(NTechMenus.steamPressMenu.get(), ::SteamPressScreen)
        MenuScreens.register(NTechMenus.turbineMenu.get(), ::TurbineScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        NuclearTech.LOGGER.debug("Creating search trees")
        val templateFolderSearchTree = ReloadableSearchTree<ItemStack>({
            it.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip ->
                ChatFormatting.stripFormatting(tooltip.string)!!.trim()
            }.stream()
        }) { Stream.of(ForgeRegistries.ITEMS.getKey(it.item)) }
        val anvilConstructingRecipeSearchTree = ReloadableSearchTree<AnvilConstructingRecipe>({
            val results = it.results.map(AnvilConstructingRecipe.ConstructingResult::stack).flatMap { stack -> stack.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip -> ChatFormatting.stripFormatting(tooltip.string)!!.trim() }}

            if (it.results.size > 1 && it.ingredientsList.size == 1) {
                val recyclingSearch = results + it.ingredientsList.single().items.flatMap { stack -> stack.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip -> ChatFormatting.stripFormatting(tooltip.string)!!.trim() }}
                recyclingSearch.stream()
            } else results.stream()
        }) { it.results.map(AnvilConstructingRecipe.ConstructingResult::stack).map { stack -> ForgeRegistries.ITEMS.getKey(stack.item) }.stream() }

        Minecraft.getInstance().searchTreeManager.apply {
            register(UseTemplateFolderScreen.searchTree, templateFolderSearchTree)
            register(AnvilScreen.searchTree, anvilConstructingRecipeSearchTree)
        }

        NuclearTech.LOGGER.debug("Setting rendering layers")
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.steelGrate.get(), RenderType.cutoutMipped())
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.glowingMushroom.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.coatedUniversalFluidDuct.get(), RenderType.cutoutMipped())
        for ((source, flowing, _, _) in NTechFluids.getFluidsList()) {
            ItemBlockRenderTypes.setRenderLayer(source.get(), RenderType.translucent())
            ItemBlockRenderTypes.setRenderLayer(flowing.get(), RenderType.translucent())
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
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.atlas.location() == InventoryMenu.BLOCK_ATLAS) {
            NuclearTech.LOGGER.debug("Adding atlas textures")
            event.addSprite(ntm("block/steam_press/steam_press_head"))
            ChemPlantTemplateItem.getChemistrySpriteLocations(Minecraft.getInstance().resourceManager).forEach(event::addSprite)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerColors(event: ColorHandlerEvent) {
        if (event is ColorHandlerEvent.Block) {
            val blockColors = event.blockColors
            blockColors.register(
                { _, level, pos, _ ->
                    if (level == null || pos == null) -1
                    else level.getBlockTint(pos, BlockTints.FLUID_DUCT_COLOR_RESOLVER)
                }, NTechBlocks.coatedUniversalFluidDuct.get()
            )
        } else if (event is ColorHandlerEvent.Item) {
            val itemColors = event.itemColors
            itemColors.register({ stack, layer ->
                if (layer == 0) -1 else {
                    val fluid = FluidIdentifierItem.getFluid(stack)
                    if (fluid.isSame(Fluids.EMPTY)) return@register -1
                    val texture = fluid.attributes.stillTexture
                    try {
                        val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture)
                        val baseColor = sprite.getAverageColor(0, 0, 0, 15, 15) and 0xFFFFFF
                        Mth.colorMultiply(baseColor, fluid.attributes.color)
                    } catch (ex: Exception) {
                        NuclearTech.LOGGER.error("Couldn't sample fluid texture $texture for tinting fluid identifier", ex)
                        -1
                    }
                }
            }, NTechItems.fluidIdentifier.get())
            for (bombKit in BombKitItem.allKits) {
                itemColors.register({ _, layer -> if (layer == 0) -1 else bombKit.color }, bombKit)
            }
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerModels(event: ModelRegistryEvent) {
        NuclearTech.LOGGER.debug("Registering custom model loaders")
        ModelLoaderRegistry.registerLoader(ntm("random"), RandomModelLoader)

        NuclearTech.LOGGER.debug("Registering random model id suppliers")
        RandomModelLoader.setIdSupplier(NTechItems.polaroid)

//        ForgeModelBakery.addSpecialModel(ntm("other/assembler"))
//        ForgeModelBakery.addSpecialModel(ntm("other/mushroom_cloud"))
    }

    @SubscribeEvent @JvmStatic
    fun bakeExtraModels(event: ModelBakeEvent) {
        NuclearTech.LOGGER.debug("Baking additional models")
        ChemPlantTemplateItem.generateTemplateIcons(event.modelLoader)
    }

    @SubscribeEvent @JvmStatic
    fun registerResourceReloadListeners(event: RegisterClientReloadListenersEvent) {
        event.registerReloadListener(SpecialModels)

        val capeTextures = NTechCapes.CapeTextureManager(Minecraft.getInstance().textureManager)
        NTechCapes.capeTextures = capeTextures
        event.registerReloadListener(capeTextures)
    }

    @SubscribeEvent @JvmStatic
    fun registerParticleProviders(event: ParticleFactoryRegisterEvent) {
        with(Minecraft.getInstance().particleEngine) {
            register(NTechParticles.CONTRAIL.get(), ContrailParticle::Provider)
            register(NTechParticles.COOLING_TOWER_CLOUD.get(), CoolingTowerCloudParticle::Provider)
            register(NTechParticles.MINI_NUKE_CLOUD.get(), MiniNukeCloudParticle::Provider)
            register(NTechParticles.MINI_NUKE_CLOUD_BALEFIRE.get(), MiniNukeCloudParticle::Provider)
            register(NTechParticles.MINI_NUKE_FLASH.get(), MiniNukeFlashParticle::Provider)
            register(NTechParticles.OIL_SPILL.get(), OilSpillParticle::Provider)
            register(NTechParticles.RBMK_FIRE.get(), RBMKFireParticle::Provider)
            register(NTechParticles.RBMK_MUSH.get(), RBMKMushParticle::Provider)
            register(NTechParticles.ROCKET_FLAME.get(), RocketFlameParticle::Provider)
            register(NTechParticles.RUBBLE.get(), RubbleParticle.Provider())
            register(NTechParticles.SHOCKWAVE.get(), ShockwaveParticle::Provider)
            register(NTechParticles.SMOKE.get(), SmokeParticle::Provider)
            register(NTechParticles.VOLCANO_SMOKE.get(), VolcanoSmokeParticle::Provider)
        }
    }

    // uncomment, compile a snapshot, put it in the mods folder of your logged-in minecraft instance, start it and see the console log
//    @SubscribeEvent @JvmStatic
//    fun grabAuthToken(event: FMLClientSetupEvent) {
//        println("AUTH_TOKEN ${Minecraft.getInstance().user.accessToken}")
//        println("UUID ${Minecraft.getInstance().user.uuid}")
//    }

    @Mod.EventBusSubscriber(modid = MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
    object Shaders {
        lateinit var rendertypeMushroomCloudShader: ShaderInstance private set

        @SubscribeEvent @JvmStatic
        fun registerShaders(event: RegisterShadersEvent) {
            event.registerShader(ShaderInstance(event.resourceManager, ntm("rendertype_mushroom_cloud"), NuclearRenderTypes.VertexFormats.POSITION_COLOR_TEX_NORMAL)) { rendertypeMushroomCloudShader = it }
        }
    }
}
