package io.github.bizcub.randomItemSpeedrun.client.config;

public interface ConfigClient {
    static ConfigClient get() {
        return Holder.INSTANCE;
    }

    static void set(final ConfigClient config) {
        if (config != null) {
            Holder.INSTANCE = config;
        }
    }

    class Holder {
        private static ConfigClient INSTANCE = new ConfigClient() { };
    }

    default boolean isHudRender() {
        return true;
    }

    default int hudColor() {
        return 0xffffffff;
    }
}
