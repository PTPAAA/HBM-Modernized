package dev.ntmr.nucleartech.datagen.provider

import dev.ntmr.nucleartech.LangKeys
import dev.ntmr.nucleartech.MODID
import dev.ntmr.nucleartech.NTechSounds
import dev.ntmr.nucleartech.TranslationKey
import dev.ntmr.nucleartech.content.NTechItems
import dev.ntmr.nucleartech.ntm
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.SoundDefinition
import net.minecraftforge.common.data.SoundDefinitionsProvider

class NuclearSoundsProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : SoundDefinitionsProvider(dataGenerator, MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Sounds"

    override fun registerSounds() {
        add(NTechSounds.anvilFall, definition().with(sound(ntm("block/anvil_fall_berserk"))).subtitle(LangKeys.SUBTITLE_ANVIL_FALL))
        add(NTechSounds.assemblerOperate, definition().with(sound(ntm("block/assembler/assembler_operate"))).subtitle(LangKeys.SUBTITLE_ASSEMBLER_OPERATE))
        add(NTechSounds.chemPlantOperate, definition().with(sound(ntm("block/chem_plant/chem_plant_operate"))).subtitle(LangKeys.SUBTITLE_CHEM_PLANT_OPERATE))
        add(NTechSounds.centrifugeOperate, definition().with(sound(ntm("block/centrifuge/operate"))).subtitle(LangKeys.SUBTITLE_CENTRIFUGE_OPERATE))
        add(NTechSounds.debris, definition().with(sound(ntm("block/debris1")), sound(ntm("block/debris2")), sound(ntm("block/debris3"))).subtitle(LangKeys.SUBTITLE_BLOCK_DEBRIS))
        add(NTechSounds.emptyIVBag, definition().with(sound(ntm("item/use/radaway"))).subtitle(LangKeys.SUBTITLE_USE_RADAWAY))
        add(NTechSounds.geiger1, definition().with(sound(ntm("geiger/geiger1"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.geiger2, definition().with(sound(ntm("geiger/geiger2"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.geiger3, definition().with(sound(ntm("geiger/geiger3"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.geiger4, definition().with(sound(ntm("geiger/geiger4"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.geiger5, definition().with(sound(ntm("geiger/geiger5"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.geiger6, definition().with(sound(ntm("geiger/geiger6"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSounds.inject, definition().with(sound(ntm("item/use/inject"))).subtitle(LangKeys.SUBTITLE_ITEM_INJECT))
        add(NTechSounds.installUpgrade, definition().with(sound(ntm("item/install_upgrade"))).subtitle(LangKeys.SUBTITLE_UPGRADE_INSTALL))
        add(NTechSounds.miniNukeExplosion, definition().with(sound(ntm("weapon/mini_nuke_explosion"))).subtitle(LangKeys.SUBTITLE_MINI_NUKE_EXPLODE))
        add(NTechSounds.missileTakeoff, definition().with(sound(ntm("weapon/missile_takeoff"))).subtitle(LangKeys.SUBTITLE_MISSILE_TAKEOFF))
        add(NTechSounds.pressOperate, definition().with(sound(ntm("block/press/press_operate"))).subtitle(LangKeys.SUBTITLE_PRESS_OPERATE))
        add(NTechSounds.randomBleep, definition().with(sound(ntm("random/bleep"))).subtitle(LangKeys.SUBTITLE_BLEEP))
        add(NTechSounds.randomBoop, definition().with(sound(ntm("random/boop"))).subtitle(LangKeys.SUBTITLE_BOOP))
        add(NTechSounds.randomUnpack, definition().with(sound(ntm("random/unpack1")), sound(ntm("random/unpack2"))).subtitle(LangKeys.SUBTITLE_ITEM_UNPACK))
        add(NTechSounds.rbmkAz5Cover, definition().with(sound(ntm("block/rbmk_az5_cover"))).subtitle(LangKeys.SUBTITLE_RBMK_AZ5_COVER))
        add(NTechSounds.rbmkExplosion, definition().with(sound(ntm("block/rbmk_explosion")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_EXPLODE))
        add(NTechSounds.rbmkShutdown, definition().with(sound(ntm("block/rbmk_shutdown")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_SHUTDOWN))
        add(NTechSounds.meteorImpact, definition().with(sound(ntm("entity/meteor_impact"))).subtitle(LangKeys.SUBTITLE_METEOR_IMPACT))
        add(NTechSounds.sirenTrackAMSSiren, definition().with(sound(ntm("block/siren/ams")).attenuationDistance(NTechItems.sirenTrackAMSSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_AMS))
        add(NTechSounds.sirenTrackAPCPass, definition().with(sound(ntm("block/siren/apc_pass")).attenuationDistance(NTechItems.sirenTrackAPCPass.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_APC_PASS))
        add(NTechSounds.sirenTrackAPCSiren, definition().with(sound(ntm("block/siren/apc")).attenuationDistance(NTechItems.sirenTrackAPCSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_APC))
        add(NTechSounds.sirenTrackAirRaidSiren, definition().with(sound(ntm("block/siren/air_raid")).attenuationDistance(NTechItems.sirenTrackAirRaidSiren.get().range).stream()).subtitle(LangKeys.SUBTITLE_SIREN_AIR_RAID))
        add(NTechSounds.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("block/siren/autopilot_disconnected")).attenuationDistance(NTechItems.sirenTrackAutopilotDisconnected.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_AUTOPILOT_DISCONNECTED))
        add(NTechSounds.sirenTrackBankAlarm, definition().with(sound(ntm("block/siren/bank")).attenuationDistance(NTechItems.sirenTrackBankAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BANK))
        add(NTechSounds.sirenTrackBeepSiren, definition().with(sound(ntm("block/siren/beep")).attenuationDistance(NTechItems.sirenTrackBeepSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BEEP))
        add(NTechSounds.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("block/siren/blast_door")).attenuationDistance(NTechItems.sirenTrackBlastDoorAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BLAST_DOOR))
        add(NTechSounds.sirenTrackClassicSiren, definition().with(sound(ntm("block/siren/classic")).attenuationDistance(NTechItems.sirenTrackClassicSiren.get().range).stream()).subtitle(LangKeys.SUBTITLE_SIREN_CLASSIC))
        add(NTechSounds.sirenTrackContainerAlarm, definition().with(sound(ntm("block/siren/container")).attenuationDistance(NTechItems.sirenTrackContainerAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_CONTAINER))
        add(NTechSounds.sirenTrackEASAlarmScreech, definition().with(sound(ntm("block/siren/eas")).attenuationDistance(NTechItems.sirenTrackEASAlarmScreech.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_EAS))
        add(NTechSounds.sirenTrackHatchSiren, definition().with(sound(ntm("block/siren/hatch")).attenuationDistance(NTechItems.sirenTrackHatchSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_HATCH))
        add(NTechSounds.sirenTrackKlaxon, definition().with(sound(ntm("block/siren/klaxon")).attenuationDistance(NTechItems.sirenTrackKlaxon.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_KLAXON))
        add(NTechSounds.sirenTrackMissileSiloSiren, definition().with(sound(ntm("block/siren/missile_silo")).attenuationDistance(NTechItems.sirenTrackMissileSiloSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_MISSILE_SILO))
        add(NTechSounds.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("block/siren/nostromo_self_destruct")).attenuationDistance(NTechItems.sirenTrackNostromoSelfDestruct.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_NOSTROMO_SELF_DESTRUCT))
        add(NTechSounds.sirenTrackRazortrainHorn, definition().with(sound(ntm("block/siren/razortrain_horn")).attenuationDistance(NTechItems.sirenTrackRazortrainHorn.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_RAZORTRAIN_HORN))
        add(NTechSounds.sirenTrackSecurityAlert, definition().with(sound(ntm("block/siren/security")).attenuationDistance(NTechItems.sirenTrackSecurityAlert.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_SECURITY))
        add(NTechSounds.sirenTrackStandardSiren, definition().with(sound(ntm("block/siren/standard")).attenuationDistance(NTechItems.sirenTrackStandardSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_STANDARD))
        add(NTechSounds.sirenTrackSweepSiren, definition().with(sound(ntm("block/siren/sweep")).attenuationDistance(NTechItems.sirenTrackSweepSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_SWEEP))
        add(NTechSounds.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("block/siren/vault_door")).attenuationDistance(NTechItems.sirenTrackVaultDoorAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_VAULT_DOOR))
    }

    private fun SoundDefinition.subtitle(key: TranslationKey) = subtitle(key.key)
}
