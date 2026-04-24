package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record AnimationPayloadS2C(ItemStack itemStack) implements CustomPacketPayload {

    public static final Type<AnimationPayloadS2C> TYPE = new Type<>(Utils.getIdentifier("animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationPayloadS2C> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, AnimationPayloadS2C::itemStack,
                    AnimationPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
