package com.bizcub.randomItemSpeedrun.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class Utils {

    public static Component getTimeComponent(int seconds, ChatFormatting color) {
        Time time = new Time(seconds);
        return Component.translatable("gui.game_start_screen.side_panel.time.time",
                time.isDaysExist() ? Component.translatable("gui.game_start_screen.side_panel.time.days", time.getDays()) : Component.empty(),
                time.isHoursExist() ? Component.translatable("gui.game_start_screen.side_panel.time.hours", time.getHours()) : Component.empty(),
                time.isMinutesExist() ? Component.translatable("gui.game_start_screen.side_panel.time.minutes", time.getMinutes()) : Component.empty(),
                Component.translatable("gui.game_start_screen.side_panel.time.seconds", time.getSeconds())
        ).withStyle(color);
    }

    public static int getPercent(int side, double percent) {
        return (int) (side / 100d * percent);
    }
}
