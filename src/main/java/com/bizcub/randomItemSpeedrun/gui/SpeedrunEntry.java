package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
/*? >=1.21.5*/ import net.minecraft.client.renderer.RenderPipelines;
/*? <=1.21.5*/ //import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SpeedrunEntry extends ObjectSelectionList.Entry<SpeedrunEntry> {

    public Speedrun speedrun;
    protected final Minecraft client = Minecraft.getInstance();

    public SpeedrunEntry(Speedrun speedrun) {
        this.speedrun = speedrun;
    }

    private void render(GuiGraphics guiGraphics, int y, int width, int height) {
        guiGraphics.blitSprite(
                /*? >=1.21.6*/ RenderPipelines.GUI_TEXTURED,
                /*? <=1.21.5 && >=1.21.2*/ //RenderType::guiTextured,
                this.speedrun.isSuccess()
                        ? Identifier.withDefaultNamespace("pending_invite/accept")
                        : Identifier.withDefaultNamespace("pending_invite/reject"),
                Utils.getPercent(width, 5),
                y + Utils.getPercent(height, 17),
                20,
                20
        );
        guiGraphics.renderItem(
                this.speedrun.getItem(),
                Utils.getPercent(width, 12),
                y + Utils.getPercent(height, 22)
        );
        guiGraphics.drawString(
                this.client.font,
                Component.translatable("gui.game_start_screen.entry",
                        this.speedrun.getItem().getItemName().getString(),
                        Component.translatable("gui.game_start_screen.entry.separator").withStyle(ChatFormatting.GRAY),
                        Utils.getTimeComponent(speedrun.time(), ChatFormatting.GRAY.getColor())
                ),
                Utils.getPercent(width, 18),
                y + Utils.getPercent(height, 37),
                -1
        );
    }

    //? >= 1.21.9 {
    @Override
    public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
        render(guiGraphics, this.getY(), this.getWidth(), this.getHeight());
    }

    //?} else {
    /*@Override
    public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float deltaTime) {
        render(guiGraphics, top, width, height);
    }*///?}

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.getItem().getItemName());
    }
}
