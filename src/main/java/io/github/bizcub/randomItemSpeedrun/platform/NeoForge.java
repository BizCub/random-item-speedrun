//? neoforge {
/*package io.github.bizcub.randomItemSpeedrun.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.network.*;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForge {

    @SubscribeEvent
    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);

        registrar.playToClient(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC, (payload, context) ->
                context.enqueueWork(() -> Minecraft.getInstance().gameRenderer.displayItemActivation(payload.itemStack())));

        registrar.playToClient(SpeedrunsPayloadS2C.TYPE, SpeedrunsPayloadS2C.CODEC, (payload, context) ->
                context.enqueueWork(() -> RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns())));

        registrar.playToClient(SoundPayloadS2C.TYPE, SoundPayloadS2C.CODEC, (payload, context) ->
                context.enqueueWork(() -> context.player().playSound(payload.soundEvent(), 1.0F, 1.0F)));

        registrar.playToClient(HUDPayloadS2C.TYPE, HUDPayloadS2C.CODEC, (payload, context) ->
                context.enqueueWork(() -> RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time())));

        registrar.playToServer(ChangeGameStatusPayloadC2S.TYPE, ChangeGameStatusPayloadC2S.CODEC, (payload, context) ->
                context.enqueueWork(() -> {
                    RandomItemSpeedrun.game.changeGameStatus();

                    PacketDistributor.sendToAllPlayers(new SpeedrunsPayloadS2C(RandomItemSpeedrun.speedruns));

                    if (RandomItemSpeedrun.game.isStarted()) {
                        PacketDistributor.sendToAllPlayers(new AnimationPayloadS2C(RandomItemSpeedrun.game.getItemStack()));
                        PacketDistributor.sendToAllPlayers(new SoundPayloadS2C(SoundEvents.UI_TOAST_IN));
                    }
                })
        );
    }

    @SubscribeEvent
    public static void onServerTickEnd(ServerTickEvent.Post event) {
        RandomItemSpeedrun.serverTick(event.getServer());
        event.getServer().getPlayerList().getPlayers().forEach(RandomItemSpeedrun::sendHUDS2C);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            RandomItemSpeedrun.sendSpeedrunsS2C(serverPlayer);
        }
    }

    @Mod(Constants.MOD_ID)
    public static class Init {

        public Init(IEventBus modEventBus, ModContainer modContainer) {
            RandomItemSpeedrun.init();

            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener((ServerStartedEvent event) ->
                    RandomItemSpeedrun.serverInit(event.getServer())
            );

            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener((ServerStoppedEvent event) ->
                    RandomItemSpeedrun.serverClose(event.getServer())
            );
        }
    }
}*///?}
