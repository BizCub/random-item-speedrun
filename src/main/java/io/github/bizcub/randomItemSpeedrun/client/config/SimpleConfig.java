package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.*;

@AutoConfig(name = Constants.MOD_ID, translate = true)
public class SimpleConfig implements Config {

    public static ConfigHolder<SimpleConfig> getInstance() {
        return ConfigHolder.register(SimpleConfig.class).onSave(config -> {
            RandomItemSpeedrunMain.setDifficulty();
            RandomItemSpeedrunMain.removeDuplicateItems();
        });
    }

    public Difficulty difficulty = Config.super.difficulty();

    @Tooltip
    public boolean removeDuplicates = Config.super.removeDuplicates();

    @Tooltip
    public boolean isHudRender = Config.super.isHudRender();

    @Color
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
