package io.github.bizcub.randomItemSpeedrun.platform;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public interface PlatformHooks {

    static PlatformHooks get() {
        return Objects.requireNonNull(Holder.instance, "Platform hooks have not been initialized");
    }

    static void init(PlatformHooks platform) {
        Holder.instance = platform;
    }

    boolean isModLoaded(String modId);

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    class Holder {
        private static volatile PlatformHooks instance;
    }
}
