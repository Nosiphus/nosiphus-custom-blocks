package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.world.item.ModCreativeModeTabs;
import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("nosiphuscustomblocks")
public class NosiphusCustomBlocks {

    public static final Logger LOGGER = LogManager.getLogger();
    public NosiphusCustomBlocks() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(eventBus);
        ModCreativeModeTabs.CREATIVE_TABS.register(eventBus);
        ModItems.ITEMS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    @Mod.EventBusSubscriber(modid = "nosiphuscustomblocks", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register(((itemStack, i) ->
                    i > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack)),
                    ModItems.BOW_TIE.get());
        }

    }

    @Mod.EventBusSubscriber(modid = "nosiphuscustomblocks", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {

        }

    }

}