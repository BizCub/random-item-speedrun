package io.github.bizcub.randomItemSpeedrun.client.gui;

//~ auto_config
import io.github.bizcub.randomItemSpeedrun.config.ClothConfigMain;
import io.github.bizcub.randomItemSpeedrun.client.config.ClothConfigClient;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ClothConfigHubScreen extends Screen {
    private final Screen lastScreen;

    public ClothConfigHubScreen(Screen lastScreen) {
        super(Component.literal("Config"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 32;

        //~ auto_config
        addRenderableWidget(Button.builder(Component.literal("Main Config"),
                        b -> this.minecraft.gui.setScreen(AutoConfigClient.getConfigScreen(ClothConfigMain.class, this).get()))
                .bounds(x, y, 200, 20).build());

        //~ auto_config
        addRenderableWidget(Button.builder(Component.literal("Client Config"),
                        b -> this.minecraft.gui.setScreen(AutoConfigClient.getConfigScreen(ClothConfigClient.class, this).get()))
                .bounds(x, y + 24, 200, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, b -> onClose())
                .bounds(x, y + 52, 200, 20).build());
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }
}
