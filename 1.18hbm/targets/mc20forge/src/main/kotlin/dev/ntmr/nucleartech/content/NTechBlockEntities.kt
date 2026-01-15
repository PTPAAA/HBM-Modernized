/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.block.entity.AssemblerBlockEntity
import dev.ntmr.nucleartech.content.block.entity.BlastFurnaceBlockEntity
import dev.ntmr.nucleartech.content.block.entity.CentrifugeBlockEntity
import dev.ntmr.nucleartech.content.block.entity.ChemPlantBlockEntity
import dev.ntmr.nucleartech.content.block.entity.CombustionGeneratorBlockEntity
import dev.ntmr.nucleartech.content.block.entity.ElectricFurnaceBlockEntity
import dev.ntmr.nucleartech.content.block.entity.FatManBlockEntity
import dev.ntmr.nucleartech.content.block.entity.LargeCoolingTowerBlockEntity
import dev.ntmr.nucleartech.content.block.entity.LaunchPadBlockEntity
import dev.ntmr.nucleartech.content.block.entity.LittleBoyBlockEntity
import dev.ntmr.nucleartech.content.block.entity.OilDerrickBlockEntity
import dev.ntmr.nucleartech.content.block.entity.PumpjackBlockEntity
import dev.ntmr.nucleartech.content.block.entity.SafeBlockEntity
import dev.ntmr.nucleartech.content.block.entity.ShredderBlockEntity
import dev.ntmr.nucleartech.content.block.entity.SirenBlockEntity
import dev.ntmr.nucleartech.content.block.entity.SmallCoolingTowerBlockEntity
import dev.ntmr.nucleartech.content.block.entity.SteamPressBlockEntity
import dev.ntmr.nucleartech.content.block.entity.TurbineBlockEntity
import dev.ntmr.nucleartech.content.block.entity.VolcanoBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAbsorberBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKAutoControlBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKBlankBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKBoilerBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKConsoleBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKInletBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKManualControlBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratedControlBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratedReaSimRodBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratedRodBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKModeratorBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKOutletBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKReaSimRodBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKReflectorBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKRodBlockEntity
import dev.ntmr.nucleartech.content.block.entity.rbmk.RBMKSteamConnectorBlockEntity
import dev.ntmr.nucleartech.content.block.entity.transmitters.CableBlockEntity
import dev.ntmr.nucleartech.content.block.entity.transmitters.FluidPipeBlockEntity
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPart
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPort
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object NTechBlockEntities : NTechRegistry<BlockEntityType<*>> {
    override val forgeRegistry: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)

    val genericMultiBlockPartBlockEntityType = register("generic_multi_block_part") { createType(MultiBlockPart::GenericMultiBlockPartBlockEntity, NTechBlocks.genericMultiBlockPart.get()) }
    val genericMultiBlockPortBlockEntityType = register("generic_multi_block_port") { createType(MultiBlockPort::GenericMultiBlockPortBlockEntity, NTechBlocks.genericMultiBlockPort.get()) }
    val safeBlockEntityType = register("safe") { createType(::SafeBlockEntity, NTechBlocks.safe.get()) }
    val sirenBlockEntityType = register("siren") { createType(::SirenBlockEntity, NTechBlocks.siren.get()) }
    val steamPressHeadBlockEntityType = register("steam_press_top") { createType(::SteamPressBlockEntity, NTechBlocks.steamPressTop.get()) }
    val blastFurnaceBlockEntityType = register("blast_furnace") { createType(::BlastFurnaceBlockEntity, NTechBlocks.blastFurnace.get()) }
    val combustionGeneratorBlockEntityType = register("combustion_generator") { createType(::CombustionGeneratorBlockEntity, NTechBlocks.combustionGenerator.get()) }
    val electricFurnaceBlockEntityType = register("electric_furnace") { createType(::ElectricFurnaceBlockEntity, NTechBlocks.electricFurnace.get()) }
    val shredderBlockEntityType = register("shredder") { createType(::ShredderBlockEntity, NTechBlocks.shredder.get()) }
    val assemblerBlockEntityType = register("assembler") { createType(::AssemblerBlockEntity, NTechBlocks.assembler.get()) }
    val chemPlantBlockEntityType = register("chem_plant") { createType(::ChemPlantBlockEntity, NTechBlocks.chemPlant.get()) }
    val turbineBlockEntityType = register("turbine") { createType(::TurbineBlockEntity, NTechBlocks.turbine.get()) }
    val smallCoolingTowerBlockEntityType = register("small_cooling_tower") { createType(::SmallCoolingTowerBlockEntity, NTechBlocks.smallCoolingTower.get()) }
    val largeCoolingTowerBlockEntityType = register("large_cooling_tower") { createType(::LargeCoolingTowerBlockEntity, NTechBlocks.largeCoolingTower.get()) }
    val oilDerrickBlockEntityType = register("oil_derrick") { createType(::OilDerrickBlockEntity, NTechBlocks.oilDerrick.get()) }
    val pumpjackBlockEntityType = register("pumpjack") { createType(::PumpjackBlockEntity, NTechBlocks.pumpjack.get()) }
    val centrifugeBlockEntityType = register("centrifuge") { createType(::CentrifugeBlockEntity, NTechBlocks.centrifuge.get()) }
    val cableBlockEntityType = register("cable") { createType(::CableBlockEntity, NTechBlocks.coatedCable.get()) }
    val fluidPipeBlockEntityType = register("fluid_pipe") { createType(::FluidPipeBlockEntity, NTechBlocks.coatedUniversalFluidDuct.get()) }
    val rbmkBlankBlockEntityType = register("rbmk_blank") { createType(::RBMKBlankBlockEntity, NTechBlocks.rbmkBlank.get()) }
    val rbmkRodBlockEntityType = register("rbmk_rod") { createType(::RBMKRodBlockEntity, NTechBlocks.rbmkRod.get()) }
    val rbmkModeratedRodBlockEntityType = register("rbmk_moderated_rod") { createType(::RBMKModeratedRodBlockEntity, NTechBlocks.rbmkModeratedRod.get()) }
    val rbmkReaSimRodBlockEntityType = register("rbmk_reasim_rod") { createType(::RBMKReaSimRodBlockEntity, NTechBlocks.rbmkReaSimRod.get()) }
    val rbmkModeratedReaSimRodBlockEntityType = register("rbmk_moderated_reasim_rod") { createType(::RBMKModeratedReaSimRodBlockEntity, NTechBlocks.rbmkModeratedReaSimRod.get()) }
    val rbmkAbsorberBlockEntityType = register("rbmk_absorber") { createType(::RBMKAbsorberBlockEntity, NTechBlocks.rbmkAbsorber.get()) }
    val rbmkModeratorBlockEntityType = register("rbmk_moderator") { createType(::RBMKModeratorBlockEntity, NTechBlocks.rbmkModerator.get()) }
    val rbmkReflectorBlockEntityType = register("rbmk_reflector") { createType(::RBMKReflectorBlockEntity, NTechBlocks.rbmkReflector.get()) }
    val rbmkBoilerBlockEntityType = register("rbmk_boiler") { createType(::RBMKBoilerBlockEntity, NTechBlocks.rbmkBoiler.get()) }
    val rbmkBoilerWaterInputBlockEntityType = register("rbmk_boiler_water_input") { createType(RBMKBoilerBlockEntity::WaterInputBlockEntity, NTechBlocks.rbmkBoilerColumn.get()) }
    val rbmkManualControlBlockEntityType = register("rbmk_manual_control") { createType(::RBMKManualControlBlockEntity, NTechBlocks.rbmkManualControlRod.get()) }
    val rbmkModeratedControlBlockEntityType = register("rbmk_moderated_control") { createType(::RBMKModeratedControlBlockEntity, NTechBlocks.rbmkModeratedControlRod.get()) }
    val rbmkAutoControlBlockEntityType = register("rbmk_auto_control") { createType(::RBMKAutoControlBlockEntity, NTechBlocks.rbmkAutoControlRod.get()) }
    val rbmkSteamConnectorBlockEntityType = register("rbmk_steam_connector") { createType(::RBMKSteamConnectorBlockEntity, NTechBlocks.rbmkSteamConnector.get()) }
    val rbmkInletBlockEntityType = register("rbmk_inlet") { createType(::RBMKInletBlockEntity, NTechBlocks.rbmkInlet.get()) }
    val rbmkOutletBlockEntityType = register("rbmk_outlet") { createType(::RBMKOutletBlockEntity, NTechBlocks.rbmkOutlet.get()) }
    val rbmkConsoleBlockEntityType = register("rbmk_console") { createType(::RBMKConsoleBlockEntity, NTechBlocks.rbmkConsole.get()) }
    val littleBoyBlockEntityType = register("little_boy") { createType(::LittleBoyBlockEntity, NTechBlocks.littleBoy.get()) }
    val fatManBlockEntityType = register("fat_man") { createType(::FatManBlockEntity, NTechBlocks.fatMan.get()) }
    val volcanoBlockEntityType = register("volcano") { createType(::VolcanoBlockEntity, NTechBlocks.volcanoCore.get()) }
    val launchPadBlockEntityType = register("launch_pad") { createType(::LaunchPadBlockEntity, NTechBlocks.launchPad.get()) }

    private fun <T : BlockEntity> createType(supplier: BlockEntityType.BlockEntitySupplier<T>, vararg blocks: Block) = BlockEntityType.Builder.of(supplier, *blocks).build()
    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
