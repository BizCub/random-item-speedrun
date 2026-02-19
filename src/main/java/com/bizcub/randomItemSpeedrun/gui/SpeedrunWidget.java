package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

import java.util.ArrayList;
import java.util.Collections;

public class SpeedrunWidget extends ObjectSelectionList<SpeedrunEntry> {

    public ArrayList<SpeedrunEntry> entries = new ArrayList<>();
    private SpeedrunEntry lastSelectedEntry;

    public SpeedrunWidget(Minecraft minecraft, int width, int height, /*? >=1.20.3 {*/ int y /*?} else {*/ /*int y1, int y2 *//*?}*/, int entryHeight) {
        super(minecraft, width, height, /*? >=1.20.3 {*/ y /*?} else {*/  /*y1, y2 *//*?}*/, entryHeight);
        refreshEntries("");
    }

    public void refreshEntries(String searchTerm) {
        this.clearEntries();
        entries.clear();

        ArrayList<Speedrun> tempSpeedruns = Main.speedruns;
        Collections.reverse(tempSpeedruns);
        tempSpeedruns.forEach(speedrun -> entries.add(new SpeedrunEntry(speedrun, this)));

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
        return Utils.getPercent(width, 98.5);
    }

    @Override
    public int getRowWidth() {
        return Utils.getPercent(width, 94);
    }
}
