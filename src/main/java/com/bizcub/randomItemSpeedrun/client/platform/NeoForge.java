//~ !graphics
//? neoforge {
/*package com.bizcub.randomItemSpeedrun.client.platform;

import com.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import com.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
/^? >=1.21.6^/ import com.bizcub.randomItemSpeedrun.client.gui.ScaledItemRenderState;
import com.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import com.bizcub.randomItemSpeedrun.util.Constants;
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

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForge {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(RandomItemSpeedrunClient.OPEN_SCREEN);
        event.register(RandomItemSpeedrunClient.QUICK_START);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == InputConstants.PRESS) {
            var inputConstants = InputConstants.getKey(
                    /^? >=1.21.9^/ event.getKeyEvent()
                    /^? <=1.21.8^/ //event.getKey(), event.getScanCode()
            );
            if (RandomItemSpeedrunClient.OPEN_SCREEN.isActiveAndMatches(inputConstants)) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
            if (RandomItemSpeedrunClient.QUICK_START.isActiveAndMatches(inputConstants)) {
                RandomItemSpeedrunMain.game.buttonPressed();
            }
        }
    }

    //? >=1.21.6 {
    @SubscribeEvent
    public static void registerPIPRenderers(RegisterPictureInPictureRenderersEvent event) {
        event.register(ScaledItemRenderState.class, ScaledItemPIPRenderer::new);
    }//?}

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        RandomItemSpeedrunClient.renderHud(event.getGuiGraphics());
    }

    @Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
    public static class Init {

        public Init(IEventBus modEventBus, ModContainer modContainer) {
            RandomItemSpeedrunClient.init();
        }
    }
}*///?}
