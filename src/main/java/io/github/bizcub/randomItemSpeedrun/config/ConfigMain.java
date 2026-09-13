package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.simpleConfigLib.autoconfig.ConfigProvider;

public interface ConfigMain {
    static ConfigMain get() {
        return ConfigProvider.get(ConfigMain.class);
    }
    static void set(ConfigMain instance) {
        ConfigProvider.set(ConfigMain.class, instance);
    }

    default Difficulty difficulty() {
        return Difficulty.NORMAL;
    }

    default boolean removeDuplicates() {
        return true;
    }
}
