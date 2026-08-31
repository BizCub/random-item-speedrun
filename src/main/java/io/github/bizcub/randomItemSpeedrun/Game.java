package io.github.bizcub.randomItemSpeedrun;

import io.github.bizcub.randomItemSpeedrun.util.Utils;
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

        if (RandomItemSpeedrun.allItemsId.isEmpty()) {
            //~ if >=26.1 'displayClientMessage' -> 'sendOverlayMessage'
            Minecraft.getInstance().player.sendOverlayMessage(Component.translatable("title.no_items") /*? <=1.21.11 {*//*, true *//*?}*/);
            return;
        }

        this.isStart = true;
        this.time = 0;
        this.itemStack = Utils.getItemStackFromId(getRandomItem());

        RandomItemSpeedrun.speedruns.add(0, new Speedrun(Utils.getIdFromItemStack(this.itemStack), "-", Speedrun.Status.IN_PROGRESS, 0, System.currentTimeMillis()));
    }

    public void stop(Speedrun.Status isSuccess, String playerName) {
        if (!this.isStart) return;

        this.isStart = false;
        RandomItemSpeedrun.speedruns.set(0, new Speedrun(Utils.getIdFromItemStack(this.itemStack), playerName, isSuccess, this.time / 20, System.currentTimeMillis()));
        RandomItemSpeedrun.writeSpeedruns();
        RandomItemSpeedrun.removeDuplicateItems();
    }

    public void buttonPressed() {
        RandomItemSpeedrun.sendChangeGameStatusC2S();
    }

    public void changeGameStatus() {
        if (!RandomItemSpeedrun.game.isStarted()) {
            RandomItemSpeedrun.game.start();
        } else {
            RandomItemSpeedrun.game.stop(Speedrun.Status.FAILURE, "");
        }
    }

    private String getRandomItem() {
        Random random = new Random();
        List<String> allItemsId = RandomItemSpeedrun.allItemsId;
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
