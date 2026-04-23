package com.bizcub.randomItemSpeedrun.network;

import com.bizcub.randomItemSpeedrun.util.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ChangeGameStatusPayloadC2S() implements CustomPacketPayload {

    public static final Type<ChangeGameStatusPayloadC2S> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "change_game_status_c2s"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeGameStatusPayloadC2S> CODEC =
            StreamCodec.unit(new ChangeGameStatusPayloadC2S());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
