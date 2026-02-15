//? fabric {
package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Main.init();

        HudRenderCallback.EVENT.register((guiGraphics, deltaTracker) -> Utils.renderHud(guiGraphics));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Constants.MY_KEYBIND.consumeClick()) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
        });
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return PlatformInit::getScreen;
        }
    }
}//?}
