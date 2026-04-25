package com.bizcub.randomItemSpeedrun.main;

import com.bizcub.randomItemSpeedrun.Game;
import com.bizcub.randomItemSpeedrun.client.config.Compat;
import com.bizcub.randomItemSpeedrun.client.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.network.*;
import com.bizcub.randomItemSpeedrun.util.Constants;
import com.bizcub.randomItemSpeedrun.util.RemovableItems;
import com.bizcub.randomItemSpeedrun.util.Utils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RandomItemSpeedrunMain {
    public static List<String> allItemsId = new ArrayList<>();
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;

    public static void init() {
        readSpeedruns();
        setDifficulty();
        game = new Game();
    }

    public static void setDifficulty() {
        if (Compat.isClothConfigLoaded()) {
            switch (Constants.getConfig().difficulty()) {
                case EASY -> fillItemsList(Constants.notEasyItems());
                case NORMAL -> fillItemsList(Constants.notMediumItems());
                case HARD -> fillItemsList(Constants.notHardItems());
                case HARDCORE -> fillItemsList(Constants.impossibleItems());
                default -> {}
            }
        } else {
            fillItemsList(Constants.notMediumItems());
        }
    }

    private static void fillItemsList(RemovableItems items) {
        allItemsId.clear();
        BuiltInRegistries.ITEM.stream().toList().forEach(item ->
                allItemsId.add(Utils.convertComponentToId(item.getDescriptionId())));

        allItemsId.removeAll(items.equalItems());
        for (String item : items.containItems())
            allItemsId.removeIf(id -> id.contains(item));
    }

    public static void serverTick(MinecraftServer server) {
        if (game == null) return;

        if (server != null
                /*? >=1.20.3 {*/ && !server.isPaused()
                /*?} else */ //&& !minecraft.isPaused()
                && game.isStarted()
        ) {
            game.addTick();

            String itemId = Utils.convertComponentToId(Utils.getNameFromItemStack(game.getItemStack()));
            for (var player : server.getPlayerList().getPlayers()) {
                ArrayList<String> itemsId = new ArrayList<>();
                player.inventoryMenu.getItems().forEach(item ->
                        itemsId.add(Utils.convertComponentToId(Utils.getNameFromItemStack(item))));

                if (itemsId.contains(itemId)) {
                    game.stop(true, player.getName().getString());
                    server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.sendSystemMessage(Component.translatable("chat.game_is_stopped", player.getName(), game.getItemStack().getItemName()));
                        sendSoundS2C(serverPlayer, SoundEvents.PLAYER_LEVELUP);
                        sendSpeedrunsS2C(serverPlayer);
                    });
                }
            }
        }
    }

    public static void sendChangeGameStatusC2S() {
        ClientPlayNetworking.send(new ChangeGameStatusPayloadC2S());
    }

    public static void sendAnimationS2C(ServerPlayer player) {
        ServerPlayNetworking.send(player, new AnimationPayloadS2C(game.getItemStack()));
    }

    public static void sendSpeedrunsS2C(ServerPlayer player) {
        ServerPlayNetworking.send(player, new SpeedrunsPayloadS2C(speedruns));
    }

    public static void sendSoundS2C(ServerPlayer player, SoundEvent soundEvent) {
        ServerPlayNetworking.send(player, new SoundPayloadS2C(soundEvent));
    }

    public static void sendHUDS2C(ServerPlayer player) {
        ServerPlayNetworking.send(player, new HUDPayloadS2C(
                game.getItemStack() != null ? game.getItemStack() : new ItemStack(Items.CACTUS),
                game.isStarted() ? game.getTime() : 0,
                game.isStarted()
        ));
    }

    public static void readSpeedruns() {
        Gson gson = new Gson();
        try {
            Constants.SPEEDRUNS_FILE.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(Constants.SPEEDRUNS_FILE)) {
            Type listType = new TypeToken<List<Speedrun>>() {}.getType();
            List<Speedrun> tests = gson.fromJson(reader, listType);
            if (tests != null) speedruns.addAll(tests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSpeedruns() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(Constants.SPEEDRUNS_FILE)) {
            gson.toJson(speedruns, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
