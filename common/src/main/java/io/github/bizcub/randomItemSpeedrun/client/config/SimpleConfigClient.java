package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigSide;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.AutoConfig;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.Color;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.Tooltip;
import io.github.bizcub.simpleConfigLib.autoconfig.gui.ConfigScreens;
import net.minecraft.client.gui.screens.Screen;

@AutoConfig(name = Constants.MOD_ID, fileName = Constants.MOD_ID + "/" + SimpleConfigClient.CONFIG_FILE, side = ConfigSide.CLIENT, translate = true)
public class SimpleConfigClient implements ConfigClient {
    static final String CONFIG_FILE = "config_client";

    public static ConfigHolder<SimpleConfigClient> getInstance() {
        return ConfigHolder.register(SimpleConfigClient.class);
    }

    public static void init() {
        ConfigClient.set(getInstance().get());
    }

    public static Screen getScreen(Screen parent) {
        return ConfigScreens.open(Constants.MOD_ID, parent);
    }

    @Tooltip
    public boolean isHudRender = ConfigClient.super.isHudRender();

    @Color(alpha = true)
    public int hudColor = ConfigClient.super.hudColor();

    @Override
    public boolean isHudRender() {
        return this.isHudRender;
    }

    @Override
    public int hudColor() {
        return this.hudColor;
    }
}
