package io.github.bizcub.randomItemSpeedrun.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;

import java.util.List;

public record SpeedrunsPayloadS2C(List<Speedrun> speedruns) implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("speedruns");

    public static final Type<SpeedrunsPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SpeedrunsPayloadS2C> CODEC =
            StreamCodec.composite(
                    Speedrun.CODEC.apply(ByteBufCodecs.list()), SpeedrunsPayloadS2C::speedruns,
                    SpeedrunsPayloadS2C::new
            );

    @Override
    public Type<SpeedrunsPayloadS2C> type() {
        return TYPE;
    }
}
