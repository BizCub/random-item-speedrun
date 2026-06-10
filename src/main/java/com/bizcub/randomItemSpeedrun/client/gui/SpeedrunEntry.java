package com.bizcub.randomItemSpeedrun.client.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
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

    private void render(GuiGraphicsExtractor graphics, int y, int width, int height) {
        Identifier accept =
                /*? >=1.20.2*/ Utils.getDefaultIdentifier("pending_invite/accept");
                /*? <=1.20.1*/ //Utils.getCustomIdentifier("realms", "textures/gui/realms/accept_icon.png");
        Identifier reject =
                /*? >=1.20.2*/ Utils.getDefaultIdentifier("pending_invite/reject");
                /*? <=1.20.1*/ //Utils.getCustomIdentifier("realms", "textures/gui/realms/reject_icon.png");
        Identifier inProgress =
                /*? >=1.20.2*/ Utils.getDefaultIdentifier("statistics/item_crafted");
                /*? <=1.20.1*/ //Utils.getIdentifier("textures/gui/sprites/item_crafted.png");

        /*? >=1.20.2*/ graphics.blitSprite(
        /*? <=1.20.1*/ //graphics.blit(
                /*? >=1.21.6*/ RenderPipelines.GUI_TEXTURED,
                /*? <=1.21.5 && >=1.21.2*/ //RenderType::guiTextured,
                switch (this.speedrun.isSuccess()) {
                    case SUCCESS -> accept;
                    case FAILURE -> reject;
                    case IN_PROGRESS -> inProgress;
                },
                Utils.getPercent(width, 5),
                y + Utils.getPercent(height, 17),
                /*? >=1.20.2*/ 20, 20
                /*? <=1.20.1*/ //0, 0, 18, 18, 37, 18
        );
        //~ if >=26.1 'renderItem(' -> 'item('
        graphics.item(
                this.speedrun.getItem(),
                Utils.getPercent(width, 12),
                y + Utils.getPercent(height, 22)
        );
        //~ draw_string
        graphics.text(
                this.client.font,
                Component.translatable("gui.game_start_screen.entry",
                        Utils.removeBracketsOrDefault(this.speedrun.getItem().getItemName().getString()),
                        Component.translatable("gui.game_start_screen.entry.separator").withStyle(ChatFormatting.GRAY),
                        Utils.getTimeComponent(speedrun.time(), 11184810)
                ),
                Utils.getPercent(width, 18),
                y + Utils.getPercent(height, 37),
                -1
        );
        //~ !draw_string
    }

    //? >=1.21.9 {
    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        render(graphics, this.getY(), this.getWidth(), this.getHeight());
    }

    //?} else {
    /*@Override
    public void render(GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float deltaTime) {
        render(graphics, top, width, height);
    }*///?}

    //? <=1.20.4 {
    /*@Override
    public boolean mouseClicked(double mouseX, double mouseY, int delta) {
        list.setSelected(this);
        return true;
    }*///?}

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.getItem().getItemName());
    }
}
