//? neoforge {
/*package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.gui.ScaledItemPIPRenderer;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.gui.ScaledItemRenderState;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
/^? >=1.21.6^/ import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForge {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(Constants.OPEN_SCREEN);
        event.register(Constants.QUICK_START);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == InputConstants.PRESS) {
            var inputConstants = InputConstants.getKey(
                    /^? >=1.21.9^/ event.getKeyEvent()
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

    @SubscribeEvent //~ !graphics
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Utils.renderHud(event.getGuiGraphicsExtractor());
    }

    @Mod(Constants.MOD_ID)
    public static class Init {

        public Init(IEventBus modEventBus, ModContainer modContainer) {
            Main.init();

            /^? >=1.21.6^/ modEventBus.addListener(this::onRegisterPIPRenderers);

            modContainer.registerExtensionPoint(
                    IConfigScreenFactory.class,
                    (container, parent) -> PlatformInit.getScreen(parent)
            );
        }

        //? >=1.21.6 {
        private void onRegisterPIPRenderers(RegisterPictureInPictureRenderersEvent event) {
            event.register(ScaledItemRenderState.class, ScaledItemPIPRenderer::new);
        }//?}
    }
}*///?}
