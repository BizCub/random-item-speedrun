package io.github.bizcub.randomItemSpeedrun.client.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelperClient;
import io.github.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
import io.github.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterPictureInPictureRendererEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.PacketDistributor;

public class Forge {

    public static void init() {
        RandomItemSpeedrunClient.init(payload -> io.github.bizcub.randomItemSpeedrun.platform.Forge.CHANNEL.send(payload, PacketDistributor.SERVER.noArg()));
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> ConfigHelperClient.getScreen(parent)));
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.FORGE)
    public static class ForgeBus {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
            if (RandomItemSpeedrunClient.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().gui.setScreen(new GameStartScreen());
            }
            if (RandomItemSpeedrunClient.QUICK_START.consumeClick()) {
                RandomItemSpeedrunClient.sendChangeGameStatus();
            }
        }

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(RandomItemSpeedrunClient.OPEN_SCREEN);
            event.register(RandomItemSpeedrunClient.QUICK_START);
        }

        @SubscribeEvent
        public static void registerPIPRenderers(RegisterPictureInPictureRendererEvent event) {
            event.register(new ScaledItemPIPRenderer());
        }

        @SubscribeEvent
        public static void onAddGuiOverlayLayers(AddGuiOverlayLayersEvent event) {
            event.getLayeredDraw().add(
                    Utils.getIdentifier("hud"),
                    (graphics, deltaTracker) -> RandomItemSpeedrunClient.renderHud(graphics)
            );
        }
    }
}
