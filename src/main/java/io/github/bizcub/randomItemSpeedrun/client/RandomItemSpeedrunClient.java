package io.github.bizcub.randomItemSpeedrun.client;

import io.github.bizcub.randomItemSpeedrun.Game;
import io.github.bizcub.randomItemSpeedrun.client.config.Config;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelper;
import io.github.bizcub.randomItemSpeedrun.client.config.ClothConfig;
import io.github.bizcub.randomItemSpeedrun.client.config.SimpleConfig;
import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;

public class RandomItemSpeedrunClient {
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;

    public static void init() {
        game = new Game();
        if (ConfigHelper.isSimpleConfigLoaded()) {
            Config.set(SimpleConfig.getInstance().get());
        } else if (ConfigHelper.isClothConfigLoaded()) {
            ClothConfig.init();
            Config.set(ClothConfig.getInstance());
        }
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
        if (ConfigHelper.isConfigLoaded()) return Config.get().hudColor() + 0xff000000;
        return 0xffffffff;
    }

    public static void renderHud(GuiGraphicsExtractor graphics) {
        if (!game.isStarted() || !Config.get().isHudRender()) return;

        //~ if >=26.1 'renderItem(' -> 'item('
        graphics.item(
                game.getItemStack(),
                Utils.getPercent(graphics.guiWidth(), 0.75),
                Utils.getPercent(graphics.guiHeight(), 2)
        );

        double offsetXPercent = 5;
        double offsetYPercent = 1.5;
        int color = getHudColor();
        //~ draw_string
        graphics.text(
                Minecraft.getInstance().font,
                Utils.removeBracketsOrDefault(game.getItemStack().getItemName().getString()),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiHeight(), offsetYPercent),
                color
        );
        offsetYPercent += 4;
        graphics.text(
                Minecraft.getInstance().font,
                Utils.getTimeComponent(game.getTime() / 20, color),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiHeight(), offsetYPercent),
                color
        );
        //~ !draw_string
    }
}
