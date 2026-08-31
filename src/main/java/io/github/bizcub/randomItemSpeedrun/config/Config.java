package io.github.bizcub.randomItemSpeedrun.config;

public interface Config {
    static Config get() {
        return Holder.INSTANCE;
    }

    static void set(final Config config) {
        if (config != null) {
            Holder.INSTANCE = config;
        }
    }

    class Holder {
        private static Config INSTANCE = new Config() { };
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
