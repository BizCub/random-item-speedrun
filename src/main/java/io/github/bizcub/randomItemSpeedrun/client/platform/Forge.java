//~ !graphics
//? forge {
/*package io.github.bizcub.randomItemSpeedrun.client.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
/^? >=1.21.6^/ import io.github.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
import io.github.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
/^? >=1.20.6 {^/ import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
/^?} else^/ //import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
/^? >=1.21.6^/ import net.minecraftforge.client.event.RegisterPictureInPictureRendererEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class Forge {

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.FORGE)
    public static class ForgeBus {

        @SubscribeEvent //~ if <=1.20.2 'ClientTickEvent.Post' -> 'ClientTickEvent'
        public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
            if (RandomItemSpeedrunClient.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().gui.setScreen(new GameStartScreen());
            }
            if (RandomItemSpeedrunClient.QUICK_START.consumeClick()) {
                RandomItemSpeedrunMain.game.buttonPressed();
            }
        }
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBus {

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(RandomItemSpeedrunClient.OPEN_SCREEN);
            event.register(RandomItemSpeedrunClient.QUICK_START);
        }

        //? >=1.21.6 {
        @SubscribeEvent
        public static void registerPIPRenderers(RegisterPictureInPictureRendererEvent event) {
            event.register(new ScaledItemPIPRenderer(/^? <26.2 >>+ ')'^/ /^event.getBufferSource()^/));
        }//?}

        //? >=1.20.6 {
        @SubscribeEvent
        public static void onAddGuiOverlayLayers(AddGuiOverlayLayersEvent event) {
            event.getLayeredDraw().add(
                    Utils.getIdentifier("hud"),
                    (graphics, deltaTracker) -> RandomItemSpeedrunClient.renderHud(graphics)
            );
        }

        //?} else {
        /^@SubscribeEvent
        public static void onAddGuiOverlayLayers(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll(
                    Constants.MOD_ID + "hud",
                    (gui, graphics, partialTick, width, height) -> RandomItemSpeedrunClient.renderHud(graphics)
            );
        }^///?}
    }
}*///?}
