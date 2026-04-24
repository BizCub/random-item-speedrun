package com.bizcub.randomItemSpeedrun.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class Utils {

    public static Component getTimeComponent(int seconds, int color) {
        Time time = new Time(seconds);
        return Component.translatable("gui.game_start_screen.side_panel.time.time",
                time.isDaysExist() ? Component.translatable("gui.game_start_screen.side_panel.time.days", time.getDays()) : Component.empty(),
                time.isHoursExist() ? Component.translatable("gui.game_start_screen.side_panel.time.hours", time.getHours()) : Component.empty(),
                time.isMinutesExist() ? Component.translatable("gui.game_start_screen.side_panel.time.minutes", time.getMinutes()) : Component.empty(),
                Component.translatable("gui.game_start_screen.side_panel.time.seconds", time.getSeconds())
        ).withStyle(style -> style.withColor(color));
    }

    public static int getPercent(int side, double percent) {
        return (int) (side / 100d * percent);
    }

    public static String convertComponentToId(String tabId) {
        return tabId.substring(tabId.lastIndexOf(".") + 1);
    }

    public static ItemStack getItemStackFromId(String id) {
        List<Item> items = BuiltInRegistries.ITEM.stream().toList();
        ItemStack itemStack = new ItemStack(Items.BARRIER);

        for (Item item : items) {
            if (getIdFromItemStack(new ItemStack(item)).equals(id)) {
                return new ItemStack(item);
            }
        }
        return itemStack;
    }

    public static String getIdFromItemStack(ItemStack itemStack) {
        return convertComponentToId(itemStack.getItem().getDescriptionId());
    }

    public static String getNameFromItemStack(ItemStack itemStack) {
        return itemStack.getItem().getDescriptionId();
    }

    public static String removeBracketsOrDefault(String string) {
        return
                /*? >=1.21.2 {*/ string;
                /*?} else*/ //string.substring(1, string.length() - 1);
    }

    public static Identifier getIdentifier(String id) {
        return
                /*? >=1.21 {*/ Identifier.fromNamespaceAndPath(
                /*?} else*/ //new Identifier(
                        Constants.MOD_ID, id);
    }

    public static Identifier getDefaultIdentifier(String id) {
        return
                /*? >=1.21 {*/ Identifier.withDefaultNamespace(id);
                /*?} else*/ //new Identifier(id);
    }
}
