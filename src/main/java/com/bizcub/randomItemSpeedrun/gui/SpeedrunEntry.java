package com.bizcub.randomItemSpeedrun.gui;

import com.bizcub.randomItemSpeedrun.util.Utils;
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
                Utils.getPercent(this.getWidth(), 5),
                this.getY() + Utils.getPercent(this.getHeight(), 17),
                20,
                20
        );
        guiGraphics.renderItem(
                this.speedrun.getItem(),
                Utils.getPercent(this.getWidth(), 12),
                this.getY() + Utils.getPercent(getHeight(), 22)
        );
        guiGraphics.drawString(
                this.client.font,
                Component.translatable("gui.game_start_screen.entry",
                        this.speedrun.getItem().getItem().getName(),
                        Component.translatable("gui.game_start_screen.entry.separator").withStyle(ChatFormatting.GRAY),
                        Utils.getTimeComponent(speedrun.time(), ChatFormatting.GRAY.getColor())
                ),
                Utils.getPercent(this.getWidth(), 18),
                this.getY() + Utils.getPercent(getHeight(), 37),
                -1
        );
    }

    @Override
    public Component getNarration() {
        return Component.translatable("narrator.select", speedrun.getItem().getItemName());
    }
}
