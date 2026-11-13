package io.github.bizcub.randomItemSpeedrun.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record AnimationPayloadS2C(ItemStack itemStack) implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("animation");

    public static final Type<AnimationPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationPayloadS2C> CODEC =
            StreamCodec.composite(ItemStack.OPTIONAL_STREAM_CODEC, AnimationPayloadS2C::itemStack, AnimationPayloadS2C::new);

    @Override
    public Type<AnimationPayloadS2C> type() {
        return TYPE;
    }
}
