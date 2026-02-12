package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ItemDisplayWidget;
import net.minecraft.world.item.ItemStack;

public class ScaledItemDisplayWidget extends ItemDisplayWidget {

    private final ItemStack itemStack;
    private final int offsetX;
    private final int offsetY;
    private final int size;

    public ScaledItemDisplayWidget(int offsetX, int offsetY, ItemStack itemStack, int size) {
        super(Minecraft.getInstance(), offsetX, offsetY, offsetX, offsetY, itemStack.getDisplayName(), itemStack, false, false);
        this.itemStack = itemStack;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.size = size;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(size, size);
        guiGraphics.renderItem(itemStack, offsetX / size, offsetY / size);
        guiGraphics.pose().popMatrix();
    }
}
