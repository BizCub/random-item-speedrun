package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.world.item.ItemStack;

public record Speedrun(
        String itemId,
        boolean isSuccess,
        int time,
        long date
) {

    public ItemStack getItem() {
        return Utils.getItemStackFromId(itemId);
    }
}
