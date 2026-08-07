package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabsFabric;
import net.fabricmc.api.ModInitializer;

public class NosiphusCustomBlocksFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        NosiphusCustomBlocks.init();
        ModCreativeModeTabsFabric.register();

    }
}
