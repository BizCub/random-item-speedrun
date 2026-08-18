package io.github.bizcub.randomItemSpeedrun.client.gui;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
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

    private void render(GuiGraphics graphics, int y, int width, int height) {
        int textX = Utils.getPercent(width, 3) + offsetX;
        int textY = y + Utils.getPercent(height, 37);
        //~ draw_string
        graphics.drawString(this.client.font, component, textX, textY, -1);
        //~ !draw_string
    }

    //? >= 1.21.9 {
    /*@Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean bl, float f) {
        render(guiGraphics, this.getY(), this.getWidth(), this.getHeight());
    }

    *///?} else {
    @Override
    public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float deltaTime) {
        this.render(guiGraphics, top, width, height);
    }//?}

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", component);
    }
}
