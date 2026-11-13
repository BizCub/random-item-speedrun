package io.github.bizcub.randomItemSpeedrun;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
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

    public static final StreamCodec<ByteBuf, Speedrun> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Speedrun::itemId,
            ByteBufCodecs.STRING_UTF8, Speedrun::playerName,
            Status.STREAM_CODEC, Speedrun::isSuccess,
            ByteBufCodecs.INT, Speedrun::time,

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

}
