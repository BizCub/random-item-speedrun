package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpeedrunInfoWidget extends ObjectSelectionList<SpeedrunInfoEntry> {

    public Speedrun speedrun;
    public Screen screen;
    public int offsetX;

    /*? >=1.20.3*/ public SpeedrunInfoWidget(Minecraft minecraft, int width, int height, int y, int entryHeight, Screen screen, int offsetX, Speedrun speedrun) {
    /*? <=1.20.2*/ //public SpeedrunInfoWidget(Minecraft minecraft, int width, int height, int y1, int y2, int entryHeight, Screen screen, int offsetX, Speedrun speedrun) {
        /*? >=1.20.3*/ super(minecraft, width, height, y, entryHeight);
        /*? <=1.20.2*/ //super(minecraft, width, height, y1, y2, entryHeight);
        this.screen = screen;
        this.offsetX = offsetX;
        this.speedrun = speedrun;
        init();
    }

    private void init() {
        DateFormat dete = new SimpleDateFormat();
        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.item", Component.literal(Utils.removeBracketsOrDefault(speedrun.getItem().getItemName().getString())).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY), offsetX));
        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.time", Utils.getTimeComponent(speedrun.time(), ChatFormatting.WHITE.getColor())).withStyle(ChatFormatting.GRAY), offsetX));
        this.addEntry(new SpeedrunInfoEntry(Component.translatable("gui.game_start_screen.side_panel.date", Component.literal(dete.format(new Date(speedrun.date()))).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY), offsetX));
    }

    @Override
    protected int scrollBarX() {
        return Utils.getPercent(width, 96.5) + this.offsetX;
    }

    @Override
    public void setFocused(GuiEventListener guiEventListener) {
    }
}
