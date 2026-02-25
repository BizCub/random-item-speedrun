package com.bizcub.randomItemSpeedrun.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import com.bizcub.randomItemSpeedrun.gui.ZoomedItemPIPRenderer;
import com.bizcub.randomItemSpeedrun.gui.ZoomedItemRenderState;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;", remap = false))
    private ImmutableMap.Builder<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> addRenderer(
            Operation<ImmutableMap.Builder<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>>> original,
            @Local(argsOnly = true) MultiBufferSource.BufferSource bufferSource
    ) {
        ImmutableMap.Builder<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> builder = original.call();
        builder.put(ZoomedItemRenderState.class, new ZoomedItemPIPRenderer(bufferSource));
        return builder;
    }
}

//?} else {
/*@Mixin(Minecraft.class)
public class GuiRendererMixin {

}*///?}
