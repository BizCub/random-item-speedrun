package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class SpeedrunWidget extends ObjectSelectionList<SpeedrunEntry> {

    public ArrayList<SpeedrunEntry> entries = new ArrayList<>();
    public GameStartScreen screen;

    private SpeedrunEntry lastSelectedEntry;

    public SpeedrunWidget(Minecraft minecraft, int i, int j, int k, int l, GameStartScreen screen) {
        super(minecraft, i, j, k, l);
        this.screen = screen;
        refreshEntries("");
    }

    public void refreshEntries(String searchTerm) {
        this.clearEntries();
        entries.clear();

        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.ITEM_FRAME), true, 1, 1771894451737L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.ICE), true, 1, 177089445137L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.GLASS), false, 1, 1770894451237L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.TARGET), true, 1, 1770894451734L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.INFESTED_STONE), true, 1, 1770894451736L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.GHAST_TEAR), true, 1, 1770894461737L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.QUARTZ), false, 1, 1770894351737L)));
        entries.add(new SpeedrunEntry(new Speedrun(new ItemStack(Items.FERN), true, 1, 1770894451537L)));

        entries.removeIf(entry -> !entry.speedrun.itemStack().getItem().getName().getString().toLowerCase().contains(searchTerm.toLowerCase()));
        entries.forEach(this::addEntry);
        if (!entries.isEmpty()) this.setFocused(entries.get(0));

        screen.changeFocus();
    }

    protected SpeedrunEntry getFocusedSpeedrunEntry() {
        for (SpeedrunEntry entry : entries) {
            if (entry.isFocused()) {
                lastSelectedEntry = entry;
                return entry;
            }
        }
        return lastSelectedEntry;
    }

    @Override
    protected int scrollBarX() {
        return (int) (getWidth() / 1.02);
    }

    @Override
    public int getRowWidth() {
        return (int) (getWidth() / 1.07);
    }
}
