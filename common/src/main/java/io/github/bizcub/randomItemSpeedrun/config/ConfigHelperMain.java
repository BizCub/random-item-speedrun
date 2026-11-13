package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.randomItemSpeedrun.platform.PlatformHooks;

public class ConfigHelperMain {
    public static boolean isSimpleConfigLoaded() {
        return PlatformHooks.get().isModLoaded("simple_config_lib");
    }

    public static void init() {
        if (isSimpleConfigLoaded()) {
            SimpleConfigMain.init();
        }
    }
}
