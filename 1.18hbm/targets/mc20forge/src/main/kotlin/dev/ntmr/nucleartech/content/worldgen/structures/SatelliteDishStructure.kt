package dev.ntmr.nucleartech.content.worldgen.structures

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.ntmr.nucleartech.content.worldgen.NTechStructures
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure
import java.util.Optional

class SatelliteDishStructure(
    settings: StructureSettings,
    startPool: Holder<StructureTemplatePool>,
    startJigsawName: Optional<ResourceLocation>,
    maxDepth: Int,
    heightProvider: HeightProvider,
    useExpansionHack: Boolean,
    projectStartToHeightmap: Optional<Heightmap.Types>,
    maxDistanceFromCenter: Int
) : JigsawStructure(
    settings,
    startPool,
    startJigsawName,
    maxDepth,
    heightProvider,
    useExpansionHack,
    projectStartToHeightmap,
    maxDistanceFromCenter
) {

    companion object {
        val CODEC: Codec<SatelliteDishStructure> = RecordCodecBuilder.create { instance ->
            instance.group(
                settingsCodec(instance),
                StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter { it.startPool },
                ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter { it.startJigsawName },
                Codec.intRange(0, 7).fieldOf("size").forGetter { it.maxDepth },
                HeightProvider.CODEC.fieldOf("start_height").forGetter { it.startHeight },
                Codec.BOOL.fieldOf("use_expansion_hack").forGetter { it.useExpansionHack },
                Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter { it.projectStartToHeightmap },
                Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter { it.maxDistanceFromCenter }
            ).apply(instance, ::SatelliteDishStructure)
        }
    }

    override fun type(): StructureType<*> {
        return NTechStructures.SATELLITE_DISH.get()
    }
}
