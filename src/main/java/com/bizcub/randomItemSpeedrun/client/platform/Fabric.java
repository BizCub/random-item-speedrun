//? fabric {
package com.bizcub.randomItemSpeedrun.client.platform;

import com.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import com.bizcub.randomItemSpeedrun.client.config.Compat;
import com.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.network.AnimationPayloadS2C;
import com.bizcub.randomItemSpeedrun.network.HUDPayloadS2C;
import com.bizcub.randomItemSpeedrun.network.SoundPayloadS2C;
import com.bizcub.randomItemSpeedrun.network.SpeedrunsPayloadS2C;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//~ if >=26.1 'keybinding' -> 'keymapping'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//~ if >=26.1 'HudRenderCallback' -> 'hud.HudElementRegistry'
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RandomItemSpeedrunClient.init();

        //? >=1.20.3 {
        ClientPlayNetworking.registerGlobalReceiver(AnimationPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> context.client().gameRenderer.displayItemActivation(payload.itemStack())));

        ClientPlayNetworking.registerGlobalReceiver(SpeedrunsPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns())));

        ClientPlayNetworking.registerGlobalReceiver(SoundPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> context.player().playSound(payload.soundEvent(), 1.0F, 1.0F)));

        ClientPlayNetworking.registerGlobalReceiver(HUDPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time())));

        //?} else {
        /*ClientPlayNetworking.registerGlobalReceiver(AnimationPayloadS2C.ID, (client, listener, buf, sender) -> {
            AnimationPayloadS2C payload = AnimationPayloadS2C.read(buf);
            client.execute(() -> client.gameRenderer.displayItemActivation(payload.itemStack()));
        });

        ClientPlayNetworking.registerGlobalReceiver(SpeedrunsPayloadS2C.ID, (client, listener, buf, sender) -> {
            SpeedrunsPayloadS2C payload = SpeedrunsPayloadS2C.read(buf);
            client.execute(() -> RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns()));
        });

        ClientPlayNetworking.registerGlobalReceiver(SoundPayloadS2C.ID, (client, listener, buf, sender) -> {
            SoundPayloadS2C payload = SoundPayloadS2C.read(buf);
            client.execute(() -> client.player.playSound(payload.soundEvent(), 1.0F, 1.0F));
        });

        ClientPlayNetworking.registerGlobalReceiver(HUDPayloadS2C.ID, (client, listener, buf, sender) -> {
            HUDPayloadS2C payload = HUDPayloadS2C.read(buf);
            client.execute(() -> RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time()));
        });*///?}

        /*? >=26.1 {*/ HudElementRegistry.addLast(Utils.getIdentifier("hud"),
        /*?} else*/ //HudRenderCallback.EVENT.register(
                (graphics, deltaTracker) ->
                        RandomItemSpeedrunClient.renderHud(graphics));

        KeyMappingHelper.registerKeyMapping(RandomItemSpeedrunClient.OPEN_SCREEN);
        KeyMappingHelper.registerKeyMapping(RandomItemSpeedrunClient.QUICK_START);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (RandomItemSpeedrunClient.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
            while (RandomItemSpeedrunClient.QUICK_START.consumeClick()) {
                RandomItemSpeedrunClient.game.buttonPressed();
            }
        });
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return Compat::getScreen;
        }
    }
}//?}
