package com.bizcub.randomItemSpeedrun.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphicsExtractor.class)
public interface GraphicsAccessor {
    @Accessor("guiRenderState")
    GuiRenderState getGuiRenderState();
}

//?} else {
/*@Mixin(value = {})
public class GraphicsAccessor {

}*///?}
