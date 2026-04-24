package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;

public record SoundPayloadS2C(SoundEvent soundEvent) implements CustomPacketPayload {

    public static final Type<SoundPayloadS2C> TYPE = new Type<>(Utils.getIdentifier("sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPayloadS2C> CODEC =
            StreamCodec.composite(
                    SoundEvent.DIRECT_STREAM_CODEC, SoundPayloadS2C::soundEvent,
                    SoundPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
