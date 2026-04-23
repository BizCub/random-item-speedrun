package com.bizcub.randomItemSpeedrun.mixin;

import com.bizcub.randomItemSpeedrun.Game;
import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "tickServer", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Main.game == null) return;
        ris$checkCollectItem();
    }

    @Unique
    private void ris$checkCollectItem() {
        Minecraft minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        Game game = Main.game;

        if (server != null
                /*? >=1.20.3*/ && !server.isPaused()
                /*? <=1.20.2*/ //&& !minecraft.isPaused()
                && game.isStarted()
        ) {
            game.addTick();

            String itemId = Utils.convertComponentToId(Utils.getNameFromItemStack(game.getItemStack()));
            for (var player : server.getPlayerList().getPlayers()) {
                ArrayList<String> itemsId = new ArrayList<>();
                player.inventoryMenu.getItems().forEach(item ->
                        itemsId.add(Utils.convertComponentToId(Utils.getNameFromItemStack(item))));

                if (itemsId.contains(itemId)) {
                    game.stop(true);
                    server.getPlayerList().getPlayers().forEach(serverPlayer ->
                            serverPlayer.sendSystemMessage(Component.translatable("chat.game_is_stopped", player.getName(), game.getItemStack().getItemName())));
                }
            }
        }
    }
}
