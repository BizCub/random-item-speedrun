//? fabric {
package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.network.AnimationPayloadS2C;
import com.bizcub.randomItemSpeedrun.network.ChangeGameStatusPayloadC2S;
import com.bizcub.randomItemSpeedrun.network.HUDPayloadS2C;
import com.bizcub.randomItemSpeedrun.network.SpeedrunsPayloadS2C;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//~ if >=26.1 'keybinding' -> 'keymapping'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//~ if >=26.1 'HudRenderCallback' -> 'hud.HudElementRegistry'
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Main.init();

        ClientPlayNetworking.registerGlobalReceiver(AnimationPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> context.client().gameRenderer.displayItemActivation(payload.itemStack())));

        ClientPlayNetworking.registerGlobalReceiver(SpeedrunsPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> Main.speedrunsRender = new ArrayList<>(payload.speedruns())));

        ClientPlayNetworking.registerGlobalReceiver(HUDPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> {
                    Main.gameRender.isStart = payload.isStart();
                    Main.gameRender.itemStack = payload.itemStack();
                    Main.gameRender.time = payload.time();
                })
        );

        /*? >=26.1 {*/ HudElementRegistry.addLast(Utils.getIdentifier("hud"),
        /*?} else*/ //HudRenderCallback.EVENT.register(
                (graphics, deltaTracker) ->
                        Utils.renderHud(graphics));

        KeyMappingHelper.registerKeyMapping(Constants.OPEN_SCREEN);
        KeyMappingHelper.registerKeyMapping(Constants.QUICK_START);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Constants.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
            while (Constants.QUICK_START.consumeClick()) {
                Main.game.buttonPressed();
            }
        });
    }

    public static class Server implements ModInitializer {

        @Override
        public void onInitialize() {
            Main.serverInit();

            PayloadTypeRegistry.serverboundPlay().register(ChangeGameStatusPayloadC2S.TYPE, ChangeGameStatusPayloadC2S.CODEC);
            PayloadTypeRegistry.clientboundPlay().register(HUDPayloadS2C.TYPE, HUDPayloadS2C.CODEC);
            PayloadTypeRegistry.clientboundPlay().register(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC);
            PayloadTypeRegistry.clientboundPlay().register(SpeedrunsPayloadS2C.TYPE, SpeedrunsPayloadS2C.CODEC);

            ServerPlayNetworking.registerGlobalReceiver(ChangeGameStatusPayloadC2S.TYPE, (payload, context) ->
                    context.server().execute(() -> {
                        Main.game.changeGameStatus();

                        context.server().getPlayerList().getPlayers().forEach(Utils::sendSpeedrunsS2C);

                        if (Main.game.isStarted())
                            context.server().getPlayerList().getPlayers().forEach(Utils::sendAnimationS2C);
                    })
            );

            ServerTickEvents.END_SERVER_TICK.register(server -> {
                Utils.serverTick(server);
                server.getPlayerList().getPlayers().forEach(Utils::sendHUDS2C);
            });

            ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                    Utils.sendSpeedrunsS2C(handler.player));
        }
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return Compat::getScreen;
        }
    }
}//?}
