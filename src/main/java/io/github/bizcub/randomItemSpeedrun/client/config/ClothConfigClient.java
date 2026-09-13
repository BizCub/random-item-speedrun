package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.util.Constants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = Constants.MOD_ID + "/config_client")
public class ClothConfigClient implements ConfigClient, ConfigData {

    public static ClothConfigClient getInstance() {
        return AutoConfig.getConfigHolder(ClothConfigClient.class).getConfig();
    }

    public static void init() {
        AutoConfig.register(ClothConfigClient.class, GsonConfigSerializer::new);
    }

    @ConfigEntry.Gui.Tooltip
    public boolean isHudRender = ConfigClient.super.isHudRender();

    @ConfigEntry.ColorPicker(allowAlpha = true)
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
