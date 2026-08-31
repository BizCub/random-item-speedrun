package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.*;

@AutoConfig(name = Constants.MOD_ID + "/config", translate = true)
public class SimpleConfig implements Config {

    public static ConfigHolder<SimpleConfig> getInstance() {
        return ConfigHolder.register(SimpleConfig.class).onSave(config -> {
            RandomItemSpeedrun.setDifficulty();
            RandomItemSpeedrun.removeDuplicateItems();
        });
    }

    public Difficulty difficulty = Config.super.difficulty();

    @Tooltip
    public boolean removeDuplicates = Config.super.removeDuplicates();

    @Tooltip
    public boolean isHudRender = Config.super.isHudRender();

    @Color(alpha = true)
    public int hudColor = Config.super.hudColor();

    @Override
    public Difficulty difficulty() {
        return this.difficulty;
    }

    @Override
    public boolean removeDuplicates() {
        return this.removeDuplicates;
    }

    @Override
    public boolean isHudRender() {
        return this.isHudRender;
    }

    @Override
    public int hudColor() {
        return this.hudColor;
    }
}
