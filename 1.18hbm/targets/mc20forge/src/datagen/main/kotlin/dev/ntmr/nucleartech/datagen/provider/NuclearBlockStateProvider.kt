package dev.ntmr.nucleartech.datagen.provider

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.block.AnvilBlock
import dev.ntmr.nucleartech.content.NTechBlocks
import dev.ntmr.nucleartech.content.NTechFluids
import dev.ntmr.nucleartech.datagen.CommonBlocks
import dev.ntmr.nucleartech.ntm
import net.minecraft.core.Direction
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.PipeBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class NuclearBlockStateProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : BlockStateProvider(dataGenerator, MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block States and Models"

    override fun registerStatesAndModels() {
        for (definition in CommonBlocks.data) {
            val block = definition.block.get()
            definition.model(this, block)
            definition.itemModel(this, block)
        }

        for ((source, _, _, block) in NTechFluids.getFluidsList()) {
            simpleBlock(block.get(), models().getBuilder(block.id.path).texture("particle", source.get().attributes.stillTexture))
        }
    }

    private val blockTransformsModel = models().getExistingFile(mcLoc("block/block"))
    private val generatedItem = models().getExistingFile(mcLoc("item/generated"))

    fun simpleItem(block: Block) {
        itemModels().getBuilder(block.registryName!!.path)
            .parent(generatedItem)
            .texture("layer0", blockTexture(block))
    }

    // no-op
    @Suppress("unused")
    fun noItemModel(block: Block) {}

    private fun name(block: Block) = block.registryName!!.path

    fun extend(rl: ResourceLocation, suffix: String): ResourceLocation =
        ResourceLocation(rl.namespace, rl.path + suffix)

    fun copiedBlock(block: Block, from: Block) {
        simpleBlock(block, models().getExistingFile(from.registryName))
    }

    fun copiedBlockItem(block: Block) {
        simpleBlockItem(block, models().getExistingFile(block.registryName))
    }

    fun cubeAllSides(
        block: Block,
        down: ResourceLocation = extend(blockTexture(block), "_down"),
        up: ResourceLocation = extend(blockTexture(block), "_up"),
        north: ResourceLocation = extend(blockTexture(block), "_north"),
        south: ResourceLocation = extend(blockTexture(block), "_south"),
        east: ResourceLocation = extend(blockTexture(block), "_east"),
        west: ResourceLocation = extend(blockTexture(block), "_west")
    ) {
        simpleBlock(block, models().cube(name(block), down, up, north, south, east, west).texture("particle", north))
    }

    fun emptyModelBlock(block: Block) {
        simpleBlock(block, models().getBuilder(block.registryName!!.path))
    }

    fun columnBlock(block: Block) {
        val texture = blockTexture(block)
        simpleBlock(block, models().cubeColumn(block.registryName!!.path, extend(texture, "_side"), extend(texture, "_end")))
    }

    fun orientableBlockWithFront(block: Block) {
        val texture = blockTexture(block)
        horizontalBlock(block, extend(texture, "_side"), extend(texture, "_front"), extend(texture, "_side"))
    }

    fun axisBeam(block: RotatedPillarBlock, thickness: Float) {
        models().withExistingParent(block.registryName!!.path, "block/block")
            .element().from(8F - thickness * .5F, 0F, 8F - thickness * .5F).to(8F + thickness * 0.5F, 16F, 8F + thickness * 0.5F)
            .allFaces { _, builder -> builder.uvs(0F, 0F, thickness, 16F) }
            .face(Direction.UP).uvs(0F, 0F, thickness, thickness).cullface(Direction.UP).end()
            .face(Direction.DOWN).uvs(0F, 0F, thickness, thickness).cullface(Direction.DOWN).end()
            .textureAll("#all").end()
            .texture("all", blockTexture(block))
            .texture("particle", blockTexture(block))
            .also { axisBlock(block, it, it) }
    }

    fun simpleTintedBlock(block: Block) {
        val texture = blockTexture(block)
        simpleBlock(block, models().withExistingParent(name(block), "block/block")
            .element().cube("#texture").end()
            .element().cube("#overlay").allFaces { _, builder -> builder.tintindex(0) }.end()
            .texture("texture", texture)
            .texture("particle", texture)
            .texture("overlay", extend(texture, "_overlay")))
    }

    fun simpleExistingModelBlock(block: Block) {
        simpleBlock(block, models().getExistingFile(block.registryName!!))
    }

    fun simpleObjModelBlock(block: Block, modelLocation: ResourceLocation = modLoc("models/other/${block.registryName!!.path}.obj"), textureLocation: ResourceLocation = modLoc("other/${block.registryName!!.path}")) {
        simpleBlock(block, models().getBuilder(block.registryName!!.path)
            .parent(blockTransformsModel)
            .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
            .modelLocation(modelLocation)
            .flipV(true).detectCullableFaces(false).end()
            .texture("texture", textureLocation)
            .texture("particle", textureLocation)
        )
    }

    fun specialModelParticles(block: Block, particleTexture: String = "other/${block.registryName!!.path}/particles") {
        simpleBlock(block, models().getBuilder(block.registryName!!.path).texture("particle", particleTexture))
    }

    fun steelScaffold(block: Block) {
        val scaffoldTexture = blockTexture(NTechBlocks.steelDecoBlock.get())
        val scaffoldModel = models().withExistingParent(block.registryName!!.path, "block/block").customLoader { parent, existingFileHelper -> OBJLoaderBuilder.begin(parent, existingFileHelper) }.modelLocation(ntm("models/block/steel_scaffold.obj")).flipV(true).end().texture("texture", scaffoldTexture).texture("particle", scaffoldTexture)
        getVariantBuilder(block)
            .partialState().with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X).modelForState().modelFile(scaffoldModel).addModel()
            .partialState().with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z).modelForState().modelFile(scaffoldModel).rotationY(90).addModel()
    }

    fun steelGrate(block: Block) {
        val grateTop = blockTexture(block)
        val grateSide = extend(grateTop, "_side")
        fun grateModel(level: Int) = models().withExistingParent("${block.registryName!!.path}${level - 1}", "block/block").element().from(0F, (level - 1) * 2F, 0F).to(16F, level * 2F, 16F)
            .allFaces { side, builder -> builder.uvs(0F, 0F, 16F, 2F).texture("#side").cullface(side) }
            .face(Direction.UP).uvs(0F, 0F, 16F, 16F).texture("#up").end()
            .face(Direction.DOWN).uvs(0F, 0F, 16F, 16F).texture("#up").end()
            .end().texture("side", grateSide).texture("up", grateTop).texture("particle", grateTop)

        getVariantBuilder(block).forAllStatesExcept({
            val level = it.getValue(BlockStateProperties.LEVEL_FLOWING)

            ConfiguredModel.builder()
                .modelFile(grateModel(level))
                .build()
        }, BlockStateProperties.WATERLOGGED)
    }

    fun simplePlant(block: Block) {
        simpleBlock(block, models().cross(block.registryName!!.path, blockTexture(NTechBlocks.glowingMushroom.get())))
    }

    fun mushroomBlock(block: Block, insideTextureProvider: Block = block) {
        val singleFaceTemplateModelFile = models().getExistingFile(mcLoc("block/template_single_face"))
        val outsideModelFile = models().getBuilder(block.registryName!!.path).parent(singleFaceTemplateModelFile).texture("texture", blockTexture(block))
        val insideModelFile = models().getBuilder("${insideTextureProvider.registryName!!.path}_inside").parent(singleFaceTemplateModelFile).texture("texture", extend(blockTexture(insideTextureProvider), "_inside"))
        getMultipartBuilder(block)
            .part().modelFile(outsideModelFile).addModel().condition(PipeBlock.NORTH, true).end()
            .part().modelFile(outsideModelFile).rotationY(90).uvLock(true).addModel().condition(PipeBlock.EAST, true).end()
            .part().modelFile(outsideModelFile).rotationY(180).uvLock(true).addModel().condition(PipeBlock.SOUTH, true).end()
            .part().modelFile(outsideModelFile).rotationY(270).uvLock(true).addModel().condition(PipeBlock.WEST, true).end()
            .part().modelFile(outsideModelFile).rotationX(270).uvLock(true).addModel().condition(PipeBlock.UP, true).end()
            .part().modelFile(outsideModelFile).rotationX(90).uvLock(true).addModel().condition(PipeBlock.DOWN, true).end()
            .part().modelFile(insideModelFile).addModel().condition(PipeBlock.NORTH, false).end()
            .part().modelFile(insideModelFile).rotationY(90).uvLock(false).addModel().condition(PipeBlock.EAST, false).end()
            .part().modelFile(insideModelFile).rotationY(180).uvLock(false).addModel().condition(PipeBlock.SOUTH, false).end()
            .part().modelFile(insideModelFile).rotationY(270).uvLock(false).addModel().condition(PipeBlock.WEST, false).end()
            .part().modelFile(insideModelFile).rotationX(270).uvLock(false).addModel().condition(PipeBlock.UP, false).end()
            .part().modelFile(insideModelFile).rotationX(90).uvLock(false).addModel().condition(PipeBlock.DOWN, false).end()
    }

    fun grassLikeBlock(block: Block) {
        simpleBlock(block, models().cubeBottomTop(block.registryName!!.path, extend(blockTexture(block), "_side"), blockTexture(Blocks.DIRT), extend(blockTexture(block), "_top")))
    }

    fun litHorizontalBlock(
        block: Block,
        side: ResourceLocation = extend(blockTexture(block), "_side"),
        sideLit: ResourceLocation = side,
        front: ResourceLocation = extend(blockTexture(block), "_front"),
        frontLit: ResourceLocation = extend(blockTexture(block), "_front_on"),
        bottom: ResourceLocation = side,
        bottomLit: ResourceLocation = bottom,
        top: ResourceLocation = side,
        topLit: ResourceLocation = top
    ) {
        getVariantBuilder(block)
            .forAllStates {
                if (it.getValue(BlockStateProperties.LIT))
                    ConfiguredModel.builder()
                        .modelFile(models().orientableWithBottom(name(block) + "_on", sideLit, frontLit, bottomLit, topLit))
                        .rotationY((it.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180).toInt() % 360)
                        .build()
                else
                    ConfiguredModel.builder()
                        .modelFile(models().orientableWithBottom(name(block), side, front, bottom, top))
                        .rotationY((it.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180).toInt() % 360)
                        .build()
            }
    }

    fun anvil(anvil: AnvilBlock) {
        getVariantBuilder(anvil).forAllStatesExcept({
            ConfiguredModel.builder()
                .modelFile(models().getBuilder(anvil.registryName!!.path)
                    .customLoader { modelLoader, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                    .modelLocation(modLoc("models/block/anvil/anvil.obj"))
                    .flipV(true).detectCullableFaces(false).end()
                    .texture("anvil_texture", blockTexture(anvil))
                    .texture("particle", blockTexture(anvil))
                    .parent(blockTransformsModel))
                .rotationY(((it.getValue(HorizontalDirectionalBlock.FACING).toYRot() + 180) % 360).toInt())
                .build()
        }, BlockStateProperties.WATERLOGGED)
    }
}
