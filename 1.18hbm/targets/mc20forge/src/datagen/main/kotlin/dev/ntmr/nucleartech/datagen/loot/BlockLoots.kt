package dev.ntmr.nucleartech.datagen.loot

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NuclearTech
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.content.block.rbmk.RBMKBaseBlock
import dev.ntmr.nucleartech.datagen.CommonBlocks
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.IntRange
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.LimitCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import net.minecraftforge.registries.ForgeRegistries

class BlockLoots : BlockLoot() {
    override fun addTables() {
        for (definition in CommonBlocks.data) {
            val block = definition.block.get()
            if (block.lootTable == BuiltInLootTables.EMPTY) {
                NuclearTech.LOGGER.info("Skipping loot table for block ${definition.block.id}, as it has Properties#noDrops set")
                continue
            }
            definition.loot(this, block)
        }
    }

    fun noDrop(block: Block) {
        add(block, noDrop())
    }

    fun addOreDrop(block: Block, item: Item) {
        add(block) { createOreDrop(it, item) }
    }

    fun addOreDrop(block: Block, item: ItemLike, defaultMin: Float, defaultMax: Float) {
        add(block,
            createSilkTouchDispatchTable(block,
                applyExplosionDecay(block,
                    LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(defaultMin, defaultMax)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                )
            )
        )
    }

    fun addOreWithRareSideDrop(block: Block, item: ItemLike, defaultMin: Float, defaultMax: Float, rareItem: ItemLike, rareItemChance: Double) {
        val hasSilkTouchCondition = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
        val hasNoSilkTouchCondition = hasSilkTouchCondition.invert()

        add(block,
            createSilkTouchDispatchTable(block,
                applyExplosionDecay(block,
                    LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(defaultMin, defaultMax)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                )
            ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F))
                .add(LootItem.lootTableItem(rareItem).`when`(hasNoSilkTouchCondition))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between((1.0 - 1.0 / rareItemChance).toFloat(), 1F)))
                .apply(LimitCount.limitCount(IntRange.lowerBound(0))))
        )
    }

    fun addOreWeightedTable(block: Block, vararg itemChances: Pair<ItemLike, Float>) {
        val tableItems = itemChances.map { (item, chance) -> LootItem.lootTableItem(item).`when`(LootItemRandomChanceCondition.randomChance(chance)) }
            .plus(LootItem.lootTableItem(block).`when`(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))))
            .toTypedArray()
        add(block, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(AlternativesEntry.alternatives(*tableItems))))
    }

    fun addSingleDropSilkTouch(block: Block, item: ItemLike) {
        add(block, createSingleItemTableWithSilkTouch(block, item))
    }

    fun addMushroomBlock(block: Block, item: ItemLike) {
        add(block, createMushroomBlockDrop(block, item))
    }

    fun rbmkDrops(block: RBMKBaseBlock) {
        add(block, LootTable.lootTable()
            .withPool(applyExplosionCondition(block, LootPool.lootPool().add(LootItem.lootTableItem(block))))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NTechItems.rbmkLid.get())).`when`(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RBMKBaseBlock.LID_TYPE, RBMKBaseBlock.LidType.CONCRETE))))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NTechItems.rbmkGlassLid.get())).`when`(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RBMKBaseBlock.LID_TYPE, RBMKBaseBlock.LidType.LEAD_GLASS))))
        )
    }

    // automatically await a loot table for all blocks registered by this mod
    override fun getKnownBlocks(): MutableIterable<Block> = ForgeRegistries.BLOCKS.entries
        .filter { it.key.location().namespace == MODID }
        .map { it.value }
        .toMutableList()
}
