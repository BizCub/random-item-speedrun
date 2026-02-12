package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GameStartScreen extends Screen {
    private EditBox searchBox;
    private SpeedrunWidget speedrunWidget;
    private ScaledItemDisplayWidget itemDisplayWidget;
    private StringWidget itemName;
    private StringWidget speedrunTime;
    private StringWidget speedrunDate;

    private String tempSearch;

    public GameStartScreen() {
        super(Component.translatable("gui.game_start_screen.title"));
    }

    @Override
    protected void init() {
        this.searchBox = new EditBox(this.font, /* pos */ getWidthPercent(1), getHeightPercent(2.5), /* size */ getWidthPercent(30), getHeightPercent(7), this.searchBox, Component.empty());
        this.searchBox.setHint(Component.translatable("gui.language.search"));
        addRenderableWidget(this.searchBox);
        this.setInitialFocus(this.searchBox);
        this.tempSearch = this.searchBox.getValue();

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 + 4, getHeightPercent(91.5)).size(125, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_RETURN_TO_MENU, button -> {}).pos(this.width / 2 - 129, getHeightPercent(91.5)).size(125, 20).build());

        this.speedrunWidget = new SpeedrunWidget(this.minecraft, /* size */ getWidthPercent(67), getHeightPercent(79), /* pos Y */ getHeightPercent(11), /* size entry */ getHeightPercent(10), this);
        addRenderableWidget(speedrunWidget);

        this.itemName = new StringWidget(getWidthPercent(69), getHeightPercent(30), 100, 100, Component.empty(), this.font);
        this.speedrunTime = new StringWidget(getWidthPercent(69), getHeightPercent(34), 100, 100, Component.empty(), this.font);
        this.speedrunDate = new StringWidget(getWidthPercent(69), getHeightPercent(38), 100, 100, Component.empty(), this.font);

        changeFocus();
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, getWidthPercent(50), getHeightPercent(4), -1);
        this.itemDisplayWidget.render(guiGraphics, i, j, f);

        this.itemName.render(guiGraphics, i, j, f);
        this.speedrunTime.render(guiGraphics, i, j, f);
        this.speedrunDate.render(guiGraphics, i, j, f);

        if (!this.searchBox.getValue().equals(this.tempSearch))
            this.speedrunWidget.refreshEntries(this.searchBox.getValue());
        this.tempSearch = this.searchBox.getValue();
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

    public void changeFocus() {
        if (speedrunWidget == null) return;

        Speedrun focusedSpeedrun = speedrunWidget.getFocusedSpeedrunEntry().speedrun;
        this.itemDisplayWidget = new ScaledItemDisplayWidget(getWidthPercent(75), getHeightPercent(14), focusedSpeedrun.itemStack(), 5);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(focusedSpeedrun.date()), ZoneId.systemDefault());
        String string = WorldSelectionList.DATE_FORMAT.format(zonedDateTime);

        this.itemName.setMessage(Component.translatable("gui.game_start_screen.side_panel.item", Component.literal(focusedSpeedrun.itemStack().getItem().getName().getString()).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
        this.speedrunTime.setMessage(Component.translatable("gui.game_start_screen.side_panel.time", Component.literal(String.valueOf(focusedSpeedrun.time())).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
        this.speedrunDate.setMessage(Component.translatable("gui.game_start_screen.side_panel.date", Component.literal(string).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
    }

    public int getWidthPercent(double percent) {
        return (int) (this.width / 100d * percent);
    }

    public int getHeightPercent(double percent) {
        return (int) (this.height / 100d * percent);
    }
}
