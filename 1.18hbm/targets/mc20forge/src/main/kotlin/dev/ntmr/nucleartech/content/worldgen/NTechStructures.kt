package dev.ntmr.nucleartech.content.worldgen

import com.mojang.serialization.Codec
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.content.NTechRegistry
import dev.ntmr.nucleartech.content.worldgen.structures.BunkerStructure
import dev.ntmr.nucleartech.content.worldgen.structures.MissileSiloStructure
import dev.ntmr.nucleartech.content.worldgen.structures.RadioStationStructure
import dev.ntmr.nucleartech.content.worldgen.structures.RuinsStructure
import dev.ntmr.nucleartech.content.worldgen.structures.SatelliteDishStructure
import dev.ntmr.nucleartech.ntm
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraftforge.registries.DeferredRegister

object NTechStructures : NTechRegistry<StructureType<*>> {
    override val forgeRegistry: DeferredRegister<StructureType<*>> = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID)

    val RUINS = register("ruins") { ExplicitStructureType(RuinsStructure.CODEC) }
    val RADIO_STATION = register("radio_station") { ExplicitStructureType(RadioStationStructure.CODEC) }
    val SATELLITE_DISH = register("satellite_dish") { ExplicitStructureType(SatelliteDishStructure.CODEC) }
    val MISSILE_SILO = register("missile_silo") { ExplicitStructureType(MissileSiloStructure.CODEC) }
    val BUNKER = register("bunker") { ExplicitStructureType(BunkerStructure.CODEC) }

    // Helper class to avoid anonymous class issues in registration if needed, or just standard type
    class ExplicitStructureType<S : Structure>(private val codec: Codec<S>) : StructureType<S> {
        override fun codec(): Codec<S> = codec
    }
}
