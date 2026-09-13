package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.simpleConfigLib.autoconfig.ConfigProvider;

public interface Config {
    static Config get() {
        return ConfigProvider.get(Config.class);
    }
    static void set(Config instance) {
        ConfigProvider.set(Config.class, instance);
    }

    default Difficulty difficulty() {
        return Difficulty.NORMAL;
    }

    default boolean removeDuplicates() {
        return true;
    }

    default boolean isHudRender() {
        return true;
    }

    default int hudColor() {
        return 0xffffffff;
    }
}
