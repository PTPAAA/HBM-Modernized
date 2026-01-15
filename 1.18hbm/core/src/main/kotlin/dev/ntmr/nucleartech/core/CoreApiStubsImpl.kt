/*
SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.nucleartech.core

import dev.ntmr.nucleartech.core.registries.NTechRegistry
import dev.ntmr.sorcerer.generated.spells.main.ApiStubs
import dev.ntmr.sorcerer.generated.spells.main.net.minecraftforge.registries.DeferredRegister
import dev.ntmr.sorcerer.generated.spells.main.net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

open class CoreApiStubsImpl(private val stubs: ApiStubs) : ApiStubs by stubs {
    override fun <T, I : T> DeferredRegister<T>.registerStub(name: String, sup: Supplier<out I>): RegistryObject<I> {
        var result: RegistryObject<I>? = null
        NTechRegistry.runHooks(getRegistryName()!!, name, object : NTechRegistry.RegistryCallback<I> {
            override fun registerNow(): RegistryObject<I> {
                result = register(name, sup)
                return result!!
            }

            override fun failIfRegistered() {
                if (result != null)
                    throw IllegalStateException("Registry item '$name' in registry '${getRegistryName()}' was already registered")
            }

            override fun getSup(): () -> I {
                return { sup.get() }
            }

            override fun swap(newSup: () -> I) {
                result = register(name, newSup)
            }

            override fun <R : I> register(name: String, sup: () -> R): RegistryObject<R> {
                return this@registerStub.register(name, sup)
            }
        })
        return result ?: with(stubs) { registerStub(name, sup) }
    }
}
