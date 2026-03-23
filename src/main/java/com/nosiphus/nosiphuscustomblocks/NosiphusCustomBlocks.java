package com.nosiphus.nosiphuscustomblocks;

import com.mojang.logging.LogUtils;
import com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs;
import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.slf4j.Logger;

@Mod("nosiphuscustomblocks")
public class NosiphusCustomBlocks {

    private static final Logger LOGGER = LogUtils.getLogger();
    public NosiphusCustomBlocks(IEventBus eventBus, ModContainer container) {

        ModBlocks.BLOCKS.register(eventBus);
        ModCreativeModeTabs.CREATIVE_TABS.register(eventBus);
        ModItems.ITEMS.register(eventBus);

    }

    @EventBusSubscriber(modid = "nosiphuscustomblocks", value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> {
                if (tintIndex > 0) return -1;
                DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
                if (dyedColor != null) {
                    return 0xFF000000 | dyedColor.rgb();
                }
                return -1;
            }, ModItems.BOW_TIE.get(), ModItems.FEZ.get());
        }

    }

    @EventBusSubscriber(modid = "nosiphuscustomblocks")
    public static class ModEvents {

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {

        }

    }

}