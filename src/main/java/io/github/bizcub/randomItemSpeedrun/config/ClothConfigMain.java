package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.world.InteractionResult;

@Config(name = Constants.MOD_ID + "/config_main")
public class ClothConfigMain implements ConfigMain, ConfigData {

    public static ClothConfigMain getInstance() {
        return AutoConfig.getConfigHolder(ClothConfigMain.class).getConfig();
    }

    public static void init() {
        AutoConfig.register(ClothConfigMain.class, GsonConfigSerializer::new).registerSaveListener((manager, data) -> {
            RandomItemSpeedrun.setDifficulty();
            RandomItemSpeedrun.removeDuplicateItems();
            return InteractionResult.SUCCESS;
        });
    }

    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public Difficulty difficulty = ConfigMain.super.difficulty();

    @ConfigEntry.Gui.Tooltip
    public boolean removeDuplicates = ConfigMain.super.removeDuplicates();

    @Override
    public Difficulty difficulty() {
        return this.difficulty;
    }

    @Override
    public boolean removeDuplicates() {
        return this.removeDuplicates;
    }
}
