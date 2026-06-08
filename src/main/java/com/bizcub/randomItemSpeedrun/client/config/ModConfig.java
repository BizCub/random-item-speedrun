package com.bizcub.randomItemSpeedrun.client.config;

public interface ModConfig {
    ModConfig CONFIG = Compat.isClothConfigLoaded() ? ModClothConfig.getInstance() : new ModConfig() { };

    default ModClothConfig.Difficulty difficulty() {
        return ModClothConfig.Difficulty.NORMAL;
    }

    default boolean removeDuplicates() {
        return true;
    }

    default boolean isHudRender() {
        return true;
    }

    default int hudColor() {
        return 0xffffff;
    }
}
