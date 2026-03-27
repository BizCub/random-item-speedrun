package com.bizcub.randomItemSpeedrun.util;

import com.bizcub.randomItemSpeedrun.config.Compat;
import com.bizcub.randomItemSpeedrun.config.Configs;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String MOD_ID = /*$ mod_id*/ "random_item_speedrun";
    public static final File SPEEDRUNS_FILE = new File("config/" + MOD_ID + "/speedruns.json");

    public static final KeyMapping OPEN_SCREEN = new KeyMapping(
            "key." + MOD_ID + ".open_game_start_screen",
            InputConstants.KEY_Y,
            /*? >=1.21.9*/ KeyMapping.Category.MISC
            /*? <=1.21.8*/ //KeyMapping.CATEGORY_MISC
    );

    public static final KeyMapping QUICK_START = new KeyMapping(
            "key." + MOD_ID + ".quick_start",
            InputConstants.UNKNOWN.getValue(),
            /*? >=1.21.9*/ KeyMapping.Category.MISC
            /*? <=1.21.8*/ //KeyMapping.CATEGORY_MISC
    );

    public static int getHudColor() {
        if (Compat.isClothConfigLoaded()) return Configs.getInstance().hudColor + 0xff000000;
        return 0xffffffff;
    }

    public static RemovableItems notEasyItems() {
        ArrayList<String> equalItems = new ArrayList<>(List.of("pale_hanging_moss", "enchanting_table", "wind_charge", "music_disc_tears", "music_disc_relic", "music_disc_creator_music_box", "totem_of_undying", "spectral_arrow", "ominous_bottle", "mojang_banner_pattern", "enchanted_golden_apple", "conduit", "warped_roots", "weeping_vines", "twisting_vines", "dried_ghast", "turtle_egg", "jukebox", "glowstone", "glowstone_dust", "comparator", "daylight_detector", "observer", "tadpole_bucket", "ghast_tear", "experience_bottle", "trident", "heart_of_the_sea", "trial_key", "respawn_anchor"));
        equalItems.addAll(notMediumItems().equalItems());
        equalItems.addAll(notHardItems().equalItems());
        equalItems.addAll(impossibleItems().equalItems());

        ArrayList<String> containItems = new ArrayList<>(List.of("mangrove", "pale_oak", "pale_moss", "crimson", "warped", "resin", "prismarine", "nether", "basalt", "blackstone", "diamond", "exposed", "quartz", "red_sand", "candle", "anvil", "soul", "nautilus_armor", "splash_potion", "pottery_sherd", "smithing_template", "waxed", "eyeblossom", "honey"));
        containItems.addAll(notMediumItems().containItems());
        containItems.addAll(notHardItems().containItems());
        containItems.addAll(impossibleItems().containItems());

        return new RemovableItems(equalItems, containItems);
    }

    public static RemovableItems notMediumItems() {
        ArrayList<String> equalItems = new ArrayList<>(List.of("diamond_block", "podzol", "mycelium", "nylium", "amethyst_cluster", "tall_grass", "large_fern", "tube_coral_block", "brain_coral_block", "bubble_coral_block", "fire_coral_block", "horn_coral_block", "tube_coral", "brain_coral", "bubble_coral", "fire_coral", "horn_coral", "dead_tube_coral", "dead_brain_coral", "dead_bubble_coral", "dead_fire_coral", "dead_horn_coral", "skull_banner_pattern", "creeper_banner_pattern", "piglin_banner_pattern", "ender_chest", "elytra", "echo_shard", "breeze_rod", "bee_nest", "end_crystal", "ender_eye", "end_rod", "music_disc_otherside", "music_disc_pigstep", "ominous_trial_key", "creaking_heart", "disc_fragment_5", "sniffer_egg", "pitcher_plant", "pitcher_pod", "torchflower", "torchflower_seeds", "grass_block", "ancient_debris"));
        equalItems.addAll(notHardItems().equalItems());
        equalItems.addAll(impossibleItems().equalItems());

        ArrayList<String> containItems = new ArrayList<>(List.of("end_stone", "purpur", "exposed", "weathered", "oxidize", "shulker", "amethyst_bud", "mushroom_block", "coral_fan", "sculk", "head", "skeleton_skull", "dragon", "netherite", "chorus", "blaze", "_ore", "horse_armor", "tipped_arrow", "lingering_potion", "froglight", "bulb"));
        containItems.addAll(notHardItems().containItems());
        containItems.addAll(impossibleItems().containItems());

        return new RemovableItems(equalItems, containItems);
    }

    public static RemovableItems notHardItems() {
        ArrayList<String> equalItems = new ArrayList<>(List.of("netherite_block", "deepslate_emerald_ore", "wither_rose", "beacon", "nether_star", "mace", "heavy_core", "recovery_compass", "music_disc_13", "music_disc_cat", "music_disc_blocks", "music_disc_chirp", "music_disc_far", "music_disc_mall", "music_disc_mellohi", "music_disc_stal", "music_disc_strad", "music_disc_ward", "music_disc_11", "music_disc_wait", "music_disc_creator", "music_disc_precipice", "music_disc_5", "guster_banner_pattern", "flow_banner_pattern", "bolt_armor_trim_smithing_template", "vex_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "flow_armor_trim_smithing_template", "spire_armor_trim_smithing_template"));
        equalItems.addAll(impossibleItems().equalItems());

        ArrayList<String> containItems = new ArrayList<>(List.of());
        containItems.addAll(impossibleItems().containItems());

        return new RemovableItems(equalItems, containItems);
    }

    public static RemovableItems impossibleItems() {
        ArrayList<String> equalItems = new ArrayList<>(List.of("player_head", "budding_amethyst", "frogspawn", "air", "debug_stick", "bedrock", "barrier", "jigsaw", "light", "end_portal_frame", "reinforced_deepslate", "farmland", "dirt_path", "vault", "suspicious_sand", "suspicious_gravel", "knowledge_book", "petrified_oak_slab"));
        ArrayList<String> containItems = new ArrayList<>(List.of("command_block", "test", "structure", "infested", "spawn_egg", "spawner"));

        return new RemovableItems(equalItems, containItems);
    }
}
