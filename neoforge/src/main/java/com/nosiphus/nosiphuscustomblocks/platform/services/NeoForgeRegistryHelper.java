package com.nosiphus.nosiphuscustomblocks.platform.services;

import com.nosiphus.nosiphuscustomblocks.NosiphusCustomBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NosiphusCustomBlocks.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NosiphusCustomBlocks.MOD_ID);

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> deferredBlock = BLOCKS.register(name, blockSupplier);
        return deferredBlock;
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> itemSupplier) {
        DeferredItem<T> deferredItem = ITEMS.register(name, itemSupplier);
        return deferredItem;
    }

}
