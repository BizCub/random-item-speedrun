package io.github.bizcub.randomItemSpeedrun.client.mixin;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
/*import net.minecraft.client.gui.render.state.GuiRenderState;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GraphicsAccessor {
    @Accessor("guiRenderState")
    GuiRenderState getGuiRenderState();
}

*///?} else {
@Mixin(GuiGraphics.class)
public class GraphicsAccessor {

}//?}
