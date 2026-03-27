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
//~ if >=26.1 'keybinding' -> 'keymapping'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
//~ if >=26.1 'HudRenderCallback' -> 'hud.HudElementRegistry'
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class Fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Main.init();

        /*? >=26.1*/ HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hud"),
        /*? <=1.21.11*/ //HudRenderCallback.EVENT.register(
                (graphics, deltaTracker) -> Utils.renderHud(graphics)
        );

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

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return PlatformInit::getScreen;
        }
    }
}//?}
