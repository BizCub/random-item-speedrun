package io.github.bizcub.randomItemSpeedrun.client.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelperClient;
import io.github.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
import io.github.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
import io.github.bizcub.randomItemSpeedrun.network.AnimationPayloadS2C;
import io.github.bizcub.randomItemSpeedrun.network.HUDPayloadS2C;
import io.github.bizcub.randomItemSpeedrun.network.SoundPayloadS2C;
import io.github.bizcub.randomItemSpeedrun.network.SpeedrunsPayloadS2C;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.PictureInPictureRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RandomItemSpeedrunClient.init(ClientPlayNetworking::send);

        ClientPlayNetworking.registerGlobalReceiver(AnimationPayloadS2C.TYPE, (payload, context) ->

                context.client().execute(() -> context.player().displayItemActivation(payload.itemStack())));

        ClientPlayNetworking.registerGlobalReceiver(SpeedrunsPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns())));

        ClientPlayNetworking.registerGlobalReceiver(SoundPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> context.player().playSound(payload.soundEvent(), 1.0F, 1.0F)));

        ClientPlayNetworking.registerGlobalReceiver(HUDPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time())));

        HudElementRegistry.addLast(Utils.getIdentifier("hud"),

                (graphics, deltaTracker) ->
                        RandomItemSpeedrunClient.renderHud(graphics));

        PictureInPictureRendererRegistry.register(ctx -> new ScaledItemPIPRenderer());

        KeyMappingHelper.registerKeyMapping(RandomItemSpeedrunClient.OPEN_SCREEN);
        KeyMappingHelper.registerKeyMapping(RandomItemSpeedrunClient.QUICK_START);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (RandomItemSpeedrunClient.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().gui.setScreen(new GameStartScreen());
            }
            while (RandomItemSpeedrunClient.QUICK_START.consumeClick()) {
                RandomItemSpeedrunClient.sendChangeGameStatus();
            }
        });
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return ConfigHelperClient::getScreen;
        }
    }
}
