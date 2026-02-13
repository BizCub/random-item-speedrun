package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.platforms.PlatformInit;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class GameStartScreen extends Screen {
    private EditBox searchBox;
    private SpeedrunWidget speedrunWidget;
    private SpeedrunInfoWidget speedrunInfoWidget;
    private ScaledItemDisplayWidget itemDisplayWidget;

    private String tempSearch;

    public GameStartScreen() {
        super(Component.translatable("gui.game_start_screen.title"));
    }

    @Override
    protected void init() {
        super.init();
        this.searchBox = new EditBox(this.font, /* pos */ getWidthPercent(5.7), getHeightPercent(2.5), /* size */ getWidthPercent(22), getHeightPercent(7), this.searchBox, Component.empty());
        this.searchBox.setHint(Component.translatable("gui.language.search"));
        addRenderableWidget(this.searchBox);
        this.setInitialFocus(this.searchBox);

        Identifier sprite = Identifier.fromNamespaceAndPath(Main.MOD_ID, "settings");
        SpriteIconButton settingsButton = addRenderableWidget(SpriteIconButton.builder(Component.empty(), button -> this.minecraft.setScreen(PlatformInit.getScreen(this)), true).size(getWidthPercent(3.8), getWidthPercent(3.8)).sprite(sprite, 15, 15).build());
        settingsButton.setPosition(getWidthPercent(1), getHeightPercent(2.5));

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 + 4, getHeightPercent(91.5)).size(125, 20).build());
        Button startButton = addRenderableWidget(Button.builder(Component.translatable("gui.game_start_screen.start_button"), button -> {}).pos(this.width / 2 - 129, getHeightPercent(91.5)).size(125, 20).build());

        this.speedrunWidget = addRenderableWidget(new SpeedrunWidget(this.minecraft, /* size */ getWidthPercent(66), getHeightPercent(79), /* pos Y */ getHeightPercent(11), /* size entry */ getHeightPercent(10), this));

        if (!Compat.isClothConfigLoaded()) {
            settingsButton.active = false;
            startButton.active = false;
            settingsButton.setTooltip(Tooltip.create(Component.translatable("gui.game_start_screen.cloth_config_is_not_loaded")));
            startButton.setTooltip(Tooltip.create(Component.translatable("gui.game_start_screen.cloth_config_is_not_loaded")));
        }

        changeFocus();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, getWidthPercent(50), getHeightPercent(4), -1);

        this.itemDisplayWidget.render(guiGraphics, i, j, f);
        this.speedrunInfoWidget.render(guiGraphics, i, j, f);

        if (!this.searchBox.getValue().equals(this.tempSearch))
            this.speedrunWidget.refreshEntries(this.searchBox.getValue());
        this.tempSearch = this.searchBox.getValue();
    }

    public void changeFocus() {
        if (speedrunWidget == null) return;
        Speedrun focusedSpeedrun = this.speedrunWidget.getFocusedSpeedrunEntry().speedrun;

        this.itemDisplayWidget = new ScaledItemDisplayWidget(getWidthPercent(77), getHeightPercent(14), focusedSpeedrun.itemStack(), 5);

        int offsetX = getWidthPercent(68.25);
        this.speedrunInfoWidget = new SpeedrunInfoWidget(this.minecraft, /* size */ getWidthPercent(32), getHeightPercent(45), /* pos Y */ getHeightPercent(45.2), /* size entry */ getHeightPercent(4), this, offsetX, focusedSpeedrun);
        this.speedrunInfoWidget.setX(offsetX);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        super.setFocused(guiEventListener);
        changeFocus();
    }

    @Override
    protected void changeFocus(ComponentPath componentPath) {
        super.changeFocus(componentPath);
        changeFocus();
    }

    public int getWidthPercent(double percent) {
        return Utils.getPercent(this.width, percent);
    }

    public int getHeightPercent(double percent) {
        return Utils.getPercent(this.height, percent);
    }
}
