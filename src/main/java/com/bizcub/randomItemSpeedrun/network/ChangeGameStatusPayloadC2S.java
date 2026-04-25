package com.bizcub.randomItemSpeedrun.network;

//? >=1.20.3 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?} else {
/*import com.mojang.serialization.Codec;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;*///?}

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;

public record ChangeGameStatusPayloadC2S() /*? >=1.20.3 >> ' {'*/ implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("change_game_status_c2s");

    //? >=1.20.3 {
    public static final Type<ChangeGameStatusPayloadC2S> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeGameStatusPayloadC2S> CODEC =
            StreamCodec.unit(new ChangeGameStatusPayloadC2S());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    //?} else {
    /*public static final Codec<ChangeGameStatusPayloadC2S> CODEC = Codec.unit(new ChangeGameStatusPayloadC2S());

    public FriendlyByteBuf toBuffer() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }*///?}
}
