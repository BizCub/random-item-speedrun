package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.ModClothConfig;
import com.bizcub.randomItemSpeedrun.config.ModConfig;
import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.RemovableItems;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String> allItemsId = new ArrayList<>();
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;

    public static void init() {
        if (Compat.isClothConfigLoaded()) ModClothConfig.init();
        Utils.readSpeedruns();
        game = new Game();
        setDifficulty();
        getConfig();
    }

    public static ModConfig getConfig() {
        return ModConfig.CONFIG;
    }

    public static void setDifficulty() {
        if (Compat.isClothConfigLoaded()) {
            switch (Main.getConfig().difficulty()) {
                case EASY -> fillItemsList(Constants.notEasyItems());
                case NORMAL -> fillItemsList(Constants.notMediumItems());
                case HARD -> fillItemsList(Constants.notHardItems());
                case HARDCORE -> fillItemsList(Constants.impossibleItems());
                default -> {}
            }
        } else {
            fillItemsList(Constants.notMediumItems());
        }
        fillItemsList(Constants.notMediumItems());
    }

    private static void fillItemsList(RemovableItems items) {
        allItemsId.clear();
        BuiltInRegistries.ITEM.stream().toList().forEach(item ->
                allItemsId.add(Utils.convertComponentToId(item.getDescriptionId())));

        allItemsId.removeAll(items.equalItems());
        for (String item : items.containItems())
            allItemsId.removeIf(id -> id.contains(item));
    }
}
