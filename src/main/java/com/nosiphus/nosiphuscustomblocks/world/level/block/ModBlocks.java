package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "nosiphuscustomblocks");

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

    //Methods
    private static ButtonBlock woodenButton(BlockSetType blockSetType, FeatureFlag... featureFlags) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(featureFlags);
        }
        return new ButtonBlock(blockbehaviour$properties, blockSetType, 30, true);
    }

}
