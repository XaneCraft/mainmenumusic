package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT)
public final class MenuMusicMute {
    private MenuMusicMute() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        // No world loaded = we're in a menu (title, create world, options, etc.)
        if (minecraft.level == null) {
            minecraft.getSoundManager().stop(null, SoundSource.MUSIC);
        }
    }
}
