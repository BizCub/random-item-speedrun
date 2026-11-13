package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.config.ConfigHelperMain;
import net.minecraft.client.gui.screens.Screen;

public class ConfigHelperClient {

    public static void init() {
        if (ConfigHelperMain.isSimpleConfigLoaded()) {
            SimpleConfigClient.init();
        }
    }

    public static Screen getScreen(Screen parent) {
        return ConfigHelperMain.isSimpleConfigLoaded() ? SimpleConfigClient.getScreen(parent) : parent;
    }
}
