package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class NosiphusCustomBlocksFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(
                NosiphusCustomBlocks::getDyedItemColor,
                ModItems.BOW_TIE.get(),
                ModItems.FEZ.get()
        );
    }

}
