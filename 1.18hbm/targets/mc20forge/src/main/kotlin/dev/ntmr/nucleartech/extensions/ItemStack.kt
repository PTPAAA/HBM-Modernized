package dev.ntmr.nucleartech.extensions

import com.google.gson.JsonObject
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.ForgeRegistries

fun ItemStack.toJson() = JsonObject().apply {
    addProperty("item", ForgeRegistries.ITEMS.getKey(item)?.toString() ?: throw IllegalStateException("Unregistered item in ItemStack!"))
    if (count != 1) addProperty("count", count)
    if (hasTag()) addProperty("nbt", tag!!.toString())
}
