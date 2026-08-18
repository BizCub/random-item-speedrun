//? forge {
package io.github.bizcub.randomItemSpeedrun.main.platform;

import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelper;
import io.github.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import io.github.bizcub.randomItemSpeedrun.network.*;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;

import java.util.ArrayList;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class Forge {

    public static final Channel<CustomPacketPayload> CHANNEL =
            ChannelBuilder.named(Utils.getIdentifier("main"))
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
                            RandomItemSpeedrunMain.game.changeGameStatus();
                            ServerPlayer sender = ctx.getSender();
                            var players = sender.level().getServer().getPlayerList().getPlayers();
                            players.forEach(RandomItemSpeedrunMain::sendSpeedrunsS2C);
                            if (RandomItemSpeedrunMain.game.isStarted()) {
                                players.forEach(p -> {
                                    RandomItemSpeedrunMain.sendAnimationS2C(p);
                                    RandomItemSpeedrunMain.sendSoundS2C(p, SoundEvents.UI_TOAST_IN);
                                });
                            }
                        });
                        ctx.setPacketHandled(true);
                    })
                    .build();

    public Forge() {
        RandomItemSpeedrunMain.init();

        if (FMLEnvironment.dist.isClient()) {
            RandomItemSpeedrunClient.init();

            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                    new ConfigScreenHandler.ConfigScreenFactory(ConfigHelper::getScreen));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent.Post event) {
        RandomItemSpeedrunMain.serverTick(event.server());
        event.server().getPlayerList().getPlayers().forEach(RandomItemSpeedrunMain::sendHUDS2C);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            RandomItemSpeedrunMain.sendSpeedrunsS2C(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        RandomItemSpeedrunMain.serverInit(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RandomItemSpeedrunMain.serverClose(event.getServer());
    }
}//?}
