package com.bizcub.randomItemSpeedrun.mixin;

import com.bizcub.randomItemSpeedrun.Game;
import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Main.game == null) return;
        ris$renderItem();
        ris$checkCollectItem();
    }

    @Unique
    private void ris$renderItem() {
        if (Main.game.getTime() == 0) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(Main.game.getItemStack());
        }
    }

    @Unique
    private void ris$checkCollectItem() {
        Minecraft minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        var player = minecraft.player;
        Game game = Main.game;

        if (server != null && player != null
                /*? >=1.20.3*/ && !server.isPaused()
                /*? <=1.20.2*/ //&& !minecraft.isPaused()
                && game.isStarted()
        ) {
            String itemId = Utils.convertComponentToId(Utils.getNameFromItemStack(game.getItemStack()));
            ArrayList<String> itemsId = new ArrayList<>();
            player.inventoryMenu.getItems().forEach(item ->
                    itemsId.add(Utils.convertComponentToId(Utils.getNameFromItemStack(item))));

            game.addTick();
            if (itemsId.contains(itemId)) {
                game.stop(true);
            }
        }
    }
}
