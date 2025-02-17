package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "nosiphuscustomblocks");

    //Beige Stained Planks
    public static final RegistryObject<Block> BEIGE_STAINED_PLANKS = registerOptional("yogmod", "beige_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> BEIGE_STAINED_STAIRS = registerOptional("yogmod", "beige_stained_stairs",
            () -> new StairBlock(BEIGE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> BEIGE_STAINED_SLAB = registerOptional("yogmod", "beige_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> BEIGE_STAINED_FENCE = registerOptional("yogmod", "beige_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> BEIGE_STAINED_FENCE_GATE = registerOptional("yogmod", "beige_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> BEIGE_STAINED_PRESSURE_PLATE = registerOptional("yogmod", "beige_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> BEIGE_STAINED_BUTTON = registerOptional("yogmod", "beige_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //White Stained Planks
    public static final RegistryObject<Block> WHITE_STAINED_PLANKS = BLOCKS.register("white_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WHITE_STAINED_STAIRS = BLOCKS.register("white_stained_stairs",
            () -> new StairBlock(WHITE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> WHITE_STAINED_SLAB = BLOCKS.register("white_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> WHITE_STAINED_FENCE = BLOCKS.register("white_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> WHITE_STAINED_FENCE_GATE = BLOCKS.register("white_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> WHITE_STAINED_PRESSURE_PLATE = BLOCKS.register("white_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> WHITE_STAINED_BUTTON = BLOCKS.register("white_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Orange Stained Planks
    public static final RegistryObject<Block> ORANGE_STAINED_PLANKS = BLOCKS.register("orange_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ORANGE_STAINED_STAIRS = BLOCKS.register("orange_stained_stairs",
            () -> new StairBlock(ORANGE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> ORANGE_STAINED_SLAB = BLOCKS.register("orange_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> ORANGE_STAINED_FENCE = BLOCKS.register("orange_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> ORANGE_STAINED_FENCE_GATE = BLOCKS.register("orange_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> ORANGE_STAINED_PRESSURE_PLATE = BLOCKS.register("orange_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> ORANGE_STAINED_BUTTON = BLOCKS.register("orange_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Magenta Stained Planks
    public static final RegistryObject<Block> MAGENTA_STAINED_PLANKS = BLOCKS.register("magenta_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> MAGENTA_STAINED_STAIRS = BLOCKS.register("magenta_stained_stairs",
            () -> new StairBlock(MAGENTA_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> MAGENTA_STAINED_SLAB = BLOCKS.register("magenta_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> MAGENTA_STAINED_FENCE = BLOCKS.register("magenta_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> MAGENTA_STAINED_FENCE_GATE = BLOCKS.register("magenta_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> MAGENTA_STAINED_PRESSURE_PLATE = BLOCKS.register("magenta_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> MAGENTA_STAINED_BUTTON = BLOCKS.register("magenta_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Light Blue Stained Planks
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_PLANKS = BLOCKS.register("light_blue_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_STAIRS = BLOCKS.register("light_blue_stained_stairs",
            () -> new StairBlock(LIGHT_BLUE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_SLAB = BLOCKS.register("light_blue_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_FENCE = BLOCKS.register("light_blue_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_FENCE_GATE = BLOCKS.register("light_blue_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_PRESSURE_PLATE = BLOCKS.register("light_blue_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_BUTTON = BLOCKS.register("light_blue_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Yellow Stained Planks
    public static final RegistryObject<Block> YELLOW_STAINED_PLANKS = BLOCKS.register("yellow_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> YELLOW_STAINED_STAIRS = BLOCKS.register("yellow_stained_stairs",
            () -> new StairBlock(YELLOW_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> YELLOW_STAINED_SLAB = BLOCKS.register("yellow_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> YELLOW_STAINED_FENCE = BLOCKS.register("yellow_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> YELLOW_STAINED_FENCE_GATE = BLOCKS.register("yellow_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> YELLOW_STAINED_PRESSURE_PLATE = BLOCKS.register("yellow_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> YELLOW_STAINED_BUTTON = BLOCKS.register("yellow_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Lime Stained Planks
    public static final RegistryObject<Block> LIME_STAINED_PLANKS = BLOCKS.register("lime_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> LIME_STAINED_STAIRS = BLOCKS.register("lime_stained_stairs",
            () -> new StairBlock(LIME_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> LIME_STAINED_SLAB = BLOCKS.register("lime_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> LIME_STAINED_FENCE = BLOCKS.register("lime_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> LIME_STAINED_FENCE_GATE = BLOCKS.register("lime_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> LIME_STAINED_PRESSURE_PLATE = BLOCKS.register("lime_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> LIME_STAINED_BUTTON = BLOCKS.register("lime_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Pink Stained Planks
    public static final RegistryObject<Block> PINK_STAINED_PLANKS = BLOCKS.register("pink_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> PINK_STAINED_STAIRS = BLOCKS.register("pink_stained_stairs",
            () -> new StairBlock(PINK_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> PINK_STAINED_SLAB = BLOCKS.register("pink_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> PINK_STAINED_FENCE = BLOCKS.register("pink_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> PINK_STAINED_FENCE_GATE = BLOCKS.register("pink_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> PINK_STAINED_PRESSURE_PLATE = BLOCKS.register("pink_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> PINK_STAINED_BUTTON = BLOCKS.register("pink_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Gray Stained Planks
    public static final RegistryObject<Block> GRAY_STAINED_PLANKS = BLOCKS.register("gray_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> GRAY_STAINED_STAIRS = BLOCKS.register("gray_stained_stairs",
            () -> new StairBlock(GRAY_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> GRAY_STAINED_SLAB = BLOCKS.register("gray_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> GRAY_STAINED_FENCE = BLOCKS.register("gray_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> GRAY_STAINED_FENCE_GATE = BLOCKS.register("gray_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> GRAY_STAINED_PRESSURE_PLATE = BLOCKS.register("gray_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> GRAY_STAINED_BUTTON = BLOCKS.register("gray_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Light Gray Stained Planks
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_PLANKS = BLOCKS.register("light_gray_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_STAIRS = BLOCKS.register("light_gray_stained_stairs",
            () -> new StairBlock(LIGHT_GRAY_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_SLAB = BLOCKS.register("light_gray_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_FENCE = BLOCKS.register("light_gray_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_FENCE_GATE = BLOCKS.register("light_gray_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_PRESSURE_PLATE = BLOCKS.register("light_gray_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_BUTTON = BLOCKS.register("light_gray_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Cyan Stained Planks
    public static final RegistryObject<Block> CYAN_STAINED_PLANKS = BLOCKS.register("cyan_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> CYAN_STAINED_STAIRS = BLOCKS.register("cyan_stained_stairs",
            () -> new StairBlock(CYAN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> CYAN_STAINED_SLAB = BLOCKS.register("cyan_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> CYAN_STAINED_FENCE = BLOCKS.register("cyan_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> CYAN_STAINED_FENCE_GATE = BLOCKS.register("cyan_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> CYAN_STAINED_PRESSURE_PLATE = BLOCKS.register("cyan_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> CYAN_STAINED_BUTTON = BLOCKS.register("cyan_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Purple Stained Planks
    public static final RegistryObject<Block> PURPLE_STAINED_PLANKS = BLOCKS.register("purple_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> PURPLE_STAINED_STAIRS = BLOCKS.register("purple_stained_stairs",
            () -> new StairBlock(PURPLE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> PURPLE_STAINED_SLAB = BLOCKS.register("purple_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> PURPLE_STAINED_FENCE = BLOCKS.register("purple_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> PURPLE_STAINED_FENCE_GATE = BLOCKS.register("purple_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> PURPLE_STAINED_PRESSURE_PLATE = BLOCKS.register("purple_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> PURPLE_STAINED_BUTTON = BLOCKS.register("purple_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Blue Stained Planks
    public static final RegistryObject<Block> BLUE_STAINED_PLANKS = BLOCKS.register("blue_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> BLUE_STAINED_STAIRS = BLOCKS.register("blue_stained_stairs",
            () -> new StairBlock(BLUE_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> BLUE_STAINED_SLAB = BLOCKS.register("blue_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> BLUE_STAINED_FENCE = BLOCKS.register("blue_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> BLUE_STAINED_FENCE_GATE = BLOCKS.register("blue_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> BLUE_STAINED_PRESSURE_PLATE = BLOCKS.register("blue_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> BLUE_STAINED_BUTTON = BLOCKS.register("blue_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Brown Stained Planks
    public static final RegistryObject<Block> BROWN_STAINED_PLANKS = BLOCKS.register("brown_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> BROWN_STAINED_STAIRS = BLOCKS.register("brown_stained_stairs",
            () -> new StairBlock(BROWN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> BROWN_STAINED_SLAB = BLOCKS.register("brown_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> BROWN_STAINED_FENCE = BLOCKS.register("brown_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> BROWN_STAINED_FENCE_GATE = BLOCKS.register("brown_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> BROWN_STAINED_PRESSURE_PLATE = BLOCKS.register("brown_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> BROWN_STAINED_BUTTON = BLOCKS.register("brown_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Green Stained Planks
    public static final RegistryObject<Block> GREEN_STAINED_PLANKS = BLOCKS.register("green_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> GREEN_STAINED_STAIRS = BLOCKS.register("green_stained_stairs",
            () -> new StairBlock(GREEN_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> GREEN_STAINED_SLAB = BLOCKS.register("green_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> GREEN_STAINED_FENCE = BLOCKS.register("green_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> GREEN_STAINED_FENCE_GATE = BLOCKS.register("green_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> GREEN_STAINED_PRESSURE_PLATE = BLOCKS.register("green_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> GREEN_STAINED_BUTTON = BLOCKS.register("green_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Red Stained Planks
    public static final RegistryObject<Block> RED_STAINED_PLANKS = BLOCKS.register("red_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> RED_STAINED_STAIRS = BLOCKS.register("red_stained_stairs",
            () -> new StairBlock(RED_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> RED_STAINED_SLAB = BLOCKS.register("red_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> RED_STAINED_FENCE = BLOCKS.register("red_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> RED_STAINED_FENCE_GATE = BLOCKS.register("red_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> RED_STAINED_PRESSURE_PLATE = BLOCKS.register("red_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> RED_STAINED_BUTTON = BLOCKS.register("red_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Black Stained Planks
    public static final RegistryObject<Block> BLACK_STAINED_PLANKS = BLOCKS.register("black_stained_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> BLACK_STAINED_STAIRS = BLOCKS.register("black_stained_stairs",
            () -> new StairBlock(BLACK_STAINED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> BLACK_STAINED_SLAB = BLOCKS.register("black_stained_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> BLACK_STAINED_FENCE = BLOCKS.register("black_stained_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> BLACK_STAINED_FENCE_GATE = BLOCKS.register("black_stained_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> BLACK_STAINED_PRESSURE_PLATE = BLOCKS.register("black_stained_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> BLACK_STAINED_BUTTON = BLOCKS.register("black_stained_button",
            () -> woodenButton(BlockSetType.OAK));

    //Methods
    @Nullable
    private static RegistryObject<Block> registerOptional(String modID, String name, Supplier<Block> block) {
        if(ModList.get().isLoaded(modID)) {
            return BLOCKS.register(name, block);
        }
        return null;
    }

    private static ButtonBlock woodenButton(BlockSetType blockSetType, FeatureFlag... featureFlags) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(featureFlags);
        }
        return new ButtonBlock(blockbehaviour$properties, blockSetType, 30, true);
    }

}
