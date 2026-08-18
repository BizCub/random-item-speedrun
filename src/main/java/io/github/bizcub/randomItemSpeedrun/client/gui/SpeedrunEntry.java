package io.github.bizcub.randomItemSpeedrun.client.gui;

import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpeedrunEntry extends ObjectSelectionList.Entry<SpeedrunEntry> {

    public Speedrun speedrun;
    protected final Minecraft client = Minecraft.getInstance();
    private final SpeedrunWidget list;

    public SpeedrunEntry(Speedrun speedrun, SpeedrunWidget speedrunWidget) {
        this.speedrun = speedrun;
        this.list = speedrunWidget;
    }

    private void render(GuiGraphics graphics, int y, int width, int height, int mouseX, int mouseY) {
        ResourceLocation accept =
                /*? >=1.20.2*/ //Utils.getDefaultResourceLocation("pending_invite/accept");
                /*? <=1.20.1*/ Utils.getCustomResourceLocation("realms", "textures/gui/realms/accept_icon.png");
        ResourceLocation reject =
                /*? >=1.20.2*/ //Utils.getDefaultResourceLocation("pending_invite/reject");
                /*? <=1.20.1*/ Utils.getCustomResourceLocation("realms", "textures/gui/realms/reject_icon.png");
        ResourceLocation inProgress =
                /*? >=1.20.2*/ //Utils.getDefaultResourceLocation("statistics/item_crafted");
                /*? <=1.20.1*/ Utils.getResourceLocation("textures/gui/sprites/item_crafted.png");

        /*? >=1.20.2*/ //graphics.blitSprite(
        /*? <=1.20.1*/ graphics.blit(
                /*? >=1.21.6*/ //RenderPipelines.GUI_TEXTURED,
                /*? <=1.21.5 && >=1.21.2*/ //RenderType::guiTextured,
                switch (this.speedrun.isSuccess()) {
                    case SUCCESS -> accept;
                    case FAILURE -> reject;
                    case IN_PROGRESS -> inProgress;
                },
                Utils.getPercent(width, 5),
                y + Utils.getPercent(height, 17),
                /*? >=1.20.2*/ //20, 20
                /*? <=1.20.1*/ 0, 0, 18, 18, 37, 18
        );
        //~ if >=26.1 'renderItem(' -> 'item('
        graphics.renderItem(
                this.speedrun.getItem(),
                Utils.getPercent(width, 12),
                y + Utils.getPercent(height, 22)
        );
        int textX = Utils.getPercent(width, 18);
        int textY = y + Utils.getPercent(height, 37);
        Component textItem = Component.translatable("gui.game_start_screen.entry",
                Utils.removeBracketsOrDefault(this.speedrun.getItem().getDisplayName().getString()),
                Component.translatable("gui.game_start_screen.entry.separator").withStyle(ChatFormatting.GRAY),
                Utils.getTimeComponent(speedrun.time(), 11184810)
        );
        //~ draw_string
        graphics.drawString(this.client.font, textItem, textX, textY, -1);
        //~ !draw_string
        int textWidth = this.client.font.width(textItem.getVisualOrderText());
        int textHeight = 9;
        if (mouseX >= textX && mouseX <= textX + textWidth && mouseY >= textY && mouseY <= textY + textHeight) {
            //~ if >=1.21.6 'renderTooltip' -> 'setTooltipForNextFrame'
            graphics.renderTooltip(
                    this.client.font,
                    Screen.getTooltipFromItem(this.client, this.speedrun.getItem()),
                    this.speedrun.getItem().getTooltipImage(),
                    mouseX,
                    mouseY
            );
        }
    }

    //? >=1.21.9 {
    /*@Override
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float a) {
        render(graphics, this.getY(), this.getWidth(), this.getHeight(), mouseX, mouseY);
    }

    *///?} else {
    @Override
    public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float deltaTime) {
        render(graphics, top, width, height, mouseX, mouseY);
    }//?}

    //? <=1.20.4 {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int delta) {
        list.setSelected(this);
        return true;
    }//?}

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.getItem().getDisplayName());
    }
}
