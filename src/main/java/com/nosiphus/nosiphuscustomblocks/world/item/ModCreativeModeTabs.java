package com.nosiphus.nosiphuscustomblocks.world.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "nosiphuscustomblocks");

    public static final List<Supplier<? extends ItemLike>> MOD_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> NOSIPHUSCUSTOMBLOCKS = CREATIVE_TABS.register("nosiphuscustomblocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.nosiphuscustomblocks"))
                    .icon(ModItems.LIGHT_BLUE_STAINED_PLANKS.get()::getDefaultInstance)
                    .displayItems((displayParams, output) ->
                            MOD_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        MOD_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @Nullable
    public static <T extends Item> RegistryObject<T> addToTabOptional(String modID, RegistryObject<T> itemLike) {
        if(ModList.get().isLoaded(modID)) {
            MOD_TAB_ITEMS.add(itemLike);
            return itemLike;
        }
        return null;
    }

}
