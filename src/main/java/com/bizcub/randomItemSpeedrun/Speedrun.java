package com.bizcub.randomItemSpeedrun;

//? >=1.20.5 {
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
//?} else {
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;*///?}

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.world.item.ItemStack;

import java.util.function.IntFunction;

public record Speedrun(
        String itemId,
        String playerName,
        Status isSuccess,
        int time,
        long date
) {

    public ItemStack getItem() {
        return Utils.getItemStackFromId(itemId);
    }

    //? >=1.20.5 {
    public static final StreamCodec<ByteBuf, Speedrun> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Speedrun::itemId,
            ByteBufCodecs.STRING_UTF8, Speedrun::playerName,
            Status.STREAM_CODEC, Speedrun::isSuccess,
            ByteBufCodecs.INT, Speedrun::time,
            //~ if >=1.21.2 'VAR_LONG' -> 'LONG'
            ByteBufCodecs.LONG, Speedrun::date,
            Speedrun::new
    );

    public enum Status {
        SUCCESS, FAILURE, IN_PROGRESS;

        private static final IntFunction<Status> BY_ID =
                ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Status> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);
    }

    //?} else {
    /*public static final Codec<Speedrun> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("itemId").forGetter(Speedrun::itemId),
            Codec.STRING.fieldOf("playerName").forGetter(Speedrun::playerName),
            Status.CODEC.fieldOf("isSuccess").forGetter(Speedrun::isSuccess),
            Codec.INT.fieldOf("time").forGetter(Speedrun::time),
            Codec.LONG.fieldOf("date").forGetter(Speedrun::date)
    ).apply(instance, Speedrun::new));

    public static Speedrun read(FriendlyByteBuf buf) {
        return new Speedrun(buf.readUtf(), buf.readUtf(), buf.readEnum(Status.class), buf.readInt(), buf.readLong());
    }

    public static void write(FriendlyByteBuf buf, Speedrun speedrun) {
        buf.writeUtf(speedrun.itemId);
        buf.writeUtf(speedrun.playerName);
        buf.writeEnum(speedrun.isSuccess);
        buf.writeInt(speedrun.time);
        buf.writeLong(speedrun.date);
    }

    public enum Status {
        SUCCESS, FAILURE, IN_PROGRESS;

        public static final Codec<Status> CODEC = Codec.STRING.xmap(Status::valueOf, Status::name);
    }*///?}
}
