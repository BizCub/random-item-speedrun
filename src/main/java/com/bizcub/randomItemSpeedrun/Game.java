package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    }

    public void stop(boolean success) {
        this.isStart = false;
        Main.speedruns.add(0, new Speedrun(Utils.getIdFromItemStack(this.itemStack), success, this.time / 20, System.currentTimeMillis()));
        var player = Minecraft.getInstance().player;
        if (player != null && success) {
            //~ if >=26.1 'displayClientMessage' -> 'sendSystemMessage'
            player.sendSystemMessage(Component.translatable("chat.game_is_stopped", itemStack.getItemName()) /*? <26.1 {*//*, false *//*?}*/);
            player.level()
                    /*? >=1.21.5*/.playPlayerSound(
                    //? <=1.21.4 {
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
