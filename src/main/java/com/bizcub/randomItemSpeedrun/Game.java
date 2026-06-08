package com.bizcub.randomItemSpeedrun;

import com.bizcub.randomItemSpeedrun.client.gui.Speedrun;
import com.bizcub.randomItemSpeedrun.main.RandomItemSpeedrunMain;
import com.bizcub.randomItemSpeedrun.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;

public class Game {

    private int time;
    private ItemStack itemStack;
    private boolean isStart = false;

    public void start() {
        if (this.isStart) return;

        if (RandomItemSpeedrunMain.allItemsId.isEmpty()) {
            //~ if >=26.1 'displayClientMessage' -> 'sendOverlayMessage'
            Minecraft.getInstance().player.sendOverlayMessage(Component.translatable("title.no_items") /*? <=1.21.11 {*//*, true *//*?}*/);
            return;
        }

        this.isStart = true;
        this.time = 0;
        this.itemStack = Utils.getItemStackFromId(getRandomItem());
    }

    public void stop(boolean isSuccess, String playerName) {
        if (!this.isStart) return;

        this.isStart = false;
        RandomItemSpeedrunMain.speedruns.add(0, new Speedrun(Utils.getIdFromItemStack(this.itemStack), playerName, isSuccess, this.time / 20, System.currentTimeMillis()));
        RandomItemSpeedrunMain.writeSpeedruns();
        RandomItemSpeedrunMain.removeDuplicateItems();
    }

    public void buttonPressed() {
        RandomItemSpeedrunMain.sendChangeGameStatusC2S();
    }

    public void changeGameStatus() {
        if (!RandomItemSpeedrunMain.game.isStarted()) {
            RandomItemSpeedrunMain.game.start();
        } else {
            RandomItemSpeedrunMain.game.stop(false, "");
        }
    }

    private String getRandomItem() {
        Random random = new Random();
        List<String> allItemsId = RandomItemSpeedrunMain.allItemsId;
        return allItemsId.get(random.nextInt(allItemsId.size()));
    }

    public void update(boolean isStart, ItemStack itemStack, int time) {
        this.isStart = isStart;
        this.itemStack = itemStack;
        this.time = time;
    }

    public void addTick() {
        this.time++;
    }

    public int getTime() {
        return this.time;
    }

    public boolean isStarted() {
        return this.isStart;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
