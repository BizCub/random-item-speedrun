package io.github.bizcub.randomItemSpeedrun.client.gui;

import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SpeedrunEntry extends ObjectSelectionList.Entry<SpeedrunEntry> {

    public Speedrun speedrun;
    protected final Minecraft client = Minecraft.getInstance();
    private final SpeedrunWidget list;

    public SpeedrunEntry(Speedrun speedrun, SpeedrunWidget speedrunWidget) {
        this.speedrun = speedrun;
        this.list = speedrunWidget;
    }

    private void render(GuiGraphicsExtractor graphics, int y, int width, int height, int mouseX, int mouseY) {
        Identifier accept = Utils.getDefaultIdentifier("pending_invite/accept");
        Identifier reject = Utils.getDefaultIdentifier("pending_invite/reject");
        Identifier inProgress = Utils.getDefaultIdentifier("statistics/item_crafted");

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,

                switch (this.speedrun.isSuccess()) {
                    case SUCCESS -> accept;
                    case FAILURE -> reject;
                    case IN_PROGRESS -> inProgress;
                },
                Utils.getPercent(width, 5),
                y + Utils.getPercent(height, 17),
                20, 20
        );
        graphics.item(
                this.speedrun.getItem(),
                Utils.getPercent(width, 12),
                y + Utils.getPercent(height, 22)
        );
        int textX = Utils.getPercent(width, 18);
        int textY = y + Utils.getPercent(height, 37);
        Component textItem = Component.translatable("gui.game_start_screen.entry",
                Utils.removeBracketsOrDefault(this.speedrun.getItem().getItemName().getString()),
                Component.translatable("gui.game_start_screen.entry.separator").withStyle(ChatFormatting.GRAY),
                Utils.getTimeComponent(speedrun.time(), 11184810)
        );
        graphics.text(this.client.font, textItem, textX, textY, -1);
        int textWidth = this.client.font.width(textItem.getVisualOrderText());
        int textHeight = 9;
        if (mouseX >= textX && mouseX <= textX + textWidth && mouseY >= textY && mouseY <= textY + textHeight) {
            graphics.setTooltipForNextFrame(
                    this.client.font,
                    Screen.getTooltipFromItem(this.client, this.speedrun.getItem()),
                    this.speedrun.getItem().getTooltipImage(),
                    mouseX,
                    mouseY
            );
        }
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        render(graphics, this.getY(), this.getWidth(), this.getHeight(), mouseX, mouseY);
    }

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.getItem().getItemName());
    }
}
