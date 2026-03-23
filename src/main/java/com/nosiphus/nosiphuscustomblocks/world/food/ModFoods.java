package com.nosiphus.nosiphuscustomblocks.world.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoods {

    public static final FoodProperties CUSTARD = stew(6).build();

    public static final FoodProperties FISH_FINGER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    private static FoodProperties.Builder stew(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.6F).usingConvertsTo(Items.BOWL);
    }

}
