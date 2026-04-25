package com.bizcub.randomItemSpeedrun.network;

//? >=1.20.3 {
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
import net.minecraft.world.item.ItemStack;

public record HUDPayloadS2C(ItemStack itemStack, int time, boolean isStart) /*? >=1.20.3 >> ' {'*/ implements CustomPacketPayload {

    public static final Identifier ID = Utils.getIdentifier("render_hud");

    //? >=1.20.3 {
    public static final Type<HUDPayloadS2C> TYPE = new Type<>(Utils.getIdentifier("render_hud"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HUDPayloadS2C> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, HUDPayloadS2C::itemStack,
                    ByteBufCodecs.INT, HUDPayloadS2C::time,
                    ByteBufCodecs.BOOL, HUDPayloadS2C::isStart,
                    HUDPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    //?} else {
    /*public static final Codec<HUDPayloadS2C> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("itemStack").forGetter(HUDPayloadS2C::itemStack),
            Codec.INT.fieldOf("time").forGetter(HUDPayloadS2C::time),
            Codec.BOOL.fieldOf("isStart").forGetter(HUDPayloadS2C::isStart)
    ).apply(instance, HUDPayloadS2C::new));

    public static HUDPayloadS2C read(FriendlyByteBuf buf) {
        return new HUDPayloadS2C(buf.readItem(), buf.readInt(), buf.readBoolean());
    }

    public FriendlyByteBuf toBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeItem(itemStack);
        buf.writeInt(time);
        buf.writeBoolean(isStart);
        return buf;
    }*///?}
}
