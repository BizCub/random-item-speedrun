package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class Game {

    private int time;
    private ItemStack itemStack;
    private boolean isStart = false;

    public void start() {
        this.time = 0;
        this.isStart = true;
        this.itemStack = Utils.getItemStackFromId(getRandomItem());
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.displayClientMessage(itemStack.getItem().getName(), false);
        }
    }

    public void stop(boolean success) {
        this.isStart = false;
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.displayClientMessage(Component.literal("game is stopped"), false);
        }
        Main.speedruns.add(new Speedrun(this.itemStack, success, this.time / 20, System.currentTimeMillis()));
    }

    public void buttonPressed() {
        if (!Main.game.isStarted()) {
            Main.game.start();
        } else {
            Main.game.stop(false);
        }
    }

    private String getRandomItem() {
        Random random = new Random();
        return Main.allItemsId.get(random.nextInt(Main.allItemsId.size()));
    }

    public void addTick() {
        time++;
    }

    public boolean isStarted() {
        return isStart;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
