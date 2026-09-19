package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.client.gui.ClothConfigHubScreen;
import io.github.bizcub.randomItemSpeedrun.config.ConfigHelperMain;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.gui.ConfigScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ConfigHelperClient {

    public static Screen getScreen(Screen parent) {
        if (ConfigHelperMain.isSimpleConfigLoaded()) {
            return ConfigScreens.open(Constants.MOD_ID, parent);
        }
        if (ConfigHelperMain.isClothConfigLoaded()) {
            //~ if >=26.2 '.screen' -> '.gui.screen()'
            return new ClothConfigHubScreen(Minecraft.getInstance().gui.screen());
        }
        return parent;
    }
}
