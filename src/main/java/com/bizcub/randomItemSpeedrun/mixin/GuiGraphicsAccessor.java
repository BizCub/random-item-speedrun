package com.bizcub.randomItemSpeedrun.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
    @Accessor("guiRenderState")
    GuiRenderState getGuiRenderState();
}

//?} else {
/*@Mixin(Minecraft.class)
public class GuiGraphicsAccessor {

}*///?}
