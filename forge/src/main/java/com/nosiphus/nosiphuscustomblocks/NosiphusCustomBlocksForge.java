package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.platform.services.ForgeRegistryHelper;
import com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabsForge;
import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NosiphusCustomBlocks.MOD_ID)
public class NosiphusCustomBlocksForge {

    public NosiphusCustomBlocksForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NosiphusCustomBlocks.init();

        ForgeRegistryHelper.BLOCKS.register(eventBus);
        ModCreativeModeTabsForge.CREATIVE_TABS.register(eventBus);
        ForgeRegistryHelper.ITEMS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = NosiphusCustomBlocks.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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