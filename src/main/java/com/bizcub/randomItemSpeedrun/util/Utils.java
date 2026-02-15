package com.bizcub.randomItemSpeedrun.util;

import com.bizcub.randomItemSpeedrun.Game;
import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.Configs;
import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {

    public static Component getTimeComponent(int seconds, int color) {
        Time time = new Time(seconds);
        return Component.translatable("gui.game_start_screen.side_panel.time.time",
                time.isDaysExist() ? Component.translatable("gui.game_start_screen.side_panel.time.days", time.getDays()) : Component.empty(),
                time.isHoursExist() ? Component.translatable("gui.game_start_screen.side_panel.time.hours", time.getHours()) : Component.empty(),
                time.isMinutesExist() ? Component.translatable("gui.game_start_screen.side_panel.time.minutes", time.getMinutes()) : Component.empty(),
                Component.translatable("gui.game_start_screen.side_panel.time.seconds", time.getSeconds())
        ).withColor(color);
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
        return convertComponentToId(itemStack.getItem().getName().getContents().toString());
    }

    public static String getNameFromItemStack(ItemStack itemStack) {
        return itemStack.getItem().getName().getString();
    }

    public static void renderHud(GuiGraphics guiGraphics) {
        Game game = Main.game;
        if (!game.isStarted() || (Compat.isClothConfigLoaded() && !Configs.getInstance().isHudRender)) return;

        double offsetXPercent = 1;
        double offsetYPercent = 1.5;
        int color = Constants.getHudColor();

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                game.getItemStack().getItemName(),
                Utils.getPercent(guiGraphics.guiWidth(), offsetXPercent),
                Utils.getPercent(guiGraphics.guiWidth(), offsetYPercent),
                color
        );
        offsetYPercent += 2.5;
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                Utils.getTimeComponent(game.getTime() / 20, color),
                Utils.getPercent(guiGraphics.guiWidth(), offsetXPercent),
                Utils.getPercent(guiGraphics.guiWidth(), offsetYPercent),
                color
        );
    }

    public static void readSpeedruns() {
        Gson gson = new Gson();
        try {
            Constants.SPEEDRUNS_FILE.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(Constants.SPEEDRUNS_FILE)) {
            Type listType = new TypeToken<List<Speedrun>>() {}.getType();
            List<Speedrun> tests = gson.fromJson(reader, listType);
            if (!Main.speedruns.isEmpty()) Main.speedruns.addAll(tests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSpeedruns() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(Constants.SPEEDRUNS_FILE)) {
            gson.toJson(Main.speedruns, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
