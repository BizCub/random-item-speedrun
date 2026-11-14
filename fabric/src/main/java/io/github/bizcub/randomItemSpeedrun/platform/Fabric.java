package io.github.bizcub.randomItemSpeedrun.platform;

import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.network.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sounds.SoundEvents;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PlatformHooks.init(new FabricHooks());
        RandomItemSpeedrun.init();

        ServerLifecycleEvents.SERVER_STARTED.register(RandomItemSpeedrun::serverInit);
        ServerLifecycleEvents.SERVER_STOPPED.register(RandomItemSpeedrun::serverClose);

        PayloadTypeRegistry.serverboundPlay().register(ChangeGameStatusPayloadC2S.TYPE, ChangeGameStatusPayloadC2S.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(HUDPayloadS2C.TYPE, HUDPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SpeedrunsPayloadS2C.TYPE, SpeedrunsPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SoundPayloadS2C.TYPE, SoundPayloadS2C.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ChangeGameStatusPayloadC2S.TYPE, (payload, context) ->
                context.server().execute(() -> {
                    RandomItemSpeedrun.game.changeGameStatus(context.player());

                    context.server().getPlayerList().getPlayers().forEach(RandomItemSpeedrun::sendSpeedrunsS2C);

                    if (RandomItemSpeedrun.game.isStarted()) {
                        context.server().getPlayerList().getPlayers().forEach(serverPlayer -> {
                            RandomItemSpeedrun.sendAnimationS2C(serverPlayer);
                            RandomItemSpeedrun.sendSoundS2C(serverPlayer, SoundEvents.UI_TOAST_IN);
                        });
                    }
                })
        );

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            RandomItemSpeedrun.serverTick(server);
            server.getPlayerList().getPlayers().forEach(RandomItemSpeedrun::sendHUDS2C);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                RandomItemSpeedrun.sendSpeedrunsS2C(handler.player));
    }
}
