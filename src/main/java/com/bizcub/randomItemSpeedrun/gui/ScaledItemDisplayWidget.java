package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.item.ItemStack;

public class ScaledItemDisplayWidget extends AbstractWidget {

    private final ItemStack itemStack;
    private final int offsetX;
    private final int offsetY;
    private final int size;

    public ScaledItemDisplayWidget(int offsetX, int offsetY, ItemStack itemStack, int size) {
        super(offsetX, offsetY, offsetX, offsetY, itemStack.getItemName());
        this.itemStack = itemStack;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.size = size;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        var pose = guiGraphics.pose();
        //? >=1.21.6 {
        pose.pushMatrix();
        pose.scale(size, size);
        guiGraphics.renderItem(itemStack, offsetX / size, offsetY / size);
        pose.popMatrix();
        //?} else {
        /*pose.pushPose();
        pose.scale(size, size, size);
        guiGraphics.renderItem(itemStack, offsetX / size, offsetY / size);
        pose.popPose();
        *///?}
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
