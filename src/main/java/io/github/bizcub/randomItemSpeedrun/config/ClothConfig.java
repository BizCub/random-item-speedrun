package io.github.bizcub.randomItemSpeedrun.config;

import io.github.bizcub.randomItemSpeedrun.RandomItemSpeedrun;
import io.github.bizcub.randomItemSpeedrun.util.Constants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.world.InteractionResult;

@me.shedaniel.autoconfig.annotation.Config(name = Constants.MOD_ID + "/config")
public class ClothConfig implements Config, ConfigData {

    public static ClothConfig getInstance() {
        return AutoConfig.getConfigHolder(ClothConfig.class).getConfig();
    }

    public static void init() {
        AutoConfig.register(ClothConfig.class, GsonConfigSerializer::new).registerSaveListener((manager, data) -> {
            RandomItemSpeedrun.setDifficulty();
            RandomItemSpeedrun.removeDuplicateItems();
            return InteractionResult.SUCCESS;
        });
    }

    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public Difficulty difficulty = Config.super.difficulty();

    @ConfigEntry.Gui.Tooltip
    public boolean removeDuplicates = Config.super.removeDuplicates();

    @ConfigEntry.Gui.Tooltip
    public boolean isHudRender = Config.super.isHudRender();

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int hudColor = Config.super.hudColor();

    @Override
    public Difficulty difficulty() {
        return this.difficulty;
    }

    @Override
    public boolean removeDuplicates() {
        return this.removeDuplicates;
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
