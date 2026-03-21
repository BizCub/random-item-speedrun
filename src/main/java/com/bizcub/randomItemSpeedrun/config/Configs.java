package com.bizcub.randomItemSpeedrun.config;

import com.bizcub.randomItemSpeedrun.Main;
import com.bizcub.randomItemSpeedrun.util.Constants;
//import me.shedaniel.autoconfig.AutoConfig;
//import me.shedaniel.autoconfig.ConfigData;
//import me.shedaniel.autoconfig.annotation.Config;
//import me.shedaniel.autoconfig.annotation.ConfigEntry;
//import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
//import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
//import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.NotNull;

//@Config(name = Constants.MOD_ID + "/config")
public class Configs /*implements ConfigData*/ {

    public static void init() {
//        AutoConfig.register(Configs.class, GsonConfigSerializer::new);
//
//        AutoConfig.getConfigHolder(Configs.class).registerSaveListener((manager, data) -> {
//            Main.setDifficulty();
//            return InteractionResult.SUCCESS;
//        });
    }

//    public static Configs getInstance() {
//        return AutoConfig.getConfigHolder(Configs.class).getConfig();
//    }

//    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public Difficulty difficulty = Difficulty.NORMAL;

//    @ConfigEntry.Gui.Tooltip
    public boolean isHudRender = true;

//    @ConfigEntry.ColorPicker
    public int hudColor = 0xffffff;

    public enum Difficulty /*implements SelectionListEntry.Translatable*/ {
        EASY("easy"),
        NORMAL("normal"),
        HARD("hard"),
        HARDCORE("hardcore");

        private final String key;

        Difficulty(String key) {
            this.key = "text.autoconfig.random_item_speedrun/config.option.difficulty." + key;
        }

//        @Override
        public @NotNull String getKey() {
            return key;
        }
    }
}
