package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.platform.Services;
import com.nosiphus.nosiphuscustomblocks.world.food.ModFoods;
import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs.addToTab;
import static com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs.addToTabOptional;

public class ModItems {

    public static void init() {
        // Triggers static field initialization
    }

    private static <T extends Item> Supplier<T> register(String name, Supplier<T> itemSupplier) {
        return Services.REGISTRY.registerItem(name, itemSupplier);
    }

    private static <T extends Item> Supplier<T> registerOptionalItem(String modID, String name, Supplier<T> itemSupplier) {
        if (Services.PLATFORM.isModLoaded(modID)) {
            return register(name, itemSupplier);
        }
        return null;
    }

    // Fezzes and Bow Ties
    public static final Supplier<ArmorItem> FEZ = addToTab(register("fez",
            () -> new ArmorItem(ModArmorMaterials.BOW_TIE, ArmorItem.Type.HELMET, new Item.Properties())));
    public static final Supplier<ArmorItem> BOW_TIE = addToTab(register("bow_tie",
            () -> new ArmorItem(ModArmorMaterials.BOW_TIE, ArmorItem.Type.CHESTPLATE, new Item.Properties())));

    // Foods
    public static final Supplier<Item> CUSTARD = addToTab(register("custard",
            () -> new Item(new Item.Properties().stacksTo(1).food(ModFoods.CUSTARD))));
    public static final Supplier<Item> FISH_FINGER = addToTab(register("fish_finger",
            () -> new Item(new Item.Properties().food(ModFoods.FISH_FINGER))));

