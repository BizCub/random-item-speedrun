package com.bizcub.randomItemSpeedrun.config;

import com.bizcub.randomItemSpeedrun.Main;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = Main.MOD_ID)
public class Configs implements ConfigData {

    public static void init() {
        AutoConfig.register(Configs.class, GsonConfigSerializer::new);
    }

    public static Configs getInstance() {
        return AutoConfig.getConfigHolder(Configs.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip
    public boolean isHudRender = true;

    @ConfigEntry.ColorPicker
    public int hudColor = 0xffffff;
}
