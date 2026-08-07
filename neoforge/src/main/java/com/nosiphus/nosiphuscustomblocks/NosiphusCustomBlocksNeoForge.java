package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.platform.services.NeoForgeRegistryHelper;
import com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabsNeoForge;
import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod(NosiphusCustomBlocks.MOD_ID)
public class NosiphusCustomBlocksNeoForge {

    public NosiphusCustomBlocksNeoForge(IEventBus eventBus, ModContainer container) {
        NosiphusCustomBlocks.init();

        NeoForgeRegistryHelper.BLOCKS.register(eventBus);
        ModCreativeModeTabsNeoForge.CREATIVE_TABS.register(eventBus);
        NeoForgeRegistryHelper.ITEMS.register(eventBus);
    }

    @EventBusSubscriber(modid = NosiphusCustomBlocks.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register(
                    NosiphusCustomBlocks::getDyedItemColor,
                    ModItems.BOW_TIE.get(),
                    ModItems.FEZ.get()
            );
        }
    }
}