package io.github.bizcub.randomItemSpeedrun.client.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelperClient;
import io.github.bizcub.randomItemSpeedrun.client.gui.GameStartScreen;
import io.github.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
import io.github.bizcub.randomItemSpeedrun.client.gui.ScaledItemRenderState;
import io.github.bizcub.randomItemSpeedrun.platform.NeoForgeHooks;
import io.github.bizcub.randomItemSpeedrun.platform.PlatformHooks;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
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
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

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
            var inputConstants = InputConstants.getKey(event.getKeyEvent());
            if (RandomItemSpeedrunClient.OPEN_SCREEN.isActiveAndMatches(inputConstants)) {
                Minecraft.getInstance().gui.setScreen(new GameStartScreen());
            }
            if (RandomItemSpeedrunClient.QUICK_START.isActiveAndMatches(inputConstants)) {
                RandomItemSpeedrunClient.sendChangeGameStatus();
            }
        }
    }

    @SubscribeEvent
    public static void registerPIPRenderers(RegisterPictureInPictureRenderersEvent event) {
        event.register(ScaledItemRenderState.class, ScaledItemPIPRenderer::new);
    }

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        RandomItemSpeedrunClient.renderHud(event.getGuiGraphics());
    }

    @Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
    public static class Init {

        public Init(IEventBus modEventBus, ModContainer modContainer) {
            PlatformHooks.init(NeoForgeHooks.INSTANCE);
            RandomItemSpeedrunClient.init(ClientPacketDistributor::sendToServer);

            modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                    (container, parent) -> ConfigHelperClient.getScreen(parent));
        }
    }
}
