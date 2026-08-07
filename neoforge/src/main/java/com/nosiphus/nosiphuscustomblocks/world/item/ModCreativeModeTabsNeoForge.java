package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.NosiphusCustomBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabsNeoForge {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NosiphusCustomBlocks.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NOSIPHUSCUSTOMBLOCKS =
            CREATIVE_TABS.register("nosiphuscustomblocks", () -> ModCreativeModeTabs.createTabBuilder().build());
}