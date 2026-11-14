package io.github.bizcub.randomItemSpeedrun.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Utils {

    public static Component getTimeComponent(int seconds, int color) {
        Time time = new Time(seconds);
        return Component.translatable("gui.game_start_screen.side_panel.time.time",
                time.isDaysExist() ? Component.translatable("gui.game_start_screen.side_panel.time.days", time.getDays()) : Component.empty(),
                time.isHoursExist() ? Component.translatable("gui.game_start_screen.side_panel.time.hours", time.getHours()) : Component.empty(),
                time.isMinutesExist() ? Component.translatable("gui.game_start_screen.side_panel.time.minutes", time.getMinutes()) : Component.empty(),
                Component.translatable("gui.game_start_screen.side_panel.time.seconds", time.getSeconds())
        ).withStyle(style -> style.withColor(color));
    }

    public static int getPercent(int side, double percent) {
        return (int) (side / 100d * percent);
    }

    public static ItemStack getItemStackFromId(String id) {
        Identifier identifier = Identifier.tryParse(id);
        return new ItemStack(identifier == null ? Items.BARRIER
                : BuiltInRegistries.ITEM.getOptional(identifier).orElse(Items.BARRIER));
    }

    public static String getIdFromItemStack(ItemStack itemStack) {
        Identifier id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return id.getNamespace().equals("minecraft") ? id.getPath() : id.toString();
    }

    public static Identifier getIdentifier(String id) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, id);
    }
}
