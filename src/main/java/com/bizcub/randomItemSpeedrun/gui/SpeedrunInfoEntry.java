package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class SpeedrunInfoEntry extends ObjectSelectionList.Entry<SpeedrunInfoEntry> {

    public Component component;
    public int offsetX;
    protected final Minecraft client = Minecraft.getInstance();

    public SpeedrunInfoEntry(Component component, int offsetX) {
        this.component = component;
        this.offsetX = offsetX;
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
        guiGraphics.drawString(
                this.client.font,
                component,
                Utils.getPercent(getWidth(), 3) + offsetX,
                this.getY() + Utils.getPercent(getHeight(), 37),
                -1
        );
    }

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", component);
    }
}
