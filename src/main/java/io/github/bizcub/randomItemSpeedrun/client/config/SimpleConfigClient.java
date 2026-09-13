package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigSide;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.AutoConfig;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.Color;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.Tooltip;

@AutoConfig(name = Constants.MOD_ID, fileName = Constants.MOD_ID + "/config_cleint_scl", translate = true, side = ConfigSide.CLIENT)
public class SimpleConfigClient implements ConfigClient {

    public static ConfigHolder<SimpleConfigClient> getInstance() {
        return ConfigHolder.register(SimpleConfigClient.class);
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
