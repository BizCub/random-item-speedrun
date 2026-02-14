package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

import java.util.ArrayList;

public class SpeedrunWidget extends ObjectSelectionList<SpeedrunEntry> {

    public ArrayList<SpeedrunEntry> entries = new ArrayList<>();
    private SpeedrunEntry lastSelectedEntry;

    public SpeedrunWidget(Minecraft minecraft, int i, int j, int k, int l) {
        super(minecraft, i, j, k, l);
        refreshEntries("");
    }

    public void refreshEntries(String searchTerm) {
        this.clearEntries();
        entries.clear();

        Main.speedruns.reversed().forEach(speedrun -> entries.add(new SpeedrunEntry(speedrun)));

        entries.removeIf(entry -> !Utils.getNameFromItemStack(entry.speedrun.getItem()).toLowerCase().contains(searchTerm.toLowerCase()));
        entries.forEach(this::addEntry);
        if (!entries.isEmpty()) this.setFocused(entries.get(0));
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
        return Utils.getPercent(getWidth(), 98.5);
    }

    @Override
    public int getRowWidth() {
        return Utils.getPercent(getWidth(), 94);
    }
}
