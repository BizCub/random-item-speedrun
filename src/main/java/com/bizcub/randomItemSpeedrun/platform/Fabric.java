//? fabric {
package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.network.AnimationPayloadC2S;
import com.bizcub.randomItemSpeedrun.network.AnimationPayloadS2C;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//~ if >=26.1 'keybinding' -> 'keymapping'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
//~ if >=26.1 'HudRenderCallback' -> 'hud.HudElementRegistry'
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Main.init();

        ClientPlayNetworking.registerGlobalReceiver(AnimationPayloadS2C.TYPE, (payload, context) ->
                context.client().execute(() -> context.client().gameRenderer.displayItemActivation(payload.itemStack())));

        /*? >=26.1*/ HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hud"),
        /*? <=1.21.11*/ //HudRenderCallback.EVENT.register(
                (graphics, deltaTracker) -> Utils.renderHud(graphics));

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
            PayloadTypeRegistry.serverboundPlay().register(AnimationPayloadC2S.TYPE, AnimationPayloadC2S.CODEC);
            PayloadTypeRegistry.clientboundPlay().register(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC);

            ServerPlayNetworking.registerGlobalReceiver(AnimationPayloadC2S.TYPE, (payload, context) ->
                    context.server().execute(() -> context.server().getPlayerList().getPlayers().forEach(player ->
                            ServerPlayNetworking.send(player, new AnimationPayloadS2C(payload.itemStack())))));
        }
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return Compat::getScreen;
        }
    }
}//?}
