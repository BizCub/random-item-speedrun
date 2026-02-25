package com.bizcub.randomItemSpeedrun.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PictureInPictureRenderer.class)
public interface PictureInPictureRendererAccessor {
    @Accessor("textureView")
    @Nullable
    GpuTextureView getTextureView();
}

//?} else {
/*@Mixin(Minecraft.class)
public class PictureInPictureRendererAccessor {

}*///?}
