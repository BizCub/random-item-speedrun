package io.github.bizcub.randomItemSpeedrun.client.gui;

import io.github.bizcub.randomItemSpeedrun.client.mixin.GraphicsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ScaledItemDisplayWidget extends AbstractWidget {

    private ItemStack itemStack;
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

    public void updateItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        var itemStackRenderState = new ItemStackRenderState();
        Minecraft mc = Minecraft.getInstance();

        mc.getItemModelResolver().updateForTopItem(itemStackRenderState, itemStack, ItemDisplayContext.GUI, mc.level, mc.player, 0);

        ScaledItemRenderState state = new ScaledItemRenderState(
                itemStackRenderState,
                offsetX,
                offsetY,
                offsetX + size,
                offsetY + size,
                0.0f,
                1.0f,
                null
        );

        ((GraphicsAccessor) graphics).getGuiRenderState().addPicturesInPictureState(state);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
