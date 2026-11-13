package io.github.bizcub.randomItemSpeedrun.platform;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.fml.ModList;

public class ForgeHooks implements PlatformHooks {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.isLoaded(modId);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        Forge.CHANNEL.send(payload, PacketDistributor.PLAYER.with(player));
    }
}
