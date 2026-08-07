package com.nosiphus.nosiphuscustomblocks.platform;

import com.nosiphus.nosiphuscustomblocks.NosiphusCustomBlocks;
import com.nosiphus.nosiphuscustomblocks.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> blockSupplier) {
        T registeredBlock = Registry.register(
                BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(NosiphusCustomBlocks.MOD_ID, name),
                blockSupplier.get()
        );
        return () -> registeredBlock;
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> itemSupplier) {
        T registeredItem = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(NosiphusCustomBlocks.MOD_ID, name),
                itemSupplier.get()
        );
        return () -> registeredItem;
    }

}
