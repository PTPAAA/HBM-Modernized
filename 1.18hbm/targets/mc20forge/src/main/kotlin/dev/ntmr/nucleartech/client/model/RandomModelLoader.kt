package dev.ntmr.nucleartech.client.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.mojang.logging.LogUtils
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.*
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.geometry.IGeometryBakingContext
import net.minecraftforge.client.model.geometry.IGeometryLoader
import net.minecraftforge.client.model.geometry.IUnbakedGeometry
import java.util.function.Function
import kotlin.random.Random

object RandomModelLoader : IGeometryLoader<RandomModelLoader.Model> {
    private val LOGGER = LogUtils.getLogger()

    override fun read(jsonObject: JsonObject, deserializationContext: JsonDeserializationContext): Model {
        if (!jsonObject.has("models")) throw RuntimeException("Random Model Loader requires a 'models' element")
        val models = jsonObject.get("models").asJsonArray
        val id = if (jsonObject.has("id_supplier")) modelIdMap.getValue(ResourceLocation(jsonObject.get("id_supplier").asString)).invoke(models.size()) else Random.nextInt(models.size())
        return Model(id, deserializationContext.deserialize(models[id], BlockModel::class.java))
    }

    private val modelIdMap = mutableMapOf<ResourceLocation, (size: Int) -> Int>().withDefault {
        LOGGER.error("Could not find random model id supplier for '$it'")
        Random::nextInt
    }

    fun setIdSupplier(identifier: ResourceLocation, idSupplier: (size: Int) -> Int) {
        modelIdMap[identifier] = idSupplier
    }

    class Model(val id: Int, val model: BlockModel) : IUnbakedGeometry<Model> {
        override fun bake(
            context: IGeometryBakingContext,
            baker: ModelBaker,
            spriteGetter: Function<Material, TextureAtlasSprite>,
            modelState: ModelState,
            overrides: ItemOverrides,
            modelLocation: ResourceLocation
        ): BakedModel {
            return model.bake(baker, model, spriteGetter, modelState, modelLocation, true)
        }
    }
}
