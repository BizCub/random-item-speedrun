package com.bizcub.randomItemSpeedrun.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SpeedrunEntry extends ObjectSelectionList.Entry<SpeedrunEntry> {

    public Speedrun speedrun;
    protected final Minecraft client = Minecraft.getInstance();

    public SpeedrunEntry(Speedrun speedrun) {
        this.speedrun = speedrun;
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                this.speedrun.isSuccess()
                        ? Identifier.withDefaultNamespace("pending_invite/accept")
                        : Identifier.withDefaultNamespace("pending_invite/reject"),
                (int) (this.getWidth() * 0.05),
                this.getY() + (int) (this.getHeight() / 7),
                20,
                20
        );
        guiGraphics.renderItem(
                this.speedrun.itemStack(),
                (int) (this.getWidth() * 0.15),
                this.getY() + (int) (this.getHeight() / 5.3)
        );
        guiGraphics.drawString(
                this.client.font,
                Component.translatable("gui.game_start_screen.entry",
                        this.speedrun.itemStack().getItem().getName(),
                        Component.literal("- " + this.speedrun.time()).withStyle(ChatFormatting.GRAY)
                ),
                (int) (this.getWidth() * 0.23),
                this.getY() + (int) (this.getHeight() / 2.6),
                -1
        );
    }

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.itemStack().getItem());
    }
}
