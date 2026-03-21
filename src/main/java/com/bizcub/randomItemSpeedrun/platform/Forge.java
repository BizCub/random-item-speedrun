//? forge {
/*package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class Forge {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(Constants.OPEN_SCREEN);
        event.register(Constants.QUICK_START);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == InputConstants.PRESS) {
            var inputConstants = InputConstants.getKey(
                    /^? >=1.21.9^/ event.getInfo()
                    /^? <=1.21.8^/ //event.getKey(), event.getScanCode()
            );
            if (Constants.OPEN_SCREEN.isActiveAndMatches(inputConstants)) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
            if (Constants.QUICK_START.isActiveAndMatches(inputConstants)) {
                Main.game.buttonPressed();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGuiPost(CustomizeGuiOverlayEvent.Chat event) {
        Utils.renderHud(event.getGuiGraphicsExtractor());
    }

    @Mod(Constants.MOD_ID)
    public static class Init {

        public Init() {
            Main.init();

            //? <=1.21.3 {
            /^ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                    new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> PlatformInit.getScreen(screen)));^///?}
        }
    }
}*///?}
