package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs.addToTab;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "nosiphuscustomblocks");

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


}
