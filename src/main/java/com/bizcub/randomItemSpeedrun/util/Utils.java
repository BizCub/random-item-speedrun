package com.bizcub.randomItemSpeedrun.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Utils {

    public static Component getTimeComponent(int seconds, ChatFormatting color) {
        Time time = new Time(seconds);
        return Component.translatable("gui.game_start_screen.side_panel.time.time",
                time.isDaysExist() ? Component.translatable("gui.game_start_screen.side_panel.time.days", time.getDays()) : Component.empty(),
                time.isHoursExist() ? Component.translatable("gui.game_start_screen.side_panel.time.hours", time.getHours()) : Component.empty(),
                time.isMinutesExist() ? Component.translatable("gui.game_start_screen.side_panel.time.minutes", time.getMinutes()) : Component.empty(),
                Component.translatable("gui.game_start_screen.side_panel.time.seconds", time.getSeconds())
        ).withStyle(color);
    }

    public static int getPercent(int side, double percent) {
        return (int) (side / 100d * percent);
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

    public static ItemStack getItemStackFromId(String id) {
        Identifier identifier = Identifier.parse("minecraft:" + id);
        Item item = BuiltInRegistries.ITEM.get(identifier).orElseThrow().value();
        return new ItemStack(item, 1);
    }

    public static String getIdFromItemStack(ItemStack itemStack) {
        return itemStack.getItem().getName().getString();
    }
}
