package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.world.item.ItemStack;

public record Speedrun(
        ItemStack itemStack,
        boolean isSuccess,
        int time,
        int date
) {
}
