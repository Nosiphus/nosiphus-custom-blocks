package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.NosiphusCustomBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabsFabric {

    public static CreativeModeTab NOSIPHUSCUSTOMBLOCKS;

    public static void register() {
        NOSIPHUSCUSTOMBLOCKS = Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(NosiphusCustomBlocks.MOD_ID, "nosiphuscustomblocks"),
                ModCreativeModeTabs.createTabBuilder().build()
        );
    }
}