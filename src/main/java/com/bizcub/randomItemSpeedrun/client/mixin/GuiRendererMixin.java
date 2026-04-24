package com.bizcub.randomItemSpeedrun.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 && !neoforge {
import com.bizcub.randomItemSpeedrun.client.gui.ScaledItemPIPRenderer;
import com.bizcub.randomItemSpeedrun.client.gui.ScaledItemRenderState;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {

    @Mutable @Shadow @Final private Map<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> pictureInPictureRenderers;
    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addRenderer(CallbackInfo ci) {
        var map = new HashMap<>(this.pictureInPictureRenderers);
        map.put(ScaledItemRenderState.class, new ScaledItemPIPRenderer(this.bufferSource));
        this.pictureInPictureRenderers = map;
    }
}

//?} else {
/*@Mixin(value = {})
public class GuiRendererMixin {

}*///?}
