package io.github.bizcub.randomItemSpeedrun.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;

public record ChangeGameStatusPayloadC2S() implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("change_game_status_c2s");

    public static final Type<ChangeGameStatusPayloadC2S> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeGameStatusPayloadC2S> CODEC =
            StreamCodec.unit(new ChangeGameStatusPayloadC2S());

    @Override
    public Type<ChangeGameStatusPayloadC2S> type() {
        return TYPE;
    }
}
