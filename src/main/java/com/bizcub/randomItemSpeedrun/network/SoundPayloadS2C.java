package com.bizcub.randomItemSpeedrun.network;

//? >=1.20.5 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?} else {
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;*///?}

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public record SoundPayloadS2C(SoundEvent soundEvent) /*? >=1.20.5 >> ' {'*/ implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("sound");

    //? >=1.20.5 {
    public static final Type<SoundPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPayloadS2C> CODEC =
            StreamCodec.composite(
                    SoundEvent.DIRECT_STREAM_CODEC, SoundPayloadS2C::soundEvent,
                    SoundPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    //?} else {
    /*public static final Codec<SoundPayloadS2C> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoundEvent.DIRECT_CODEC.fieldOf("soundEvent").forGetter(SoundPayloadS2C::soundEvent)
    ).apply(instance, SoundPayloadS2C::new));

    public static SoundPayloadS2C read(FriendlyByteBuf buf) {
        String resourceLocationStr = buf.readUtf();

        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(Identifier.tryParse(resourceLocationStr));

        return new SoundPayloadS2C(soundEvent);
    }

    public FriendlyByteBuf toBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUtf(soundEvent.getLocation().toString());
        return buf;
    }*///?}
}
