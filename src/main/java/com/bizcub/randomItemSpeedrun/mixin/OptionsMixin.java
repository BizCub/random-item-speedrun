package com.bizcub.randomItemSpeedrun.mixin;

import net.minecraft.client.CameraType;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin {

    @Shadow private CameraType cameraType;
    @Shadow public boolean hideGui;

    @Inject(method = "setCameraType", at = @At("TAIL"))
    public void hideHUD(CallbackInfo ci) {
        this.hideGui = !this.cameraType.isFirstPerson();
    }
}