    // Sonic Screwdrivers
    public static final Supplier<Item> BLUE_SONIC_SCREWDRIVER = addToTab(register("blue_sonic_screwdriver",
            () -> new SwordItem(ModTiers.BLUE_SONIC_SCREWDRIVER, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.BLUE_SONIC_SCREWDRIVER, 3, -2.4F)))));
    public static final Supplier<Item> GREEN_SONIC_SCREWDRIVER = addToTab(register("green_sonic_screwdriver",
            () -> new SwordItem(ModTiers.GREEN_SONIC_SCREWDRIVER, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.GREEN_SONIC_SCREWDRIVER, 3, -2.4F)))));

    // TARDIS
    public static final Supplier<BlockItem> GOLDEN_ROUNDEL_BLOCK = addToTab(register("golden_roundel_block",
            () -> new BlockItem(ModBlocks.GOLDEN_ROUNDEL_BLOCK.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LABELED_TARDIS_DOOR = addToTab(register("labeled_tardis_door",
            () -> new DoubleHighBlockItem(ModBlocks.LABELED_TARDIS_DOOR.get(), new Item.Properties())));
    public static final Supplier<BlockItem> TARDIS_DOOR = addToTab(register("tardis_door",
            () -> new DoubleHighBlockItem(ModBlocks.TARDIS_DOOR.get(), new Item.Properties())));
    public static final Supplier<BlockItem> TARDIS_SLAB = addToTab(register("tardis_slab",
            () -> new BlockItem(ModBlocks.TARDIS_SLAB.get(), new Item.Properties())));

    // Ceiling Lights
    public static final Supplier<BlockItem> FLUORESCENT_PANEL_CEILING_LIGHT = addToTabOptional("yogmod", registerOptionalItem("yogmod", "fluorescent_panel_ceiling_light",
            () -> new BlockItem(ModBlocks.FLUORESCENT_PANEL_CEILING_LIGHT.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GLOWSTONE_CEILING_LIGHT = addToTab(register("glowstone_ceiling_light",
            () -> new BlockItem(ModBlocks.GLOWSTONE_CEILING_LIGHT.get(), new Item.Properties())));
    public static final Supplier<BlockItem> SEA_LANTERN_CEILING_LIGHT = addToTab(register("sea_lantern_ceiling_light",
            () -> new BlockItem(ModBlocks.SEA_LANTERN_CEILING_LIGHT.get(), new Item.Properties())));

    // Beige Stained Planks
    public static final Supplier<BlockItem> BEIGE_STAINED_PLANKS = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_planks",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_STAIRS = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_stairs",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_SLAB = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_slab",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_FENCE = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_fence",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_FENCE_GATE = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_PRESSURE_PLATE = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BEIGE_STAINED_BUTTON = addToTabOptional("yogmod", registerOptionalItem("yogmod", "beige_stained_button",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_BUTTON.get(), new Item.Properties())));

    // White Stained Planks
    public static final Supplier<BlockItem> WHITE_STAINED_PLANKS = addToTab(register("white_stained_planks",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_STAIRS = addToTab(register("white_stained_stairs",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_SLAB = addToTab(register("white_stained_slab",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_FENCE = addToTab(register("white_stained_fence",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_FENCE_GATE = addToTab(register("white_stained_fence_gate",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_PRESSURE_PLATE = addToTab(register("white_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> WHITE_STAINED_BUTTON = addToTab(register("white_stained_button",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_BUTTON.get(), new Item.Properties())));

    // Orange Stained Planks
    public static final Supplier<BlockItem> ORANGE_STAINED_PLANKS = addToTab(register("orange_stained_planks",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_STAIRS = addToTab(register("orange_stained_stairs",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_SLAB = addToTab(register("orange_stained_slab",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_FENCE = addToTab(register("orange_stained_fence",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_FENCE_GATE = addToTab(register("orange_stained_fence_gate",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_PRESSURE_PLATE = addToTab(register("orange_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> ORANGE_STAINED_BUTTON = addToTab(register("orange_stained_button",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_BUTTON.get(), new Item.Properties())));

    // Magenta Stained Planks
    public static final Supplier<BlockItem> MAGENTA_STAINED_PLANKS = addToTab(register("magenta_stained_planks",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_STAIRS = addToTab(register("magenta_stained_stairs",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_SLAB = addToTab(register("magenta_stained_slab",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_FENCE = addToTab(register("magenta_stained_fence",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_FENCE_GATE = addToTab(register("magenta_stained_fence_gate",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_PRESSURE_PLATE = addToTab(register("magenta_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> MAGENTA_STAINED_BUTTON = addToTab(register("magenta_stained_button",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_BUTTON.get(), new Item.Properties())));

    // Light Blue Stained Planks
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_PLANKS = addToTab(register("light_blue_stained_planks",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_STAIRS = addToTab(register("light_blue_stained_stairs",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_SLAB = addToTab(register("light_blue_stained_slab",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_FENCE = addToTab(register("light_blue_stained_fence",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_FENCE_GATE = addToTab(register("light_blue_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_PRESSURE_PLATE = addToTab(register("light_blue_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_BLUE_STAINED_BUTTON = addToTab(register("light_blue_stained_button",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_BUTTON.get(), new Item.Properties())));

    // Yellow Stained Planks
    public static final Supplier<BlockItem> YELLOW_STAINED_PLANKS = addToTab(register("yellow_stained_planks",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_STAIRS = addToTab(register("yellow_stained_stairs",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_SLAB = addToTab(register("yellow_stained_slab",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_FENCE = addToTab(register("yellow_stained_fence",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_FENCE_GATE = addToTab(register("yellow_stained_fence_gate",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_PRESSURE_PLATE = addToTab(register("yellow_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> YELLOW_STAINED_BUTTON = addToTab(register("yellow_stained_button",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_BUTTON.get(), new Item.Properties())));

    // Lime Stained Planks
    public static final Supplier<BlockItem> LIME_STAINED_PLANKS = addToTab(register("lime_stained_planks",
            () -> new BlockItem(ModBlocks.LIME_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_STAIRS = addToTab(register("lime_stained_stairs",
            () -> new BlockItem(ModBlocks.LIME_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_SLAB = addToTab(register("lime_stained_slab",
            () -> new BlockItem(ModBlocks.LIME_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_FENCE = addToTab(register("lime_stained_fence",
            () -> new BlockItem(ModBlocks.LIME_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_FENCE_GATE = addToTab(register("lime_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIME_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_PRESSURE_PLATE = addToTab(register("lime_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIME_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIME_STAINED_BUTTON = addToTab(register("lime_stained_button",
            () -> new BlockItem(ModBlocks.LIME_STAINED_BUTTON.get(), new Item.Properties())));

    // Pink Stained Planks
    public static final Supplier<BlockItem> PINK_STAINED_PLANKS = addToTab(register("pink_stained_planks",
            () -> new BlockItem(ModBlocks.PINK_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_STAIRS = addToTab(register("pink_stained_stairs",
            () -> new BlockItem(ModBlocks.PINK_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_SLAB = addToTab(register("pink_stained_slab",
            () -> new BlockItem(ModBlocks.PINK_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_FENCE = addToTab(register("pink_stained_fence",
            () -> new BlockItem(ModBlocks.PINK_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_FENCE_GATE = addToTab(register("pink_stained_fence_gate",
            () -> new BlockItem(ModBlocks.PINK_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_PRESSURE_PLATE = addToTab(register("pink_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.PINK_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PINK_STAINED_BUTTON = addToTab(register("pink_stained_button",
            () -> new BlockItem(ModBlocks.PINK_STAINED_BUTTON.get(), new Item.Properties())));

    // Gray Stained Planks
    public static final Supplier<BlockItem> GRAY_STAINED_PLANKS = addToTab(register("gray_stained_planks",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_STAIRS = addToTab(register("gray_stained_stairs",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_SLAB = addToTab(register("gray_stained_slab",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_FENCE = addToTab(register("gray_stained_fence",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_FENCE_GATE = addToTab(register("gray_stained_fence_gate",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_PRESSURE_PLATE = addToTab(register("gray_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GRAY_STAINED_BUTTON = addToTab(register("gray_stained_button",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_BUTTON.get(), new Item.Properties())));

    // Light Gray Stained Planks
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_PLANKS = addToTab(register("light_gray_stained_planks",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_STAIRS = addToTab(register("light_gray_stained_stairs",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_SLAB = addToTab(register("light_gray_stained_slab",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_FENCE = addToTab(register("light_gray_stained_fence",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_FENCE_GATE = addToTab(register("light_gray_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_PRESSURE_PLATE = addToTab(register("light_gray_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> LIGHT_GRAY_STAINED_BUTTON = addToTab(register("light_gray_stained_button",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_BUTTON.get(), new Item.Properties())));

    // Cyan Stained Planks
    public static final Supplier<BlockItem> CYAN_STAINED_PLANKS = addToTab(register("cyan_stained_planks",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_STAIRS = addToTab(register("cyan_stained_stairs",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_SLAB = addToTab(register("cyan_stained_slab",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_FENCE = addToTab(register("cyan_stained_fence",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_FENCE_GATE = addToTab(register("cyan_stained_fence_gate",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_PRESSURE_PLATE = addToTab(register("cyan_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> CYAN_STAINED_BUTTON = addToTab(register("cyan_stained_button",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_BUTTON.get(), new Item.Properties())));

    // Purple Stained Planks
    public static final Supplier<BlockItem> PURPLE_STAINED_PLANKS = addToTab(register("purple_stained_planks",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_STAIRS = addToTab(register("purple_stained_stairs",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_SLAB = addToTab(register("purple_stained_slab",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_FENCE = addToTab(register("purple_stained_fence",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_FENCE_GATE = addToTab(register("purple_stained_fence_gate",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_PRESSURE_PLATE = addToTab(register("purple_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> PURPLE_STAINED_BUTTON = addToTab(register("purple_stained_button",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_BUTTON.get(), new Item.Properties())));

    // Blue Stained Planks
    public static final Supplier<BlockItem> BLUE_STAINED_PLANKS = addToTab(register("blue_stained_planks",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_STAIRS = addToTab(register("blue_stained_stairs",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_SLAB = addToTab(register("blue_stained_slab",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_FENCE = addToTab(register("blue_stained_fence",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_FENCE_GATE = addToTab(register("blue_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_PRESSURE_PLATE = addToTab(register("blue_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLUE_STAINED_BUTTON = addToTab(register("blue_stained_button",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_BUTTON.get(), new Item.Properties())));

    // Brown Stained Planks
    public static final Supplier<BlockItem> BROWN_STAINED_PLANKS = addToTab(register("brown_stained_planks",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_STAIRS = addToTab(register("brown_stained_stairs",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_SLAB = addToTab(register("brown_stained_slab",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_FENCE = addToTab(register("brown_stained_fence",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_FENCE_GATE = addToTab(register("brown_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_PRESSURE_PLATE = addToTab(register("brown_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BROWN_STAINED_BUTTON = addToTab(register("brown_stained_button",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_BUTTON.get(), new Item.Properties())));

    // Green Stained Planks
    public static final Supplier<BlockItem> GREEN_STAINED_PLANKS = addToTab(register("green_stained_planks",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_STAIRS = addToTab(register("green_stained_stairs",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_SLAB = addToTab(register("green_stained_slab",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_FENCE = addToTab(register("green_stained_fence",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_FENCE_GATE = addToTab(register("green_stained_fence_gate",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_PRESSURE_PLATE = addToTab(register("green_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> GREEN_STAINED_BUTTON = addToTab(register("green_stained_button",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_BUTTON.get(), new Item.Properties())));

    // Red Stained Planks
    public static final Supplier<BlockItem> RED_STAINED_PLANKS = addToTab(register("red_stained_planks",
            () -> new BlockItem(ModBlocks.RED_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_STAIRS = addToTab(register("red_stained_stairs",
            () -> new BlockItem(ModBlocks.RED_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_SLAB = addToTab(register("red_stained_slab",
            () -> new BlockItem(ModBlocks.RED_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_FENCE = addToTab(register("red_stained_fence",
            () -> new BlockItem(ModBlocks.RED_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_FENCE_GATE = addToTab(register("red_stained_fence_gate",
            () -> new BlockItem(ModBlocks.RED_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_PRESSURE_PLATE = addToTab(register("red_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.RED_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> RED_STAINED_BUTTON = addToTab(register("red_stained_button",
            () -> new BlockItem(ModBlocks.RED_STAINED_BUTTON.get(), new Item.Properties())));

    // Black Stained Planks
    public static final Supplier<BlockItem> BLACK_STAINED_PLANKS = addToTab(register("black_stained_planks",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_PLANKS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_STAIRS = addToTab(register("black_stained_stairs",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_STAIRS.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_SLAB = addToTab(register("black_stained_slab",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_SLAB.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_FENCE = addToTab(register("black_stained_fence",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_FENCE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_FENCE_GATE = addToTab(register("black_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_PRESSURE_PLATE = addToTab(register("black_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final Supplier<BlockItem> BLACK_STAINED_BUTTON = addToTab(register("black_stained_button",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_BUTTON.get(), new Item.Properties())));
}