package com.bizcub.randomItemSpeedrun.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 && !neoforge {
import com.bizcub.randomItemSpeedrun.gui.ScaledItemPIPRenderer;
import com.bizcub.randomItemSpeedrun.gui.ScaledItemRenderState;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
//? >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;//?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {

    @Mutable @Shadow @Final private Map<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> pictureInPictureRenderers;
    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addRenderer(GuiRenderState guiRenderState, MultiBufferSource.BufferSource bufferSource, /*? >=1.21.9 {*/ SubmitNodeCollector submitNodeCollector, FeatureRenderDispatcher featureRenderDispatcher, /*?}*/ List<PictureInPictureRenderer<?>> list, CallbackInfo ci) {
        var map = new HashMap<>(pictureInPictureRenderers);
        map.put(ScaledItemRenderState.class, new ScaledItemPIPRenderer(this.bufferSource));
        pictureInPictureRenderers = map;
    }
}

//?} else {
/*@Mixin(Minecraft.class)
public class GuiRendererMixin {

}*///?}
