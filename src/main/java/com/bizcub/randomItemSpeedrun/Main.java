package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.Configs;
import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String> allItemsId = new ArrayList<>();
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;

    public static void init() {
        if (Compat.isClothConfigLoaded()) Configs.init();
        Utils.readSpeedruns();
        game = new Game();

        fillItemsList();
        removeImpossibleItems();
    }

    private static void fillItemsList() {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;
        var items = itemRegistry.stream().toList();
        items.forEach(item -> allItemsId.add(Utils.convertComponentToId(item.getDescriptionId())));
    }

    private static void removeImpossibleItems() {
        List<String> removableItems = List.of("player_head", "air", "debug_stick", "bedrock", "barrier", "jigsaw", "light", "end_portal_frame", "reinforced_deepslate", "farmland", "vault", "suspicious_sand", "suspicious_gravel", "knowledge_book");
        allItemsId.removeAll(removableItems);
        allItemsId.removeIf(id -> id.contains("command_block") || id.contains("test") || id.contains("structure") || id.contains("infested") || id.contains("spawn_egg") || id.contains("spawner"));
    }
}
