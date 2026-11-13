package io.github.bizcub.randomItemSpeedrun.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public record SoundPayloadS2C(SoundEvent soundEvent) implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("sound");

    public static final Type<SoundPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPayloadS2C> CODEC =
            StreamCodec.composite(
                    SoundEvent.DIRECT_STREAM_CODEC, SoundPayloadS2C::soundEvent,
                    SoundPayloadS2C::new
            );

    @Override
    public Type<SoundPayloadS2C> type() {
        return TYPE;
    }
}
