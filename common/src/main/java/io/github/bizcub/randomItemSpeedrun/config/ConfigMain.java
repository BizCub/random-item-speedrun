package io.github.bizcub.randomItemSpeedrun.config;

public interface ConfigMain {
    static ConfigMain get() {
        return Holder.INSTANCE;
    }

    static void set(ConfigMain config) {
        if (config != null) {
            Holder.INSTANCE = config;
        }
    }

    class Holder {
        private static ConfigMain INSTANCE = new ConfigMain() { };
    }

    default Difficulty difficulty() {
        return Difficulty.NORMAL;
    }

    default boolean removeDuplicates() {
        return true;
    }

    enum Difficulty {
        EASY, NORMAL, HARD, HARDCORE
    }
}
