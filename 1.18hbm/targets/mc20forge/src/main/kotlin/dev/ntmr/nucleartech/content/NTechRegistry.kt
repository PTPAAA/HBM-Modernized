/*
 * SPDX-FileCopyrightText: 2025 MartinTheDragon <martin@ntmr.dev>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.content

import dev.ntmr.nucleartech.MODID
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject

interface NTechRegistry<V> {
    val forgeRegistry: DeferredRegister<V>

    fun <R : V> register(name: String, sup: () -> R): RegistryObject<R> = forgeRegistry.register(name, sup)

    fun <R : V> registerBefore(target: String, name: String, sup: () -> R): RegistryObject<R> {
        // addHook(RegistryHook.Before<V>(ForgeRegistries.FLUIDS.getKey(forgeRegistry)!!, target) { callback -> ... })
        return forgeRegistry.register(name, sup) // Fallback to normal registration
    }

    fun <R : V> registerAfter(target: String, name: String, sup: () -> R): RegistryObject<R> {
        // addHook(RegistryHook.After<V>(ForgeRegistries.FLUIDS.getKey(forgeRegistry)!!, target) { callback, _ -> ... })
        return forgeRegistry.register(name, sup) // Fallback to normal registration
    }

    interface RegistryCallback<V> {
        fun registerNow(): RegistryObject<V>
        fun failIfRegistered()
        fun getSup(): () -> V
        fun swap(newSup: () -> V)

        fun <R : V> register(name: String, sup: () -> R): RegistryObject<R>
    }

    interface RegistryHook<V> {
        fun onRegister(registry: ResourceLocation, name: String, callback: RegistryCallback<*>): Boolean

        class Before<V>(
            val registry: ResourceLocation,
            val entry: String,
            val action: (callback: RegistryCallback<V>) -> Unit
        ) : RegistryHook<V> {
            override fun onRegister(registry: ResourceLocation, name: String, callback: RegistryCallback<*>): Boolean {
                if (registry === this.registry && entry == name) {
                    callback.failIfRegistered()
                    action(callback.cast())
                    return true
                }
                return false
            }
        }

        class After<V>(
            val registry: ResourceLocation,
            val entry: String,
            val action: (callback: RegistryCallback<V>, entry: RegistryObject<V>) -> Unit
        ): RegistryHook<V> {
            override fun onRegister(registry: ResourceLocation, name: String, callback: RegistryCallback<*>): Boolean {
                if (registry === this.registry && entry == name) {
                    action(callback.cast(), callback.cast<V>().registerNow())
                    return true
                }
                return false
            }
        }

        class Modify<V>(
            val registry: ResourceLocation,
            val entry: String,
            val modifier: (callback: RegistryCallback<V>, sup: () -> V) -> (() -> V)
        ): RegistryHook<V> {
            override fun onRegister(registry: ResourceLocation, name: String, callback: RegistryCallback<*>): Boolean {
                if (registry === this.registry && entry == name) {
                    callback.cast<V>().swap(modifier(callback.cast(), callback.cast<V>().getSup()))
                    return true
                }
                return false
            }
        }
    }

    companion object {
        private val REGISTRY_HOOKS = mutableListOf<RegistryHook<*>>()

        fun addHook(hook: RegistryHook<*>) {
            REGISTRY_HOOKS += hook
        }

        fun runHooks(registry: ResourceLocation, name: String, callback: RegistryCallback<*>) {
            REGISTRY_HOOKS.removeAll { it.onRegister(registry, name, callback) }
        }

        @Suppress("UNCHECKED_CAST")
        fun <X> RegistryCallback<*>.cast(): RegistryCallback<X> {
            return this as RegistryCallback<X>
        }
    }
}

