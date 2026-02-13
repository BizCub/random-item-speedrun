package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SpeedrunInfoWidget extends ObjectSelectionList<SpeedrunInfoEntry> {

    public Speedrun speedrun;
    public Screen screen;
    public int offsetX;

    public SpeedrunInfoWidget(Minecraft minecraft, int i, int j, int k, int l, Screen screen, int offsetX, Speedrun speedrun) {
        super(minecraft, i, j, k, l);
        this.screen = screen;
        this.offsetX = offsetX;
        this.speedrun = speedrun;
        init();
    }

    private void init() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(speedrun.date()), ZoneId.systemDefault());
        String date = WorldSelectionList.DATE_FORMAT.format(zonedDateTime);

        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.item", Component.literal(speedrun.itemStack().getItem().getName().getString()).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY), offsetX));
        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.time", Utils.getTimeComponent(speedrun.time(), ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY), offsetX));
        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.date", Component.literal(date).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY), offsetX));
    }

    @Override
    protected int scrollBarX() {
        return Utils.getPercent(getWidth(), 96.5) + this.offsetX;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
    }
}
