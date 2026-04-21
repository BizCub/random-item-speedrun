package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.util.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record AnimationPayloadC2S(ItemStack itemStack) implements CustomPacketPayload {

    public static final Type<AnimationPayloadC2S> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "c2s"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationPayloadC2S> CODEC =
            StreamCodec.composite(ItemStack.STREAM_CODEC, AnimationPayloadC2S::itemStack, AnimationPayloadC2S::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
