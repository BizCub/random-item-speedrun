package com.bizcub.randomItemSpeedrun.client.gui;

//? >=1.20.3 {
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
//?} else {
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;*///?}

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.world.item.ItemStack;

public record Speedrun(
        String itemId,
        String playerName,
        boolean isSuccess,
        int time,
        long date
) {

    public ItemStack getItem() {
        return Utils.getItemStackFromId(itemId);
    }

//    public static final StreamCodec<ByteBuf, Speedrun> CODEC = StreamCodec.composite(
//            ByteBufCodecs.STRING_UTF8, Speedrun::itemId,
//            ByteBufCodecs.STRING_UTF8, Speedrun::playerName,
//            ByteBufCodecs.BOOL, Speedrun::isSuccess,
//            ByteBufCodecs.INT, Speedrun::time,
//            //~ if >=1.20.5 'VAR_LONG' -> 'LONG'
//            ByteBufCodecs.VAR_LONG, Speedrun::date,
//            Speedrun::new
//    );

    public static final Codec<Speedrun> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("itemId").forGetter(Speedrun::itemId),
            Codec.STRING.fieldOf("playerName").forGetter(Speedrun::playerName),
            Codec.BOOL.fieldOf("isSuccess").forGetter(Speedrun::isSuccess),
            Codec.INT.fieldOf("time").forGetter(Speedrun::time),
            Codec.LONG.fieldOf("date").forGetter(Speedrun::date)
    ).apply(instance, Speedrun::new));

    public static Speedrun read(FriendlyByteBuf buf) {
        return new Speedrun(buf.readUtf(), buf.readUtf(), buf.readBoolean(), buf.readInt(), buf.readLong());
    }

    public static void write(FriendlyByteBuf buf, Speedrun speedrun) {
        buf.writeUtf(speedrun.itemId);
        buf.writeUtf(speedrun.playerName);
        buf.writeBoolean(speedrun.isSuccess);
        buf.writeInt(speedrun.time);
        buf.writeLong(speedrun.date);
    }
}
