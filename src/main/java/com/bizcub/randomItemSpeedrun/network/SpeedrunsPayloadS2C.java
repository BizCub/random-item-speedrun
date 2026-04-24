package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record SpeedrunsPayloadS2C(List<Speedrun> speedruns) implements CustomPacketPayload {

    public static final Type<SpeedrunsPayloadS2C> TYPE = new Type<>(Utils.getIdentifier("speedruns"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpeedrunsPayloadS2C> CODEC =
            StreamCodec.composite(
                    Speedrun.CODEC.apply(ByteBufCodecs.list()), SpeedrunsPayloadS2C::speedruns,
                    SpeedrunsPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
