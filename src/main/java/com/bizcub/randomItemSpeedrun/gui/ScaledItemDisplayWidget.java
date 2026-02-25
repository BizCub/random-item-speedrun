package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
//? >=1.21.6 {
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;//?}
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
        //? >=1.21.6 {
        var itemStackRenderState = new ItemStackRenderState();
        Minecraft mc = Minecraft.getInstance();

        mc.getItemModelResolver().updateForTopItem(itemStackRenderState, itemStack, ItemDisplayContext.GUI, mc.level, mc.player, 0);

        ZoomedItemRenderState state = new ZoomedItemRenderState(
                itemStackRenderState,
                offsetX,
                offsetY,
                offsetX + size,
                offsetY + size,
                0.0f,
                1.0f,
                null
        );

        guiGraphics.guiRenderState.submitPicturesInPictureState(state);

        //?} else {
        /*var pose = guiGraphics.pose();
        int scale = size / 15;

        pose.pushPose();
        pose.scale(scale, scale, scale);
        guiGraphics.renderItem(itemStack, offsetX / scale, offsetY / scale);
        pose.popPose();*///?}
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
