package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.NosiphusCustomBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabsForge {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NosiphusCustomBlocks.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NOSIPHUSCUSTOMBLOCKS =
            CREATIVE_TABS.register("nosiphuscustomblocks", () -> ModCreativeModeTabs.createTabBuilder().build());
}