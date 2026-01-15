/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.block.entity.*
import dev.ntmr.nucleartech.content.block.entity.chemistry.*
import dev.ntmr.nucleartech.content.block.entity.explosive.*
import dev.ntmr.nucleartech.content.block.entity.machine.*
import dev.ntmr.nucleartech.content.block.entity.oil.*
import dev.ntmr.nucleartech.content.block.entity.physics.*
import dev.ntmr.nucleartech.content.block.entity.processor.*
import dev.ntmr.nucleartech.content.block.entity.transport.*
import dev.ntmr.nucleartech.content.block.entity.watz.*
import dev.ntmr.nucleartech.content.block.entity.rbmk.*

import dev.ntmr.nucleartech.content.block.multi.MultiBlockPart
import dev.ntmr.nucleartech.content.block.multi.MultiBlockPort
import dev.ntmr.nucleartech.content.NTechRegistry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import dev.ntmr.nucleartech.content.block.entity.chemistry.*
import dev.ntmr.nucleartech.content.block.entity.explosive.*
import dev.ntmr.nucleartech.content.block.entity.machine.*
import dev.ntmr.nucleartech.content.block.entity.oil.*
import dev.ntmr.nucleartech.content.block.entity.physics.*
import dev.ntmr.nucleartech.content.block.entity.processor.*
import dev.ntmr.nucleartech.content.block.entity.transmitters.*
import dev.ntmr.nucleartech.content.block.entity.watz.*
import dev.ntmr.nucleartech.content.block.entity.FusionCoreBlockEntity

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
    val dieselGeneratorBlockEntityType = register("diesel_generator") { createType(::DieselGeneratorBlockEntity, NTechBlocks.dieselGenerator.get()) }
    val batteryBoxBlockEntityType = register("battery_box") { createType(::BatteryBoxBlockEntity, NTechBlocks.batteryBox.get()) }
    val electricFurnaceBlockEntityType = register("electric_furnace") { createType(::ElectricFurnaceBlockEntity, NTechBlocks.electricFurnace.get()) }
    val electricPressBlockEntityType = register("electric_press") { createType(::ElectricPressBlockEntity, NTechBlocks.electricPress.get()) }
    val fractionatingColumnBlockEntityType = register("fractionating_column") { createType(::FractionatingColumnBlockEntity, NTechBlocks.fractionatingColumn.get()) }
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
 
    val forcefieldGeneratorBlockEntityType = register("forcefield_generator") { createType(::ForcefieldGeneratorBlockEntity, NTechBlocks.forcefieldGenerator.get()) }
    val fusionCoreBlockEntityType = register("fusion_core") { createType(::FusionCoreBlockEntity, NTechBlocks.fusionCore.get()) }
    val watzCoreBlockEntityType = register("watz_core") { createType(::WatzCoreBlockEntity, NTechBlocks.watzCore.get()) }
    val oilRefineryBlockEntityType = register("oil_refinery") { createType(::OilRefineryBlockEntity, NTechBlocks.oilRefinery.get()) }
    val cyclotronBlockEntityType = register("cyclotron") { createType(::CyclotronBlockEntity, NTechBlocks.cyclotron.get()) }
    val felBlockEntityType = register("fel") { createType(::FELBlockEntity, NTechBlocks.fel.get()) }
    // val ruinedReactorCoreBlockEntity = register("ruined_reactor_core") { BlockEntityType.Builder.of(::RuinedReactorCoreBlockEntity, NTechBlocks.ruinedReactorCore.get()).build(null) }
 
    // Phase 25
    val teleporter = register("teleporter") { BlockEntityType.Builder.of(::TeleporterBlockEntity, NTechBlocks.teleporter.get()).build(null) }
 
    // Phase 22: Transport
    val conveyorBlockEntityType = register("conveyor") { BlockEntityType.Builder.of(::ConveyorBlockEntity, NTechBlocks.conveyor.get()).build(null) }
    
    val genericFluidPipeBlockEntityType = register("generic_fluid_pipe") { BlockEntityType.Builder.of(::FluidPipeBlockEntity, NTechBlocks.fluidPipe.get()).build(null) }
    val blackHoleBombBlockEntityType = register("black_hole_bomb") { createType(::BlackHoleBombBlockEntity, NTechBlocks.blackHoleBomb.get()) }
    val lhcBlockEntityType = register("lhc") { createType(::LHCBlockEntity, NTechBlocks.lhc.get()) }
    val prototypeBlockEntityType = register("prototype") { createType(::PrototypeBlockEntity, NTechBlocks.prototype.get()) }
    val tsarBombaBlockEntityType = register("tsar_bomba") { createType(::TsarBombaBlockEntity, NTechBlocks.tsarBomba.get()) }
    
    val oreWasherBlockEntityType = register("ore_washer") { createType(::OreWasherBlockEntity, NTechBlocks.oreWasher.get()) }
    val thermalCentrifugeBlockEntityType = register("thermal_centrifuge") { createType(::ThermalCentrifugeBlockEntity, NTechBlocks.thermalCentrifuge.get()) }
    val crystallizerBlockEntityType = register("crystallizer") { createType(::CrystallizerBlockEntity, NTechBlocks.crystallizer.get()) }
    val crackingTowerBlockEntityType = register("cracking_tower") { createType(::CrackingTowerBlockEntity, NTechBlocks.crackingTower.get()) }
    val catalyticReformerBlockEntityType = register("catalytic_reformer") { createType(::CatalyticReformerBlockEntity, NTechBlocks.catalyticReformer.get()) }
    val arcFurnaceBlockEntityType = register("arc_furnace") { createType<ArcFurnaceBlockEntity>(::ArcFurnaceBlockEntity, NTechBlocks.arcFurnace.get()) }
 
    // Aliases for compatibility with code using legacy short names
    val forcefieldGenerator get() = forcefieldGeneratorBlockEntityType
    val fusionCore get() = fusionCoreBlockEntityType
    val centrifuge get() = centrifugeBlockEntityType
    val blackHoleBomb get() = blackHoleBombBlockEntityType
    val lhc get() = lhcBlockEntityType
    val prototype get() = prototypeBlockEntityType
    val tsarBomba get() = tsarBombaBlockEntityType
    val oreWasher get() = oreWasherBlockEntityType
    val thermalCentrifuge get() = thermalCentrifugeBlockEntityType
    val crystallizer get() = crystallizerBlockEntityType
    val crackingTower get() = crackingTowerBlockEntityType
    val catalyticReformer get() = catalyticReformerBlockEntityType
    val arcFurnace get() = arcFurnaceBlockEntityType

    private fun <T : BlockEntity> createType(supplier: BlockEntityType.BlockEntitySupplier<T>, vararg blocks: Block) = BlockEntityType.Builder.of(supplier, *blocks).build()
    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
