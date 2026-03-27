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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
        /*? >=1.21.2*/ return string;
        /*? <=1.21.1*/ //return string.substring(1, string.length() - 1);
    }

    public static Identifier getIdentifier(String id) {
        /*? >=1.21*/ return Identifier.fromNamespaceAndPath(Constants.MOD_ID, id);
        /*? <=1.20.6*/ //return new Identifier(Constants.MOD_ID, id);
    }

    public static Identifier getCustomIdentifier(String location, String id) {
        /*? >=1.21*/ return Identifier.fromNamespaceAndPath(location, id);
        /*? <=1.20.6*/ //return new Identifier(location, id);
    }

    public static Identifier getDefaultIdentifier(String id) {
        /*? >=1.21*/ return Identifier.withDefaultNamespace(id);
        /*? <=1.20.6*/ //return new Identifier(id);
    }

    public static void renderHud(GuiGraphicsExtractor graphics) {
        Game game = Main.game;
        if (!game.isStarted()  || (Compat.isClothConfigLoaded() && !Configs.getInstance().isHudRender)) return;

        double offsetXPercent = 1;
        double offsetYPercent = 1.5;
        int color = Constants.getHudColor();

        //~ draw_string
        graphics.text(
                Minecraft.getInstance().font,
                Utils.removeBracketsOrDefault(game.getItemStack().getItemName().getString()),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiWidth(), offsetYPercent),
                color
        );
        offsetYPercent += 2.5;
        graphics.text(
                Minecraft.getInstance().font,
                Utils.getTimeComponent(game.getTime() / 20, color),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiWidth(), offsetYPercent),
                color
        );
        //~ !draw_string
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
            if (tests != null) Main.speedruns.addAll(tests);
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
