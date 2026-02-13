package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.Configs;
import com.bizcub.randomItemSpeedrun.gui.GameStartScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static final String MOD_ID = /*$ mod_id*/ "random_item_speedrun";

    public static List<String> allItemsId = new ArrayList<>();

    public static void init() {
        if (Compat.isClothConfigLoaded()) Configs.init();
        itemsInit();

        KeyMapping.Category CATEGORY = new KeyMapping.Category(
                Identifier.withDefaultNamespace(Main.MOD_ID)
        );

        KeyMapping openStartScreen = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key." + MOD_ID + ".start.open_game_start_screen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openStartScreen.consumeClick()) {
                Minecraft.getInstance().setScreen(new GameStartScreen());
            }
        });
    }

    public static String getRandomItem() {
        Random random = new Random();
        return allItemsId.get(random.nextInt(allItemsId.size()));
    }

    public static ItemStack getItemStackFromId(String id) {
        Identifier identifier = Identifier.parse("minecraft:" + id);
        Item item = BuiltInRegistries.ITEM.get(identifier).orElseThrow().value();
        return new ItemStack(item, 1);
    }

    public static String convertComponentToId(String tabId) {
        int firstIndex = tabId.indexOf("'");
        if (tabId.startsWith("key=", firstIndex - 4)) {
            tabId = tabId.substring(firstIndex + 1);
            tabId = tabId.substring(0, tabId.indexOf("'"));
            tabId = tabId.substring(tabId.lastIndexOf(".") + 1);
        }
        return tabId;
    }

    private static void itemsInit() {
        fillItemsList();
        removeImpossibleItems();
    }

    private static void fillItemsList() {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;
        Stream<Holder.Reference<Item>> items = itemRegistry.listElements();
        items.toList().forEach(holder -> allItemsId.add(convertComponentToId(holder.value().asItem().getName().getContents().toString())));
    }

    private static void removeImpossibleItems() {
        List<String> removableItems = List.of("air", "debug_stick", "bedrock", "barrier", "jigsaw", "light", "end_portal_frame", "reinforced_deepslate", "farmland", "vault", "suspicious_sand", "suspicious_gravel", "knowledge_book");
        allItemsId.removeAll(removableItems);
        allItemsId.removeIf(id -> id.contains("command_block") || id.contains("test") || id.contains("structure") || id.contains("infested_stone") || id.contains("spawn_egg") || id.contains("spawner"));
    }
}
