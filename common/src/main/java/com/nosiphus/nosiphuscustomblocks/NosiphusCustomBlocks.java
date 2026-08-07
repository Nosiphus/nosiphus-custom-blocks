package com.nosiphus.nosiphuscustomblocks;

import com.nosiphus.nosiphuscustomblocks.world.item.ModItems;
import com.nosiphus.nosiphuscustomblocks.world.level.block.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NosiphusCustomBlocks {

    public static final String MOD_ID = "nosiphuscustomblocks";
    public static final String MOD_NAME = "Nosiphus Custom Blocks";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        ModBlocks.init();
        ModItems.init();
    }

    public static int getDyedItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex > 0) return -1;
        DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
        if (dyedColor != null) {
            return 0xFF000000 | dyedColor.rgb();
        }
        return -1;
    }

}