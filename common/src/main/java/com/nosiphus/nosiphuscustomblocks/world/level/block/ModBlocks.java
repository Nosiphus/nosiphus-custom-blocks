package com.nosiphus.nosiphuscustomblocks.world.level.block;

import com.nosiphus.nosiphuscustomblocks.platform.Services;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class ModBlocks {

    public static void init() {

    }

    private static <T extends Block> Supplier<T> register(String name, Supplier<T> blockSupplier) {
        return Services.REGISTRY.registerBlock(name, blockSupplier);
    }

    private static <T extends Block> Supplier<T> registerOptional(String modID, String name, Supplier<T> blockSupplier) {
        if (Services.PLATFORM.isModLoaded(modID)) {
            return register(name, blockSupplier);
        }
        return null;
    }

    private static Block woodenButton(BlockSetType type) {
        return new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), type, 30, true);
    }

    // TARDIS
    public static final Supplier<Block> GOLDEN_ROUNDEL_BLOCK = register("golden_roundel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final Supplier<Block> LABELED_TARDIS_DOOR = register("labeled_tardis_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final Supplier<Block> TARDIS_DOOR = register("tardis_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final Supplier<Block> TARDIS_SLAB = register("tardis_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));

    // Ceiling Lights
    public static final Supplier<Block> FLUORESCENT_PANEL_CEILING_LIGHT = registerOptional("yogmod", "fluorescent_panel_ceiling_light",
            () -> new CeilingLightBlock(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));
    public static final Supplier<Block> GLOWSTONE_CEILING_LIGHT = register("glowstone_ceiling_light",
            () -> new CeilingLightBlock(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));
    public static final Supplier<Block> SEA_LANTERN_CEILING_LIGHT = register("sea_lantern_ceiling_light",
            () -> new CeilingLightBlock(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN)));

    // Beige Stained Planks
    public static final Supplier<Block> BEIGE_STAINED_PLANKS = registerOptional("yogmod", "beige_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BEIGE_STAINED_STAIRS = registerOptional("yogmod", "beige_stained_stairs",
            () -> new StairBlock(BEIGE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> BEIGE_STAINED_SLAB = registerOptional("yogmod", "beige_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> BEIGE_STAINED_FENCE = registerOptional("yogmod", "beige_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> BEIGE_STAINED_FENCE_GATE = registerOptional("yogmod", "beige_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> BEIGE_STAINED_PRESSURE_PLATE = registerOptional("yogmod", "beige_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> BEIGE_STAINED_BUTTON = registerOptional("yogmod", "beige_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // White Stained Planks
    public static final Supplier<Block> WHITE_STAINED_PLANKS = register("white_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> WHITE_STAINED_STAIRS = register("white_stained_stairs",
            () -> new StairBlock(WHITE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> WHITE_STAINED_SLAB = register("white_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> WHITE_STAINED_FENCE = register("white_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> WHITE_STAINED_FENCE_GATE = register("white_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> WHITE_STAINED_PRESSURE_PLATE = register("white_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> WHITE_STAINED_BUTTON = register("white_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Orange Stained Planks
    public static final Supplier<Block> ORANGE_STAINED_PLANKS = register("orange_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> ORANGE_STAINED_STAIRS = register("orange_stained_stairs",
            () -> new StairBlock(ORANGE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> ORANGE_STAINED_SLAB = register("orange_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> ORANGE_STAINED_FENCE = register("orange_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> ORANGE_STAINED_FENCE_GATE = register("orange_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> ORANGE_STAINED_PRESSURE_PLATE = register("orange_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> ORANGE_STAINED_BUTTON = register("orange_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Magenta Stained Planks
    public static final Supplier<Block> MAGENTA_STAINED_PLANKS = register("magenta_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> MAGENTA_STAINED_STAIRS = register("magenta_stained_stairs",
            () -> new StairBlock(MAGENTA_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> MAGENTA_STAINED_SLAB = register("magenta_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> MAGENTA_STAINED_FENCE = register("magenta_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> MAGENTA_STAINED_FENCE_GATE = register("magenta_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> MAGENTA_STAINED_PRESSURE_PLATE = register("magenta_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> MAGENTA_STAINED_BUTTON = register("magenta_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Light Blue Stained Planks
    public static final Supplier<Block> LIGHT_BLUE_STAINED_PLANKS = register("light_blue_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_STAIRS = register("light_blue_stained_stairs",
            () -> new StairBlock(LIGHT_BLUE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_SLAB = register("light_blue_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_FENCE = register("light_blue_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_FENCE_GATE = register("light_blue_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_PRESSURE_PLATE = register("light_blue_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> LIGHT_BLUE_STAINED_BUTTON = register("light_blue_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Yellow Stained Planks
    public static final Supplier<Block> YELLOW_STAINED_PLANKS = register("yellow_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> YELLOW_STAINED_STAIRS = register("yellow_stained_stairs",
            () -> new StairBlock(YELLOW_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> YELLOW_STAINED_SLAB = register("yellow_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> YELLOW_STAINED_FENCE = register("yellow_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> YELLOW_STAINED_FENCE_GATE = register("yellow_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> YELLOW_STAINED_PRESSURE_PLATE = register("yellow_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> YELLOW_STAINED_BUTTON = register("yellow_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Lime Stained Planks
    public static final Supplier<Block> LIME_STAINED_PLANKS = register("lime_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> LIME_STAINED_STAIRS = register("lime_stained_stairs",
            () -> new StairBlock(LIME_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> LIME_STAINED_SLAB = register("lime_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> LIME_STAINED_FENCE = register("lime_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> LIME_STAINED_FENCE_GATE = register("lime_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> LIME_STAINED_PRESSURE_PLATE = register("lime_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> LIME_STAINED_BUTTON = register("lime_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Pink Stained Planks
    public static final Supplier<Block> PINK_STAINED_PLANKS = register("pink_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> PINK_STAINED_STAIRS = register("pink_stained_stairs",
            () -> new StairBlock(PINK_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> PINK_STAINED_SLAB = register("pink_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> PINK_STAINED_FENCE = register("pink_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> PINK_STAINED_FENCE_GATE = register("pink_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> PINK_STAINED_PRESSURE_PLATE = register("pink_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> PINK_STAINED_BUTTON = register("pink_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Gray Stained Planks
    public static final Supplier<Block> GRAY_STAINED_PLANKS = register("gray_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> GRAY_STAINED_STAIRS = register("gray_stained_stairs",
            () -> new StairBlock(GRAY_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> GRAY_STAINED_SLAB = register("gray_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> GRAY_STAINED_FENCE = register("gray_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> GRAY_STAINED_FENCE_GATE = register("gray_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> GRAY_STAINED_PRESSURE_PLATE = register("gray_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> GRAY_STAINED_BUTTON = register("gray_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Light Gray Stained Planks
    public static final Supplier<Block> LIGHT_GRAY_STAINED_PLANKS = register("light_gray_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_STAIRS = register("light_gray_stained_stairs",
            () -> new StairBlock(LIGHT_GRAY_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_SLAB = register("light_gray_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_FENCE = register("light_gray_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_FENCE_GATE = register("light_gray_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_PRESSURE_PLATE = register("light_gray_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> LIGHT_GRAY_STAINED_BUTTON = register("light_gray_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Cyan Stained Planks
    public static final Supplier<Block> CYAN_STAINED_PLANKS = register("cyan_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> CYAN_STAINED_STAIRS = register("cyan_stained_stairs",
            () -> new StairBlock(CYAN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> CYAN_STAINED_SLAB = register("cyan_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> CYAN_STAINED_FENCE = register("cyan_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> CYAN_STAINED_FENCE_GATE = register("cyan_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> CYAN_STAINED_PRESSURE_PLATE = register("cyan_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> CYAN_STAINED_BUTTON = register("cyan_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Purple Stained Planks
    public static final Supplier<Block> PURPLE_STAINED_PLANKS = register("purple_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> PURPLE_STAINED_STAIRS = register("purple_stained_stairs",
            () -> new StairBlock(PURPLE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> PURPLE_STAINED_SLAB = register("purple_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> PURPLE_STAINED_FENCE = register("purple_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> PURPLE_STAINED_FENCE_GATE = register("purple_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> PURPLE_STAINED_PRESSURE_PLATE = register("purple_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> PURPLE_STAINED_BUTTON = register("purple_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Blue Stained Planks
    public static final Supplier<Block> BLUE_STAINED_PLANKS = register("blue_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BLUE_STAINED_STAIRS = register("blue_stained_stairs",
            () -> new StairBlock(BLUE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> BLUE_STAINED_SLAB = register("blue_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> BLUE_STAINED_FENCE = register("blue_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> BLUE_STAINED_FENCE_GATE = register("blue_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> BLUE_STAINED_PRESSURE_PLATE = register("blue_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> BLUE_STAINED_BUTTON = register("blue_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Brown Stained Planks
    public static final Supplier<Block> BROWN_STAINED_PLANKS = register("brown_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BROWN_STAINED_STAIRS = register("brown_stained_stairs",
            () -> new StairBlock(BROWN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> BROWN_STAINED_SLAB = register("brown_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> BROWN_STAINED_FENCE = register("brown_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> BROWN_STAINED_FENCE_GATE = register("brown_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> BROWN_STAINED_PRESSURE_PLATE = register("brown_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> BROWN_STAINED_BUTTON = register("brown_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Green Stained Planks
    public static final Supplier<Block> GREEN_STAINED_PLANKS = register("green_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> GREEN_STAINED_STAIRS = register("green_stained_stairs",
            () -> new StairBlock(GREEN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> GREEN_STAINED_SLAB = register("green_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> GREEN_STAINED_FENCE = register("green_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> GREEN_STAINED_FENCE_GATE = register("green_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> GREEN_STAINED_PRESSURE_PLATE = register("green_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> GREEN_STAINED_BUTTON = register("green_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Red Stained Planks
    public static final Supplier<Block> RED_STAINED_PLANKS = register("red_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> RED_STAINED_STAIRS = register("red_stained_stairs",
            () -> new StairBlock(RED_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> RED_STAINED_SLAB = register("red_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> RED_STAINED_FENCE = register("red_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> RED_STAINED_FENCE_GATE = register("red_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> RED_STAINED_PRESSURE_PLATE = register("red_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> RED_STAINED_BUTTON = register("red_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    // Black Stained Planks
    public static final Supplier<Block> BLACK_STAINED_PLANKS = register("black_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BLACK_STAINED_STAIRS = register("black_stained_stairs",
            () -> new StairBlock(BLACK_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final Supplier<Block> BLACK_STAINED_SLAB = register("black_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final Supplier<Block> BLACK_STAINED_FENCE = register("black_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<Block> BLACK_STAINED_FENCE_GATE = register("black_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final Supplier<Block> BLACK_STAINED_PRESSURE_PLATE = register("black_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final Supplier<Block> BLACK_STAINED_BUTTON = register("black_stained_button",
            () -> woodenButton(BlockSetType.OAK));
}