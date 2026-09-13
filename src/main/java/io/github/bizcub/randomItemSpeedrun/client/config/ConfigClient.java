package io.github.bizcub.randomItemSpeedrun.client.config;

import io.github.bizcub.simpleConfigLib.autoconfig.ConfigProvider;

public interface ConfigClient {
    static ConfigClient get() {
        return ConfigProvider.get(ConfigClient.class);
    }
    static void set(ConfigClient instance) {
        ConfigProvider.set(ConfigClient.class, instance);
    }

    default boolean isHudRender() {
        return true;
    }

    default int hudColor() {
        return 0xffffffff;
    }
}
