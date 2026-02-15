package com.bizcub.randomItemSpeedrun.util;

import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.Configs;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.io.File;

public class Constants {

    public static final String MOD_ID = /*$ mod_id*/ "random_item_speedrun";
    public static final File SPEEDRUNS_FILE = new File("config/" + MOD_ID + "/speedruns.json");

    public static final KeyMapping MY_KEYBIND = new KeyMapping(
            "key." + MOD_ID + ".start.open_game_start_screen",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_Y,
            /*? >=1.21.9*/ KeyMapping.Category.MISC
            /*? <=1.21.8*/ //KeyMapping.CATEGORY_MISC
    );

    public static int getHudColor() {
        if (Compat.isClothConfigLoaded()) return Configs.getInstance().hudColor + 0xff000000;
        return 0xffffffff;
    }
}
