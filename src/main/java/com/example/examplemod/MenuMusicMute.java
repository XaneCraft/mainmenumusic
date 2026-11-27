package com.example.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@Mod(value = "examplemod", dist = Dist.CLIENT)
@EventBusSubscriber(modid = "examplemod", value = Dist.CLIENT)
public class MenuMusicMute {
    private static final org.slf4j.Logger LOG = LogUtils.getLogger();

    public MenuMusicMute() {
    }

    private static boolean inMenu() {
        return Minecraft.getInstance().level == null;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (sound == null) return;

        String id = sound.getIdentifier().toString();
        if (sound.getSource() == SoundSource.MUSIC || id.contains("music")) {
            LOG.info("MenuMusicMute saw: id={} source={} inMenu={}", id, sound.getSource(), inMenu());
        }

        if (sound.getSource() != SoundSource.MUSIC) return;

        boolean isVanilla = "minecraft".equals(sound.getIdentifier().getNamespace());

        // Block all music in menus, and block vanilla music everywhere
        if (inMenu() || isVanilla) {
            event.setSound(null);
        }
    }

    // Backup for menus: stop anything that slipped through
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (inMenu()) {
            Minecraft.getInstance().getSoundManager().stop(null, SoundSource.MUSIC);
        }
    }
}
