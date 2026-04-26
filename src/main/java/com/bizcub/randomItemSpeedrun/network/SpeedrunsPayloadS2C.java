package com.bizcub.randomItemSpeedrun.network;

//? >=1.20.5 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?} else {
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;*///?}

import com.bizcub.randomItemSpeedrun.client.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;

import java.util.List;

public record SpeedrunsPayloadS2C(List<Speedrun> speedruns) /*? >=1.20.5 >> ' {'*/ implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("speedruns");

    //? >=1.20.5 {
    public static final Type<SpeedrunsPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SpeedrunsPayloadS2C> CODEC =
            StreamCodec.composite(
                    Speedrun.CODEC.apply(ByteBufCodecs.list()), SpeedrunsPayloadS2C::speedruns,
                    SpeedrunsPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    //?} else {
    /*public static final Codec<SpeedrunsPayloadS2C> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Speedrun.CODEC.listOf().fieldOf("speedruns").forGetter(SpeedrunsPayloadS2C::speedruns)
            ).apply(instance, SpeedrunsPayloadS2C::new));

    public static SpeedrunsPayloadS2C read(FriendlyByteBuf buf) {
        return new SpeedrunsPayloadS2C(buf.readList(Speedrun::read));
    }

    public FriendlyByteBuf toBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeCollection(speedruns, Speedrun::write);
        return buf;
    }*///?}
}
