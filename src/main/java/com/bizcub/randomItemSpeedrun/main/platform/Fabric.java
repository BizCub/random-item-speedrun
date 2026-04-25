//? fabric {
package com.bizcub.randomItemSpeedrun.main.platform;

import com.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import com.bizcub.randomItemSpeedrun.network.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
/*? >=1.20.3*/ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sounds.SoundEvents;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        RandomItemSpeedrunMain.init();

        //? >=1.20.3 {
        /*~ if >=26.1 'playC2S' -> 'serverboundPlay'*/ /*~ if >=26.1 'playS2C' -> 'clientboundPlay' {*/
        PayloadTypeRegistry.serverboundPlay().register(ChangeGameStatusPayloadC2S.TYPE, ChangeGameStatusPayloadC2S.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(HUDPayloadS2C.TYPE, HUDPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SpeedrunsPayloadS2C.TYPE, SpeedrunsPayloadS2C.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SoundPayloadS2C.TYPE, SoundPayloadS2C.CODEC);/*~}*/ //?}

        //? >=1.20.3 {
        ServerPlayNetworking.registerGlobalReceiver(ChangeGameStatusPayloadC2S.TYPE, (payload, context) ->
                context.server().execute(() -> {
                    RandomItemSpeedrunMain.game.changeGameStatus();

                    context.server().getPlayerList().getPlayers().forEach(RandomItemSpeedrunMain::sendSpeedrunsS2C);

                    if (RandomItemSpeedrunMain.game.isStarted()) {
                        context.server().getPlayerList().getPlayers().forEach(serverPlayer -> {
                            RandomItemSpeedrunMain.sendAnimationS2C(serverPlayer);
                            RandomItemSpeedrunMain.sendSoundS2C(serverPlayer, SoundEvents.UI_TOAST_IN);
                        });
                    }
                })
        );

        //?} else {
        /*ServerPlayNetworking.registerGlobalReceiver(ChangeGameStatusPayloadC2S.ID, (server, player, listener, buf, sender) ->
                server.execute(() -> {
                    RandomItemSpeedrunMain.game.changeGameStatus();

                    server.getPlayerList().getPlayers().forEach(RandomItemSpeedrunMain::sendSpeedrunsS2C);

                    if (RandomItemSpeedrunMain.game.isStarted()) {
                        server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                            RandomItemSpeedrunMain.sendAnimationS2C(serverPlayer);
                            RandomItemSpeedrunMain.sendSoundS2C(serverPlayer, SoundEvents.UI_TOAST_IN);
                        });
                    }
                })
        );*///?}

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            RandomItemSpeedrunMain.serverTick(server);
            server.getPlayerList().getPlayers().forEach(RandomItemSpeedrunMain::sendHUDS2C);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                RandomItemSpeedrunMain.sendSpeedrunsS2C(handler.player));
    }
}//?}
