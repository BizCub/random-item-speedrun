package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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

    public static final StreamCodec<ByteBuf, Speedrun> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Speedrun::itemId,
            ByteBufCodecs.STRING_UTF8, Speedrun::playerName,
            ByteBufCodecs.BOOL, Speedrun::isSuccess,
            ByteBufCodecs.INT, Speedrun::time,
            ByteBufCodecs.LONG, Speedrun::date,
            Speedrun::new
    );
}
