package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.*;

@AutoConfig(name = Constants.MOD_ID, fileName = Constants.MOD_ID + "/config_main_scl", translate = true)
public class SimpleConfigMain implements ConfigMain {

    public static ConfigHolder<SimpleConfigMain> getInstance() {
        return ConfigHolder.register(SimpleConfigMain.class).onSave(config -> {
            RandomItemSpeedrun.setDifficulty();
            RandomItemSpeedrun.removeDuplicateItems();
        });
    }

    public Difficulty difficulty = ConfigMain.super.difficulty();

    @Tooltip
    public boolean removeDuplicates = ConfigMain.super.removeDuplicates();

    @Override
    public Difficulty difficulty() {
        return this.difficulty;
    }

    @Override
    public boolean removeDuplicates() {
        return this.removeDuplicates;
    }
}
