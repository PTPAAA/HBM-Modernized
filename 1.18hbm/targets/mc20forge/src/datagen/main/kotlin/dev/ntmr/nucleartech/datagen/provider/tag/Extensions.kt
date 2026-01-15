package dev.ntmr.nucleartech.datagen.provider.tag

import net.minecraft.data.tags.TagsProvider
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

fun <T> TagsProvider.TagAppender<T>.add(registryObject: RegistryObject<T>): TagsProvider.TagAppender<T> =
    this.add(registryObject.key as net.minecraft.resources.ResourceKey<T>)

fun <T> TagsProvider.TagAppender<T>.add(vararg registryObjects: RegistryObject<T>): TagsProvider.TagAppender<T> {
    registryObjects.forEach { this.add(it.key as net.minecraft.resources.ResourceKey<T>) }
    return this
}

// Extensions for Block, Item, and Fluid removed as they conflict with IntrinsicHolderTagsProvider.add
// Use RegistryObject extensions or vanilla add methods.

// Extensions for TagAppender to wrap RegistryObject and support chaining
fun <T> TagsProvider.TagAppender<T>.add(registryObject: RegistryObject<T>): TagsProvider.TagAppender<T> =
    this.add(registryObject.key as net.minecraft.resources.ResourceKey<T>)

fun <T> TagsProvider.TagAppender<T>.add(vararg registryObjects: RegistryObject<T>): TagsProvider.TagAppender<T> {
    registryObjects.forEach { this.add(it.key as net.minecraft.resources.ResourceKey<T>) }
    return this
}

fun TagsProvider.TagAppender<Block>.add(block: Block): TagsProvider.TagAppender<Block> =
    this.add(ForgeRegistries.BLOCKS.getResourceKey(block).orElseThrow { IllegalStateException("Block $block not in registry") })

fun TagsProvider.TagAppender<Block>.add(vararg blocks: Block): TagsProvider.TagAppender<Block> {
    blocks.forEach { add(it) }
    return this
}

fun TagsProvider.TagAppender<Item>.add(item: Item): TagsProvider.TagAppender<Item> =
    this.add(ForgeRegistries.ITEMS.getResourceKey(item).orElseThrow { IllegalStateException("Item $item not in registry") })

fun TagsProvider.TagAppender<Item>.add(vararg items: Item): TagsProvider.TagAppender<Item> {
    items.forEach { add(it) }
    return this
}

fun TagsProvider.TagAppender<Fluid>.add(fluid: Fluid): TagsProvider.TagAppender<Fluid> =
    this.add(ForgeRegistries.FLUIDS.getResourceKey(fluid).orElseThrow { IllegalStateException("Fluid $fluid not in registry") })

fun TagsProvider.TagAppender<Fluid>.add(vararg fluids: Fluid): TagsProvider.TagAppender<Fluid> {
    fluids.forEach { add(it) }
    return this
}
