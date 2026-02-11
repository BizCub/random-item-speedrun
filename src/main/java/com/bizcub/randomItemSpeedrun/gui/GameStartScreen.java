package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class GameStartScreen extends Screen {

    private EditBox searchBox;
    private SpeedrunWidget speedrunWidget;

    public GameStartScreen() {
        super(Component.translatable("gui.game_start_screen.title"));
    }

    @Override
    protected void init() {
        super.init();

        this.searchBox = new EditBox(this.font, /* pos */ getWidth(44), getHeight(25), /* size */ getWidth(3.2), getHeight(12), this.searchBox, Component.empty());
        this.searchBox.setHint(Component.translatable("gui.language.search"));
        addRenderableWidget(this.searchBox);
        this.setInitialFocus(this.searchBox);

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 + 4, getHeight(1.1)).size(125, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_RETURN_TO_MENU, button -> {}).pos(this.width / 2 - 129, getHeight(1.1)).size(125, 20).build());

        this.speedrunWidget = new SpeedrunWidget(this.minecraft, /* size */ getWidth(1.5), getHeight(1.35), /* pos Y */ getHeight(6.5), /* size entry */ getHeight(10));
        addRenderableWidget(speedrunWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, /* pos */ getWidth(2), getHeight(16), -1);
    }

    public int getWidth(double width) {
        return (int) (this.width / width);
    }

    public int getHeight(double height) {
        return (int) (this.height / height);
    }
}
