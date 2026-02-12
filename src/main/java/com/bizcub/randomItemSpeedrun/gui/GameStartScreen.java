package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.ChatFormatting;
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

    private Speedrun focusedSpeedrun;

    private String tempSearch;

    public GameStartScreen() {
        super(Component.translatable("gui.game_start_screen.title"));
    }

    @Override
    protected void init() {
        this.searchBox = new EditBox(this.font, /* pos */ getWidth(44), getHeight(25), /* size */ getWidth(3.2), getHeight(12), this.searchBox, Component.empty());
        this.searchBox.setHint(Component.translatable("gui.language.search"));
        addRenderableWidget(this.searchBox);
        this.setInitialFocus(this.searchBox);
        tempSearch = this.searchBox.getValue();

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 + 4, getHeight(1.1)).size(125, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_RETURN_TO_MENU, button -> {}).pos(this.width / 2 - 129, getHeight(1.1)).size(125, 20).build());

        speedrunWidget = new SpeedrunWidget(this.minecraft, /* size */ getWidth(1.5), getHeight(1.35), /* pos Y */ getHeight(6.5), /* size entry */ getHeight(10));
        addRenderableWidget(speedrunWidget);

        itemName = new StringWidget(getWidth(1.45), getHeight(3.3), 100, 100, Component.empty(), this.font);
        speedrunTime = new StringWidget(getWidth(1.45), getHeight(2.8), 100, 100, Component.empty(), this.font);
        speedrunDate = new StringWidget(getWidth(1.45), getHeight(2.5), 100, 100, Component.empty(), this.font);

        setItemDisplayWidget();
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, getWidth(2), getHeight(16), -1);
        itemDisplayWidget.render(guiGraphics, i, j, f);

        itemName.render(guiGraphics, i, j, f);
        speedrunTime.render(guiGraphics, i, j, f);
        speedrunDate.render(guiGraphics, i, j, f);

        if (!this.searchBox.getValue().equals(tempSearch))
            speedrunWidget.refreshEntries(this.searchBox.getValue());
        tempSearch = this.searchBox.getValue();
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        super.setFocused(guiEventListener);
        if (speedrunWidget != null) setItemDisplayWidget();
    }

    private void setItemDisplayWidget() {
        focusedSpeedrun = speedrunWidget.getFocusedSpeedrunEntry().speedrun;
        itemDisplayWidget = new ScaledItemDisplayWidget(getWidth(1.35), getHeight(7), focusedSpeedrun.itemStack(), 5);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(focusedSpeedrun.date()), ZoneId.systemDefault());
        String string = WorldSelectionList.DATE_FORMAT.format(zonedDateTime);

        itemName.setMessage(Component.translatable("gui.game_start_screen.side_panel.item", Component.literal(focusedSpeedrun.itemStack().getItem().getName().getString()).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
        speedrunTime.setMessage(Component.translatable("gui.game_start_screen.side_panel.time", Component.literal(String.valueOf(focusedSpeedrun.time())).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
        speedrunDate.setMessage(Component.translatable("gui.game_start_screen.side_panel.date", Component.literal(string).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
    }

    public int getWidth(double width) {
        return (int) (this.width / width);
    }

    public int getHeight(double height) {
        return (int) (this.height / height);
    }
}
