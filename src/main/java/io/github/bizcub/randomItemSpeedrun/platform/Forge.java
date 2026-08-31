//? forge {
/*package io.github.bizcub.randomItemSpeedrun.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelper;
import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.network.*;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkDirection;

//? >=1.20.2 {
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
//?} else {
/^import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
^///?}

import java.util.ArrayList;
import java.util.Optional;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class Forge {

    //? >=1.20.5 {
    public static final Channel<CustomPacketPayload> CHANNEL =
            ChannelBuilder
                    .named(Utils.getIdentifier("main"))
                    .networkProtocolVersion(1)
                    .optional()
                    .payloadChannel()
                    .play()
                    .clientbound()
                    .add(AnimationPayloadS2C.TYPE, AnimationPayloadS2C.CODEC, (payload, ctx) -> {
                        ctx.enqueueWork(() -> Minecraft.getInstance().gameRenderer.displayItemActivation(payload.itemStack()));
                        ctx.setPacketHandled(true);
                    })
                    .add(SpeedrunsPayloadS2C.TYPE, SpeedrunsPayloadS2C.CODEC, (payload, ctx) -> {
                        ctx.enqueueWork(() -> RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns()));
                        ctx.setPacketHandled(true);
                    })
                    .add(SoundPayloadS2C.TYPE, SoundPayloadS2C.CODEC, (payload, ctx) -> {
                        ctx.enqueueWork(() -> Minecraft.getInstance().player.playSound(payload.soundEvent(), 1.0F, 1.0F));
                        ctx.setPacketHandled(true);
                    })
                    .add(HUDPayloadS2C.TYPE, HUDPayloadS2C.CODEC, (payload, ctx) -> {
                        ctx.enqueueWork(() -> RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time()));
                        ctx.setPacketHandled(true);
                    })
                    .serverbound()
                    .add(ChangeGameStatusPayloadC2S.TYPE, ChangeGameStatusPayloadC2S.CODEC, (payload, ctx) -> {
                        ctx.enqueueWork(() -> {
                            RandomItemSpeedrun.game.changeGameStatus();
                            ServerPlayer sender = ctx.getSender();
                            var players = sender.level().getServer().getPlayerList().getPlayers();
                            players.forEach(RandomItemSpeedrun::sendSpeedrunsS2C);
                            if (RandomItemSpeedrun.game.isStarted()) {
                                players.forEach(p -> {
                                    RandomItemSpeedrun.sendAnimationS2C(p);
                                    RandomItemSpeedrun.sendSoundS2C(p, SoundEvents.UI_TOAST_IN);
                                });
                            }
                        });
                        ctx.setPacketHandled(true);
                    })
                    .build();

    //?} >=1.20.2 {
    /^public static final SimpleChannel CHANNEL =
            ChannelBuilder
                    .named(Utils.getIdentifier("main"))
                    .networkProtocolVersion(1)
                    .acceptedVersions((status, ver) -> true)
                    .simpleChannel()
                    .messageBuilder(AnimationPayloadS2C.class, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder((payload, buf) -> buf.writeBytes(payload.toBuffer()))
                    .decoder(AnimationPayloadS2C::read)
                    .consumerMainThread((payload, ctx) ->
                            Minecraft.getInstance().gameRenderer.displayItemActivation(payload.itemStack()))
                    .add()
                    .messageBuilder(SpeedrunsPayloadS2C.class, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder((payload, buf) -> buf.writeBytes(payload.toBuffer()))
                    .decoder(SpeedrunsPayloadS2C::read)
                    .consumerMainThread((payload, ctx) ->
                            RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns()))
                    .add()
                    .messageBuilder(SoundPayloadS2C.class, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder((payload, buf) -> buf.writeBytes(payload.toBuffer()))
                    .decoder(SoundPayloadS2C::read)
                    .consumerMainThread((payload, ctx) ->
                            Minecraft.getInstance().player.playSound(payload.soundEvent(), 1.0F, 1.0F))
                    .add()
                    .messageBuilder(HUDPayloadS2C.class, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder((payload, buf) -> buf.writeBytes(payload.toBuffer()))
                    .decoder(HUDPayloadS2C::read)
                    .consumerMainThread((payload, ctx) ->
                            RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time()))
                    .add()
                    .messageBuilder(ChangeGameStatusPayloadC2S.class, NetworkDirection.PLAY_TO_SERVER)
                    .encoder((payload, buf) -> buf.writeBytes(payload.toBuffer()))
                    .decoder(ChangeGameStatusPayloadC2S::read)
                    .consumerMainThread((payload, ctx) -> {
                        RandomItemSpeedrun.game.changeGameStatus();
                        ServerPlayer sender = ctx.getSender();
                        var players = sender.level().getServer().getPlayerList().getPlayers();
                        players.forEach(RandomItemSpeedrun::sendSpeedrunsS2C);
                        if (RandomItemSpeedrun.game.isStarted()) {
                            players.forEach(p -> {
                                RandomItemSpeedrun.sendAnimationS2C(p);
                                RandomItemSpeedrun.sendSoundS2C(p, SoundEvents.UI_TOAST_IN);
                            });
                        }
                    })
                    .add();

    ^///?} else {
    /^private static final String NETWORK_PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            Utils.getIdentifier("main"),
            () -> NETWORK_PROTOCOL_VERSION,
            NETWORK_PROTOCOL_VERSION::equals,
            NETWORK_PROTOCOL_VERSION::equals
    );

    private static void registerPayloads() {
        int id = 0;
        CHANNEL.registerMessage(
                id++,
                AnimationPayloadS2C.class,
                (payload, buf) -> buf.writeBytes(payload.toBuffer()),
                AnimationPayloadS2C::read,
                (payload, ctx) ->
                        Minecraft.getInstance().gameRenderer.displayItemActivation(payload.itemStack()),
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        CHANNEL.registerMessage(
                id++,
                SpeedrunsPayloadS2C.class,
                (payload, buf) -> buf.writeBytes(payload.toBuffer()),
                SpeedrunsPayloadS2C::read,
                (payload, ctx) ->
                        RandomItemSpeedrunClient.speedruns = new ArrayList<>(payload.speedruns()),
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        CHANNEL.registerMessage(
                id++,
                SoundPayloadS2C.class,
                (payload, buf) -> buf.writeBytes(payload.toBuffer()),
                SoundPayloadS2C::read,
                (payload, ctx) ->
                        Minecraft.getInstance().player.playSound(payload.soundEvent(), 1.0F, 1.0F),
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        CHANNEL.registerMessage(
                id++,
                HUDPayloadS2C.class,
                (payload, buf) -> buf.writeBytes(payload.toBuffer()),
                HUDPayloadS2C::read,
                (payload, ctx) ->
                        RandomItemSpeedrunClient.game.update(payload.isStart(), payload.itemStack(), payload.time()),
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        CHANNEL.registerMessage(
                id++,
                ChangeGameStatusPayloadC2S.class,
                (payload, buf) -> buf.writeBytes(payload.toBuffer()),
                ChangeGameStatusPayloadC2S::read,
                (payload, ctx) -> {
                    RandomItemSpeedrun.game.changeGameStatus();
                    ServerPlayer sender = ctx.get().getSender();
                    var players = sender.level().getServer().getPlayerList().getPlayers();
                    players.forEach(RandomItemSpeedrun::sendSpeedrunsS2C);
                    if (RandomItemSpeedrun.game.isStarted()) {
                        players.forEach(p -> {
                            RandomItemSpeedrun.sendAnimationS2C(p);
                            RandomItemSpeedrun.sendSoundS2C(p, SoundEvents.UI_TOAST_IN);
                        });
                    }
                },
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }^///?}

    public Forge() {
        RandomItemSpeedrun.init();

        if (FMLEnvironment.dist.isClient()) {
            RandomItemSpeedrunClient.init();

            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                    new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> ConfigHelper.getScreen(parent)));
        }

        /^? 1.20.1^/ //registerPayloads();
    }

    @SubscribeEvent //~ if <=1.20.2 'ServerTickEvent.Post' -> 'ServerTickEvent'
    public static void onServerTick(TickEvent.ServerTickEvent.Post event) {
        /^? <=1.20.2^/ //if (event.phase != TickEvent.Phase.END) return;

        //~ if >=1.21.9 'event.getServer()' -> 'event.server()' {
        RandomItemSpeedrun.serverTick(event.server());
        event.server().getPlayerList().getPlayers().forEach(RandomItemSpeedrun::sendHUDS2C);//~}
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            RandomItemSpeedrun.sendSpeedrunsS2C(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        RandomItemSpeedrun.serverInit(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RandomItemSpeedrun.serverClose(event.getServer());
    }
}*///?}
