package com.nosiphus.nosiphuscustomblocks.world.item;

import com.nosiphus.nosiphuscustomblocks.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final List<Supplier<? extends ItemLike>> MOD_TAB_ITEMS = new ArrayList<>();

    public static CreativeModeTab.Builder createTabBuilder() {
        return CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup.nosiphuscustomblocks"))
                .icon(() -> new ItemStack(ModItems.LIGHT_BLUE_STAINED_PLANKS.get()))
                .displayItems((displayParams, output) ->
                        MOD_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()))
                );
    }

    public static <T extends ItemLike> Supplier<T> addToTab(Supplier<T> itemLike) {
        MOD_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @Nullable
    public static <T extends ItemLike> Supplier<T> addToTabOptional(String modID, Supplier<T> itemLike) {
        if (Services.PLATFORM.isModLoaded(modID)) {
            MOD_TAB_ITEMS.add(itemLike);
            return itemLike;
        }
        return null;
    }
}