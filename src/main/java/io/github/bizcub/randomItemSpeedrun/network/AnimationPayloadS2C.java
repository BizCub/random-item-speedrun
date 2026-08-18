package io.github.bizcub.randomItemSpeedrun.network;

//? >=1.20.5 {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
*///?} else {
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;//?}

import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record AnimationPayloadS2C(ItemStack itemStack) /*? >=1.20.5 >> ' {'*/ /*implements CustomPacketPayload*/ {

    public static final ResourceLocation ID = Utils.getResourceLocation("animation");

    //? >=1.20.5 {
    /*public static final Type<AnimationPayloadS2C> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationPayloadS2C> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, AnimationPayloadS2C::itemStack,
                    AnimationPayloadS2C::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    *///?} else {
    public static final Codec<AnimationPayloadS2C> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("itemStack").forGetter(AnimationPayloadS2C::itemStack)
    ).apply(instance, AnimationPayloadS2C::new));

    public static AnimationPayloadS2C read(FriendlyByteBuf buf) {
        return new AnimationPayloadS2C(buf.readItem());
    }

    public FriendlyByteBuf toBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeItem(itemStack);
        return buf;
    }//?}
}
