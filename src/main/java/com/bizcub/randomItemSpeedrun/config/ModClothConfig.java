package com.bizcub.randomItemSpeedrun.config;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Constants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.NotNull;

@Config(name = Constants.MOD_ID + "/config")
public class ModClothConfig implements ModConfig, ConfigData {

    public static void init() {
        AutoConfig.getConfigHolder(ModClothConfig.class).registerSaveListener((manager, data) -> {
            Main.setDifficulty();
            return InteractionResult.SUCCESS;
        });
    }

    public static ModClothConfig getInstance() {
        return AutoConfig.register(ModClothConfig.class, GsonConfigSerializer::new).getConfig();
    }

    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public Difficulty difficulty = ModConfig.super.difficulty();

    @ConfigEntry.Gui.Tooltip
    public boolean isHudRender = ModConfig.super.isHudRender();

    @ConfigEntry.ColorPicker
    public int hudColor = ModConfig.super.hudColor();

    public enum Difficulty implements SelectionListEntry.Translatable {
        EASY("easy"),
        NORMAL("normal"),
        HARD("hard"),
        HARDCORE("hardcore");

        private final String key;

        Difficulty(String key) {
            this.key = "text.autoconfig.random_item_speedrun/config.option.difficulty." + key;
        }

        @Override
        public @NotNull String getKey() {
            return key;
        }
    }

    @Override
    public Difficulty difficulty() {
        return this.difficulty;
    }

    @Override
    public boolean isHudRender() {
        return this.isHudRender;
    }

    @Override
    public int hudColor() {
        return this.hudColor;
    }
}
