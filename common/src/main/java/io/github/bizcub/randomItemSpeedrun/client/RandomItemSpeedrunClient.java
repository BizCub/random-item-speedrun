package io.github.bizcub.randomItemSpeedrun.client;

import io.github.bizcub.randomItemSpeedrun.Game;
import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigClient;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelperClient;
import io.github.bizcub.randomItemSpeedrun.network.ChangeGameStatusPayloadC2S;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;
import java.util.function.Consumer;

public class RandomItemSpeedrunClient {
    public static ArrayList<Speedrun> speedruns = new ArrayList<>();
    public static Game game;
    private static Consumer<ChangeGameStatusPayloadC2S> sendToServer;

    public static void init(Consumer<ChangeGameStatusPayloadC2S> sendToServer) {
        RandomItemSpeedrunClient.sendToServer = sendToServer;
        game = new Game();

        ConfigHelperClient.init();
    }

    public static final KeyMapping OPEN_SCREEN = new KeyMapping(
            "key." + Constants.MOD_ID + ".open_game_start_screen",
            InputConstants.KEY_Y,
            KeyMapping.Category.MISC
    );

    public static final KeyMapping QUICK_START = new KeyMapping(
            "key." + Constants.MOD_ID + ".quick_start",
            InputConstants.UNKNOWN.getValue(),
            KeyMapping.Category.MISC
    );

    public static void sendChangeGameStatus() {
        sendToServer.accept(new ChangeGameStatusPayloadC2S());
    }

    public static void renderHud(GuiGraphicsExtractor graphics) {
        if (!game.isStarted() || !ConfigClient.get().isHudRender()) return;

        graphics.item(
                game.getItemStack(),
                Utils.getPercent(graphics.guiWidth(), 0.75),
                Utils.getPercent(graphics.guiHeight(), 2)
        );

        double offsetXPercent = 5;
        double offsetYPercent = 1.5;
        int color = ConfigClient.get().hudColor();

        graphics.text(
                Minecraft.getInstance().font,
                Utils.removeBracketsOrDefault(game.getItemStack().getItemName().getString()),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiHeight(), offsetYPercent),
                color
        );
        offsetYPercent += 4;
        graphics.text(
                Minecraft.getInstance().font,
                Utils.getTimeComponent(game.getTime() / 20, color),
                Utils.getPercent(graphics.guiWidth(), offsetXPercent),
                Utils.getPercent(graphics.guiHeight(), offsetYPercent),
                color
        );
    }
}
