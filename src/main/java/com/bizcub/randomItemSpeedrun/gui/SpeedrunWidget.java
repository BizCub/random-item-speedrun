package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpeedrunWidget extends ObjectSelectionList<SpeedrunEntry> {

    public int width;

    public SpeedrunWidget(Minecraft minecraft, int i, int j, int k, int l) {
        super(minecraft, i, j, k, l);
        this.width = i;

        SpeedrunEntry entry = new SpeedrunEntry(new Speedrun(new ItemStack(Items.ITEM_FRAME), true, 1, 11));

        this.addEntry(entry);
        this.setSelected(entry);

        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.ICE), true, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.GLASS), false, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.TARGET), true, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.INFESTED_STONE), true, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.GHAST_TEAR), true, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.QUARTZ), false, 1, 11)));
        this.addEntry(new SpeedrunEntry(new Speedrun(new ItemStack(Items.FERN), true, 1, 11)));
    }

    @Override
    protected int scrollBarX() {
        return this.width - 6;
    }

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }
}
