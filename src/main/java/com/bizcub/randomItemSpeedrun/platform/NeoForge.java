//? neoforge {
/*package com.bizcub.randomItemSpeedrun.platform;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.gui.ZoomedItemPIPRenderer;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.gui.ZoomedItemRenderState;
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
        event.register(Constants.MY_KEYBIND);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == InputConstants.PRESS &&
                Constants.MY_KEYBIND.isActiveAndMatches(InputConstants.getKey(
                        /^? >=1.21.9^/ //event.getKeyEvent()
                        /^? <=1.21.8^/ event.getKey(), event.getScanCode()
        ))) {
            Minecraft.getInstance().setScreen(new GameStartScreen());
        }
    }

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Utils.renderHud(event.getGuiGraphics());
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
            event.register(ZoomedItemRenderState.class, ZoomedItemPIPRenderer::new);
        }//?}
    }
}*///?}
