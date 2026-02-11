package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class SpeedrunEntry extends ObjectSelectionList.Entry<SpeedrunEntry> {

    public Speedrun speedrun;
    protected final Minecraft client;

    public SpeedrunEntry(Speedrun speedrun) {
        this.speedrun = speedrun;
        this.client = Minecraft.getInstance();
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
        guiGraphics.drawString(this.client.font, speedrun.itemStack().getItem().getName(), (int) (this.getWidth() * 0.1), this.getY() + (int) (this.getHeight() / 3), 0xFFFFFFFF);
    }

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.itemStack().getItem());
    }
}
