package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record HUDPayloadS2C(ItemStack itemStack, int time, boolean isStart) implements CustomPacketPayload {

    public static final Type<HUDPayloadS2C> TYPE = new Type<>(Utils.getIdentifier("render_hud"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HUDPayloadS2C> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, HUDPayloadS2C::itemStack,
                    ByteBufCodecs.INT, HUDPayloadS2C::time,
                    ByteBufCodecs.BOOL, HUDPayloadS2C::isStart,
                    HUDPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
