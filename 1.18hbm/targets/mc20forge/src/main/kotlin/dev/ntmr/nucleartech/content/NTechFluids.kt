/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.fluid.CoriumFluid
import dev.ntmr.nucleartech.content.fluid.GaseousFluid
import dev.ntmr.nucleartech.content.fluid.VolcanicLavaBlock
import dev.ntmr.nucleartech.content.fluid.VolcanicLavaFluid
import dev.ntmr.nucleartech.content.LateRegistryProperty
import dev.ntmr.nucleartech.ntm
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.core.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.*
import net.minecraft.world.level.block.DispenserBlock
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
import net.minecraftforge.common.SoundActions
import net.minecraftforge.fluids.FluidType
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fluids.ForgeFlowingFluid.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.level.pathfinder.BlockPathTypes

object NTechFluids : NTechRegistry<Fluid> {
    private val fluids = mutableListOf<FluidObject<*, *, *, *>>()

    private const val ABSOLUTE_ZERO = -273

    override val forgeRegistry: DeferredRegister<Fluid> = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID)
    val FLUID_TYPES: DeferredRegister<FluidType> = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID)

    val spentSteam = registerFluid("spent_steam", FluidAttributes.builder(ntm("fluid/spent_steam"), ntm("fluid/spent_steam")).gaseous().sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steam = registerFluid("steam", FluidAttributes.builder(ntm("fluid/steam"), ntm("fluid/steam")).density(-100).gaseous().temperature(100 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamHot = registerFluid("hot_steam", FluidAttributes.builder(ntm("fluid/hot_steam"), ntm("fluid/hot_steam")).density(-10).gaseous().temperature(300 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamSuperHot = registerFluid("super_hot_steam", FluidAttributes.builder(ntm("fluid/super_hot_steam"), ntm("fluid/super_hot_steam")).density(0).gaseous().temperature(450 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamUltraHot = registerFluid("ultra_hot_steam", FluidAttributes.builder(ntm("fluid/ultra_hot_steam"), ntm("fluid/ultra_hot_steam")).density(10).gaseous().temperature(600 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val oil = registerFluid("oil", FluidAttributes.builder(ntm("fluid/oil_still"), ntm("fluid/oil_flow")), ::Source, ::Flowing, ::BucketItem, propertiesModifier = { tickRate(30).levelDecreasePerBlock(5) })
    val gas = registerFluid("gas", FluidAttributes.builder(ntm("fluid/gas_still"), ntm("fluid/gas_still")).gaseous().density(-100).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })
    val uraniumHexafluoride = registerFluid("uranium_hexafluoride", FluidAttributes.builder(ntm("fluid/uranium_hexafluoride_still"), ntm("fluid/uranium_hexafluoride_flow")).mapColor(0xE6D1CEBEu.toInt()).gaseous().sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })
    val corium = registerFluid("corium_fluid", FluidAttributes.builder(ntm("fluid/corium_still"), ntm("fluid/corium_flow")).density(100_000).temperature(3000 - ABSOLUTE_ZERO), CoriumFluid::Source, CoriumFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(30) }, fluidBlockProperties = BlockBehaviour.Properties.of().mapColor(MapColor.WATER).speedFactor(0.01F).noCollission().strength(1000F).noLootTable())
    val volcanicLava = registerFluid("volcanic_lava", FluidAttributes.builder(ntm("fluid/volcanic_lava_still"), ntm("fluid/volcanic_lava_flow")).luminosity(15).density(3200).viscosity(7000).temperature(900 - ABSOLUTE_ZERO).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), VolcanicLavaFluid::Source, VolcanicLavaFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(36).levelDecreasePerBlock(2) }, fluidBlockProperties = BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().strength(1000F).noLootTable().lightLevel { 15 }.hasPostProcess { _, _, _ -> true }, liquidBlockCreator = ::VolcanicLavaBlock)

    // specifying a default argument doesn't work because of generics
    fun registerFluid(name: String, attributes: FluidAttributes.Builder) = registerFluid(name, attributes, ::Source, ::Flowing, ::BucketItem)

    // æugh
    fun <S : ForgeFlowingFluid, F : ForgeFlowingFluid, BU : BucketItem> registerFluid(
        name: String,
        builder: FluidAttributes.Builder,
        sourceCreator: (properties: Properties) -> S,
        flowingCreator: (properties: Properties) -> F,
        bucketCreator: (fluidSupplier: Supplier<out Fluid>, properties: Item.Properties) -> BU,
        propertiesModifier: Properties.() -> Unit = {},
        bucketProperties: () -> Item.Properties = { Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET) },
        fluidBlockProperties: BlockBehaviour.Properties = BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().strength(100F).noLootTable(),
        liquidBlockCreator: (fluidSupplier: Supplier<out FlowingFluid>, properties: BlockBehaviour.Properties) -> LiquidBlock = ::LiquidBlock
    ): FluidObject<S, F, BU, LiquidBlock> {
        val fluidObject = FluidObject<S, F, BU, LiquidBlock>()
        
        fluidObject.type = FLUID_TYPES.register(name) {
             object : FluidType(builder.propertiesBuilder) {
                override fun initializeClient(consumer: Consumer<IClientFluidTypeExtensions>) {
                    consumer.accept(object : IClientFluidTypeExtensions {
                        override fun getStillTexture() = builder.still
                        override fun getFlowingTexture() = builder.flow
                        override fun getTintColor() = builder.tintColor
                        // Overlay texture support if needed, but not in original builder usage
                    })
                }
            }
        }

        val properties = Properties(fluidObject.type, fluidObject::getSourceFluid, fluidObject::getFlowingFluid)
            .bucket(fluidObject::getBucket)
            .block(fluidObject::getBlock)
        
        // Apply logic from builder (e.g. descriptionId is typically auto-handled by FluidType but translationKey call in original code sets it for block/bucket maybe?)
        // In 1.18 original: attributes.translationKey("block.nucleartech.$name")
        // FluidType handles descriptionId internally (fluid_type.modid.name).
        // Blocks/Items handle their own.
        
        properties.propertiesModifier()
        fluidObject.source = register(name) { sourceCreator(properties) }
        fluidObject.flowing = register("flowing_$name") { flowingCreator(properties) }
        fluidObject.bucket = NTechItems.register("${name}_bucket") { bucketCreator.invoke(fluidObject::getSourceFluid, bucketProperties()) }
        fluidObject.block = NTechBlocks.register(name) { liquidBlockCreator(fluidObject::getSourceFluid, fluidBlockProperties) }
        fluids += fluidObject
        return fluidObject
    }

    fun getFluidsList() = fluids.toList()

    private val bucketBehaviour = object : DefaultDispenseItemBehavior() {
        override fun execute(source: BlockSource, stack: ItemStack): ItemStack {
            val bucket = stack.item as DispensibleContainerItem
            val pos = source.pos.relative(source.blockState.getValue(DispenserBlock.FACING))
            val level = source.level
            // emptyContents signature might vary in 1.20?
            // boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult)
            return if (bucket.emptyContents(null, level, pos, null)) {
                bucket.checkExtraContent(null, level, stack, pos)
                ItemStack(Items.BUCKET)
            } else super.execute(source, stack)
        }
    }

    fun registerDispenserBehaviour() {
        for ((_, _, bucket, _) in getFluidsList()) {
            DispenserBlock.registerBehavior(bucket.get(), bucketBehaviour)
        }
    }

    class FluidObject<S : Fluid, F : Fluid, BU : BucketItem, BL : LiquidBlock> {
        var source by LateRegistryProperty<RegistryObject<S>>()
        var flowing by LateRegistryProperty<RegistryObject<F>>()
        var bucket by LateRegistryProperty<RegistryObject<BU>>()
        var block by LateRegistryProperty<RegistryObject<BL>>()
        lateinit var type: RegistryObject<FluidType>

        operator fun component1() = source
        operator fun component2() = flowing
        operator fun component3() = bucket
        operator fun component4() = block
    }

    // Compatibility Shim for FluidAttributes
    object FluidAttributes {
        fun builder(still: ResourceLocation, flow: ResourceLocation) = Builder(still, flow)

        class Builder(val still: ResourceLocation, val flow: ResourceLocation) {
            val propertiesBuilder = FluidType.Properties.create()
            var tintColor: Int = -1

            fun density(density: Int) = apply { propertiesBuilder.density(density) }
            fun viscosity(viscosity: Int) = apply { propertiesBuilder.viscosity(viscosity) }
            fun temperature(temperature: Int) = apply { propertiesBuilder.temperature(temperature) }
            fun luminosity(luminosity: Int) = apply { propertiesBuilder.lightLevel(luminosity) }
            fun sound(fill: SoundEvent?, empty: SoundEvent? = null) = apply { 
                if (fill != null) propertiesBuilder.sound(SoundActions.BUCKET_FILL, fill)
                if (empty != null) propertiesBuilder.sound(SoundActions.BUCKET_EMPTY, empty)
            }
            // Overloaded sound helper from original code usage
            fun sound(sound: SoundEvent?) = apply {
               // If null, maybe disable sounds? FluidType defaults to water sounds.
               // We can't strictly "null" it in builder if it expects SoundAction.
               // But if usage is sound(null), we might ignore it or set empty sound?
            }
            fun gaseous() = apply { 
                propertiesBuilder.density(-1000) // Gaseous usually implies negative density or specific tag?
                // In generic 1.18, gaseous meant light gas.
            }
            fun mapColor(color: Int) = apply { 
               // tintColor? Or map color for minimaps?
               // FluidType doesn't have mapColor directly? 
               // It's usually IClientFluidTypeExtensions.getTintColor().
               // So we store it in tintColor.
               tintColor = color
            }
            fun translationKey(key: String) = apply { propertiesBuilder.descriptionId(key) }
        }
    }
}

// need this for referencing in registerFluid
private fun <S : Fluid> NTechFluids.FluidObject<S, *, *, *>.getSourceFluid() = source.get()
private fun <F : Fluid> NTechFluids.FluidObject<*, F, *, *>.getFlowingFluid() = flowing.get()
private fun <BU : BucketItem> NTechFluids.FluidObject<*, *, BU, *>.getBucket() = bucket.get()
private fun <BL : LiquidBlock> NTechFluids.FluidObject<*, *, *, BL>.getBlock() = block.get()
