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
        checkCollectItem();
    }

    @Unique
    private void checkCollectItem() {
        var server = Minecraft.getInstance().getSingleplayerServer();
        var player = Minecraft.getInstance().player;
        Game game = Main.game;

        if (server != null && player != null && !server.isPaused() && game.isStarted()) {
            String itemId = Utils.convertComponentToId(Utils.getIdFromItemStack(game.getItemStack()));
            ArrayList<String> itemsId = new ArrayList<>();
            player.inventoryMenu.getItems().forEach(item ->
                    itemsId.add(Utils.convertComponentToId(Utils.getIdFromItemStack(item))));

            game.addTick();
            if (itemsId.contains(itemId)) {
                game.stop(true);
            }
        }
    }
}
