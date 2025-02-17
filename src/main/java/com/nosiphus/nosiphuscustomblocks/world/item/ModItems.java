package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs.addToTab;
import static com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs.addToTabOptional;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "nosiphuscustomblocks");

    //Bow Ties
    public static final RegistryObject<ArmorItem> BOW_TIE = addToTab(ITEMS.register("bow_tie",
            () -> new DyeableArmorItem(ModArmorMaterials.BOW_TIE, ArmorItem.Type.CHESTPLATE, new Item.Properties())));

    //Foods
    public static final RegistryObject<Item> CUSTARD = addToTab(ITEMS.register("custard",
            () -> new BowlFoodItem(new Item.Properties().stacksTo(1)
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(0.6f)
                            .alwaysEat()
                            .build()))));
    public static final RegistryObject<Item> FISH_FINGER = addToTab(ITEMS.register("fish_finger",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationMod(0.6f)
                            .alwaysEat()
                            .build()))));

    //Sonic Screwdrivers
    public static final RegistryObject<Item> BLUE_SONIC_SCREWDRIVER = addToTab(ITEMS.register("blue_sonic_screwdriver",
            () -> new SwordItem(ModTiers.BLUE_SONIC_SCREWDRIVER, 3, -2.4F, new Item.Properties())));
    public static final RegistryObject<Item> GREEN_SONIC_SCREWDRIVER = addToTab(ITEMS.register("green_sonic_screwdriver",
            () -> new SwordItem(ModTiers.GREEN_SONIC_SCREWDRIVER, 3, -2.4F, new Item.Properties())));

    //Beige Stained Planks
    public static final RegistryObject<BlockItem> BEIGE_STAINED_PLANKS = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_planks",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_STAIRS = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_stairs",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_SLAB = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_slab",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_FENCE = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_fence",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_FENCE_GATE = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_PRESSURE_PLATE = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BEIGE_STAINED_BUTTON = addToTabOptional("yogmod", registerOptionalBlockItem("yogmod", "beige_stained_button",
            () -> new BlockItem(ModBlocks.BEIGE_STAINED_BUTTON.get(), new Item.Properties())));

    //White Stained Planks
    public static final RegistryObject<BlockItem> WHITE_STAINED_PLANKS = addToTab(ITEMS.register("white_stained_planks",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_STAIRS = addToTab(ITEMS.register("white_stained_stairs",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_SLAB = addToTab(ITEMS.register("white_stained_slab",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_FENCE = addToTab(ITEMS.register("white_stained_fence",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_FENCE_GATE = addToTab(ITEMS.register("white_stained_fence_gate",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("white_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> WHITE_STAINED_BUTTON = addToTab(ITEMS.register("white_stained_button",
            () -> new BlockItem(ModBlocks.WHITE_STAINED_BUTTON.get(), new Item.Properties())));

    //Orange Stained Planks
    public static final RegistryObject<BlockItem> ORANGE_STAINED_PLANKS = addToTab(ITEMS.register("orange_stained_planks",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_STAIRS = addToTab(ITEMS.register("orange_stained_stairs",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_SLAB = addToTab(ITEMS.register("orange_stained_slab",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_FENCE = addToTab(ITEMS.register("orange_stained_fence",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_FENCE_GATE = addToTab(ITEMS.register("orange_stained_fence_gate",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("orange_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> ORANGE_STAINED_BUTTON = addToTab(ITEMS.register("orange_stained_button",
            () -> new BlockItem(ModBlocks.ORANGE_STAINED_BUTTON.get(), new Item.Properties())));

    //Magenta Stained Planks
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_PLANKS = addToTab(ITEMS.register("magenta_stained_planks",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_STAIRS = addToTab(ITEMS.register("magenta_stained_stairs",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_SLAB = addToTab(ITEMS.register("magenta_stained_slab",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_FENCE = addToTab(ITEMS.register("magenta_stained_fence",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_FENCE_GATE = addToTab(ITEMS.register("magenta_stained_fence_gate",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("magenta_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> MAGENTA_STAINED_BUTTON = addToTab(ITEMS.register("magenta_stained_button",
            () -> new BlockItem(ModBlocks.MAGENTA_STAINED_BUTTON.get(), new Item.Properties())));

    //Light Blue Stained Planks
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_PLANKS = addToTab(ITEMS.register("light_blue_stained_planks",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_STAIRS = addToTab(ITEMS.register("light_blue_stained_stairs",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_SLAB = addToTab(ITEMS.register("light_blue_stained_slab",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_FENCE = addToTab(ITEMS.register("light_blue_stained_fence",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_FENCE_GATE = addToTab(ITEMS.register("light_blue_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("light_blue_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_BUTTON = addToTab(ITEMS.register("light_blue_stained_button",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_STAINED_BUTTON.get(), new Item.Properties())));

    //Yellow Stained Planks
    public static final RegistryObject<BlockItem> YELLOW_STAINED_PLANKS = addToTab(ITEMS.register("yellow_stained_planks",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_STAIRS = addToTab(ITEMS.register("yellow_stained_stairs",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_SLAB = addToTab(ITEMS.register("yellow_stained_slab",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_FENCE = addToTab(ITEMS.register("yellow_stained_fence",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_FENCE_GATE = addToTab(ITEMS.register("yellow_stained_fence_gate",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("yellow_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> YELLOW_STAINED_BUTTON = addToTab(ITEMS.register("yellow_stained_button",
            () -> new BlockItem(ModBlocks.YELLOW_STAINED_BUTTON.get(), new Item.Properties())));

    //Lime Stained Planks
    public static final RegistryObject<BlockItem> LIME_STAINED_PLANKS = addToTab(ITEMS.register("lime_stained_planks",
            () -> new BlockItem(ModBlocks.LIME_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_STAIRS = addToTab(ITEMS.register("lime_stained_stairs",
            () -> new BlockItem(ModBlocks.LIME_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_SLAB = addToTab(ITEMS.register("lime_stained_slab",
            () -> new BlockItem(ModBlocks.LIME_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_FENCE = addToTab(ITEMS.register("lime_stained_fence",
            () -> new BlockItem(ModBlocks.LIME_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_FENCE_GATE = addToTab(ITEMS.register("lime_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIME_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("lime_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIME_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIME_STAINED_BUTTON = addToTab(ITEMS.register("lime_stained_button",
            () -> new BlockItem(ModBlocks.LIME_STAINED_BUTTON.get(), new Item.Properties())));

    //Pink Stained Planks
    public static final RegistryObject<BlockItem> PINK_STAINED_PLANKS = addToTab(ITEMS.register("pink_stained_planks",
            () -> new BlockItem(ModBlocks.PINK_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_STAIRS = addToTab(ITEMS.register("pink_stained_stairs",
            () -> new BlockItem(ModBlocks.PINK_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_SLAB = addToTab(ITEMS.register("pink_stained_slab",
            () -> new BlockItem(ModBlocks.PINK_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_FENCE = addToTab(ITEMS.register("pink_stained_fence",
            () -> new BlockItem(ModBlocks.PINK_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_FENCE_GATE = addToTab(ITEMS.register("pink_stained_fence_gate",
            () -> new BlockItem(ModBlocks.PINK_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("pink_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.PINK_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PINK_STAINED_BUTTON = addToTab(ITEMS.register("pink_stained_button",
            () -> new BlockItem(ModBlocks.PINK_STAINED_BUTTON.get(), new Item.Properties())));

    //Gray Stained Planks
    public static final RegistryObject<BlockItem> GRAY_STAINED_PLANKS = addToTab(ITEMS.register("gray_stained_planks",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_STAIRS = addToTab(ITEMS.register("gray_stained_stairs",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_SLAB = addToTab(ITEMS.register("gray_stained_slab",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_FENCE = addToTab(ITEMS.register("gray_stained_fence",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_FENCE_GATE = addToTab(ITEMS.register("gray_stained_fence_gate",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("gray_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GRAY_STAINED_BUTTON = addToTab(ITEMS.register("gray_stained_button",
            () -> new BlockItem(ModBlocks.GRAY_STAINED_BUTTON.get(), new Item.Properties())));

    //Light Gray Stained Planks
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_PLANKS = addToTab(ITEMS.register("light_gray_stained_planks",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_STAIRS = addToTab(ITEMS.register("light_gray_stained_stairs",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_SLAB = addToTab(ITEMS.register("light_gray_stained_slab",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_FENCE = addToTab(ITEMS.register("light_gray_stained_fence",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_FENCE_GATE = addToTab(ITEMS.register("light_gray_stained_fence_gate",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("light_gray_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_BUTTON = addToTab(ITEMS.register("light_gray_stained_button",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_STAINED_BUTTON.get(), new Item.Properties())));

    //Cyan Stained Planks
    public static final RegistryObject<BlockItem> CYAN_STAINED_PLANKS = addToTab(ITEMS.register("cyan_stained_planks",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_STAIRS = addToTab(ITEMS.register("cyan_stained_stairs",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_SLAB = addToTab(ITEMS.register("cyan_stained_slab",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_FENCE = addToTab(ITEMS.register("cyan_stained_fence",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_FENCE_GATE = addToTab(ITEMS.register("cyan_stained_fence_gate",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("cyan_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> CYAN_STAINED_BUTTON = addToTab(ITEMS.register("cyan_stained_button",
            () -> new BlockItem(ModBlocks.CYAN_STAINED_BUTTON.get(), new Item.Properties())));

    //Purple Stained Planks
    public static final RegistryObject<BlockItem> PURPLE_STAINED_PLANKS = addToTab(ITEMS.register("purple_stained_planks",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_STAIRS = addToTab(ITEMS.register("purple_stained_stairs",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_SLAB = addToTab(ITEMS.register("purple_stained_slab",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_FENCE = addToTab(ITEMS.register("purple_stained_fence",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_FENCE_GATE = addToTab(ITEMS.register("purple_stained_fence_gate",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("purple_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> PURPLE_STAINED_BUTTON = addToTab(ITEMS.register("purple_stained_button",
            () -> new BlockItem(ModBlocks.PURPLE_STAINED_BUTTON.get(), new Item.Properties())));

    //Blue Stained Planks
    public static final RegistryObject<BlockItem> BLUE_STAINED_PLANKS = addToTab(ITEMS.register("blue_stained_planks",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_STAIRS = addToTab(ITEMS.register("blue_stained_stairs",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_SLAB = addToTab(ITEMS.register("blue_stained_slab",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_FENCE = addToTab(ITEMS.register("blue_stained_fence",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_FENCE_GATE = addToTab(ITEMS.register("blue_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("blue_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLUE_STAINED_BUTTON = addToTab(ITEMS.register("blue_stained_button",
            () -> new BlockItem(ModBlocks.BLUE_STAINED_BUTTON.get(), new Item.Properties())));

    //Brown Stained Planks
    public static final RegistryObject<BlockItem> BROWN_STAINED_PLANKS = addToTab(ITEMS.register("brown_stained_planks",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_STAIRS = addToTab(ITEMS.register("brown_stained_stairs",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_SLAB = addToTab(ITEMS.register("brown_stained_slab",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_FENCE = addToTab(ITEMS.register("brown_stained_fence",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_FENCE_GATE = addToTab(ITEMS.register("brown_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("brown_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BROWN_STAINED_BUTTON = addToTab(ITEMS.register("brown_stained_button",
            () -> new BlockItem(ModBlocks.BROWN_STAINED_BUTTON.get(), new Item.Properties())));

    //Green Stained Planks
    public static final RegistryObject<BlockItem> GREEN_STAINED_PLANKS = addToTab(ITEMS.register("green_stained_planks",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_STAIRS = addToTab(ITEMS.register("green_stained_stairs",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_SLAB = addToTab(ITEMS.register("green_stained_slab",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_FENCE = addToTab(ITEMS.register("green_stained_fence",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_FENCE_GATE = addToTab(ITEMS.register("green_stained_fence_gate",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("green_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> GREEN_STAINED_BUTTON = addToTab(ITEMS.register("green_stained_button",
            () -> new BlockItem(ModBlocks.GREEN_STAINED_BUTTON.get(), new Item.Properties())));

    //Red Stained Planks
    public static final RegistryObject<BlockItem> RED_STAINED_PLANKS = addToTab(ITEMS.register("red_stained_planks",
            () -> new BlockItem(ModBlocks.RED_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_STAIRS = addToTab(ITEMS.register("red_stained_stairs",
            () -> new BlockItem(ModBlocks.RED_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_SLAB = addToTab(ITEMS.register("red_stained_slab",
            () -> new BlockItem(ModBlocks.RED_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_FENCE = addToTab(ITEMS.register("red_stained_fence",
            () -> new BlockItem(ModBlocks.RED_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_FENCE_GATE = addToTab(ITEMS.register("red_stained_fence_gate",
            () -> new BlockItem(ModBlocks.RED_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("red_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.RED_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> RED_STAINED_BUTTON = addToTab(ITEMS.register("red_stained_button",
            () -> new BlockItem(ModBlocks.RED_STAINED_BUTTON.get(), new Item.Properties())));

    //Black Stained Planks
    public static final RegistryObject<BlockItem> BLACK_STAINED_PLANKS = addToTab(ITEMS.register("black_stained_planks",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_PLANKS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_STAIRS = addToTab(ITEMS.register("black_stained_stairs",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_STAIRS.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_SLAB = addToTab(ITEMS.register("black_stained_slab",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_SLAB.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_FENCE = addToTab(ITEMS.register("black_stained_fence",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_FENCE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_FENCE_GATE = addToTab(ITEMS.register("black_stained_fence_gate",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_FENCE_GATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_PRESSURE_PLATE = addToTab(ITEMS.register("black_stained_pressure_plate",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_PRESSURE_PLATE.get(), new Item.Properties())));
    public static final RegistryObject<BlockItem> BLACK_STAINED_BUTTON = addToTab(ITEMS.register("black_stained_button",
            () -> new BlockItem(ModBlocks.BLACK_STAINED_BUTTON.get(), new Item.Properties())));

    //Methods
    @Nullable
    private static RegistryObject<BlockItem> registerOptionalBlockItem(String modID, String name, Supplier<BlockItem> blockItem) {
        if(ModList.get().isLoaded(modID)) {
            return ITEMS.register(name, blockItem);
        }
        return null;
    }

    @Nullable
    private static RegistryObject<Item> registerOptionalItem(String modID, String name, Supplier<Item> item) {
        if(ModList.get().isLoaded(modID)) {
            return ITEMS.register(name, item);
        }
        return null;
    }

}
