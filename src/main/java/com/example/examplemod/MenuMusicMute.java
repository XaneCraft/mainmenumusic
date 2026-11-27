package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT)
public final class MenuMusicMute {
    private MenuMusicMute() {
    }

    private static boolean inMenu() {
        return Minecraft.getInstance().level == null;
    }

    // Cancel music before it ever starts while in a menu
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (sound != null && sound.getSource() == SoundSource.MUSIC && inMenu()) {
            event.setSound(null);
        }
    }

    // Backup: stop anything that slipped through
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (inMenu()) {
            Minecraft.getInstance().getSoundManager().stop(null, SoundSource.MUSIC);
        }
    }
}
