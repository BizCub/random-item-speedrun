package com.bizcub.randomItemSpeedrun.client;

import com.bizcub.randomItemSpeedrun.Game;
import com.bizcub.randomItemSpeedrun.client.config.Compat;
import com.bizcub.randomItemSpeedrun.client.config.ModClothConfig;
import com.bizcub.randomItemSpeedrun.client.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;

public class RandomItemSpeedrunClient {
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;

    public static void init() {
        if (Compat.isClothConfigLoaded()) ModClothConfig.init();
        game = new Game();
        Constants.getConfig();
    }

    public static final KeyMapping OPEN_SCREEN = new KeyMapping(
            "key." + Constants.MOD_ID + ".open_game_start_screen",
            InputConstants.KEY_Y,
            /*? >=1.21.9*/ KeyMapping.Category.MISC
            /*? <=1.21.8*/ //KeyMapping.CATEGORY_MISC
    );

    public static final KeyMapping QUICK_START = new KeyMapping(
            "key." + Constants.MOD_ID + ".quick_start",
            InputConstants.UNKNOWN.getValue(),
            /*? >=1.21.9*/ KeyMapping.Category.MISC
            /*? <=1.21.8*/ //KeyMapping.CATEGORY_MISC
    );

    public static int getHudColor() {
        if (Compat.isClothConfigLoaded()) return Constants.getConfig().hudColor() + 0xff000000;
        return 0xffffffff;
    }

    public static void renderHud(GuiGraphicsExtractor graphics) {
        if (!game.isStarted() || !Constants.getConfig().isHudRender()) return;

        double offsetXPercent = 1;
        double offsetYPercent = 1.5;
        int color = getHudColor();

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
}
