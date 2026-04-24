package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class Game {

    public int time;
    public ItemStack itemStack;
    public boolean isStart = false;

    public void start() {
        if (this.isStart) return;

        this.isStart = true;
        this.time = 0;
        this.itemStack = Utils.getItemStackFromId(getRandomItem());
    }

    public void stop(boolean isSuccess, String playerName) {
        if (!this.isStart) return;

        this.isStart = false;
        Main.speedruns.add(0, new Speedrun(Utils.getIdFromItemStack(this.itemStack), playerName, isSuccess, this.time / 20, System.currentTimeMillis()));
        var player = Minecraft.getInstance().player;
        if (player != null && isSuccess) {
            player.level()
                    /*? >=1.21.5 {*/.playPlayerSound(
                    //?} else {
                    /*.playSound(
                    player,
                    player.blockPosition(),*///?}
                    SoundEvents.BELL_BLOCK,
                    SoundSource.PLAYERS,
                    5.0F,
                    1.0F
            );
        }
        Utils.writeSpeedruns();
    }

    public void buttonPressed() {
        Utils.sendChangeGameStatusC2S();
    }

    public void changeGameStatus() {
        if (!Main.game.isStarted()) {
            Main.game.start();
        } else {
            Main.game.stop(false, "");
        }
    }

    private String getRandomItem() {
        Random random = new Random();

        System.out.println(Main.allItemsId);

        return Main.allItemsId.get(random.nextInt(Main.allItemsId.size()));
    }

    public boolean isStarted() {
        return isStart;
    }

    public void addTick() {
        time++;
    }

    public int getTime() {
        return time;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
