//? forge {
/*package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
/^ >=1.19^/ import net.minecraftforge.client.ConfigScreenHandler;
/^ >=1.18 && <=1.18.2^/ /^import net.minecraftforge.client.ConfigGuiHandler;^/
/^ >=1.17 && <=1.17.1^/ /^import net.minecraftforge.fmlclient.ConfigGuiHandler;^/
/^ <=1.16.5^/ /^import net.minecraftforge.fml.ExtensionPoint;^/
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
/^? >=1.21.6^/ import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
/^? <=1.21.5^/ //import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class Forge {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(Constants.MY_KEYBIND);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        /^? >=1.21.9^/ if (event.getAction() == InputConstants.PRESS && Constants.MY_KEYBIND.isActiveAndMatches(InputConstants.getKey(event.getInfo()))) {
        /^? <=1.21.8^/ //if (event.getAction() == InputConstants.PRESS && Constants.MY_KEYBIND.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
            Minecraft.getInstance().setScreen(new GameStartScreen());
        }
    }

    @SubscribeEvent
    public static void onRenderGuiPost(CustomizeGuiOverlayEvent.Chat event) {
        Utils.renderHud(event.getGuiGraphics());
    }

    @Mod(Constants.MOD_ID)
    public static class Init {

        public Init() {
            Main.init();

            //? >=1.19 && <=1.21.3 {
            /^ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
                return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                    return PlatformInit.getScreen(screen);
                });
            });

            ^///?} >=1.17 && <=1.18.2 {
            /^ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                    () -> new ConfigGuiHandler.ConfigGuiFactory((client, parent) ->
                            PlatformInit.getScreen(parent))
            );

            ^///?} <=1.16.5 {
            /^ModLoadingContext.get().registerExtensionPoint(
                    ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) ->
                            PlatformInit.getScreen(screen)
            );^///?}
        }
    }
}*///?}
