package dev.ntmr.nucleartech.datagen.provider

import dev.ntmr.nucleartech.MaterialGroup
import dev.ntmr.nucleartech.datagen.provider.localisation.DeDeLanguageProvider
import dev.ntmr.nucleartech.datagen.provider.localisation.EnUsLanguageProvider
import dev.ntmr.nucleartech.datagen.provider.localisation.ItItLanguageProvider
import dev.ntmr.nucleartech.datagen.provider.localisation.KoKrLanguageProvider
import dev.ntmr.nucleartech.datagen.provider.localisation.PlPlLanguageProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

object NuclearLanguageProviders {
    lateinit var keys: Set<String>
    lateinit var materialTranslations: Map<MaterialGroup, String>

    fun getLanguageProviders(dataGenerator: DataGenerator) = listOf<LanguageProvider>(
        EnUsLanguageProvider(dataGenerator), // ensure this runs first for data validation
        DeDeLanguageProvider(dataGenerator),
        ItItLanguageProvider(dataGenerator),
        KoKrLanguageProvider(dataGenerator),
        PlPlLanguageProvider(dataGenerator),
    )

    const val EN_US = "en_us"
    const val DE_DE = "de_de"
    const val IT_IT = "it_it"
    const val KO_KR = "ko_kr"
    const val PL_PL = "pl_pl"
}
