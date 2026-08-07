package com.nosiphus.nosiphuscustomblocks.world.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final Holder<ArmorMaterial> BOW_TIE = register("bow_tie",
            Util.make(new EnumMap<>(ArmorItem.Type.class), protection -> {
                protection.put(ArmorItem.Type.BOOTS, 0);
                protection.put(ArmorItem.Type.LEGGINGS, 0);
                protection.put(ArmorItem.Type.CHESTPLATE, 1);
                protection.put(ArmorItem.Type.HELMET, 1);
                protection.put(ArmorItem.Type.BODY, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            () -> Ingredient.of(Items.LEATHER),
            List.of(
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("nosiphuscustomblocks", "bow_tie"), "", true),
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("nosiphuscustomblocks", "bow_tie"), "_overlay", false)
            )
    );

    public static Holder<ArmorMaterial> bootstrap(Registry<ArmorMaterial> registry) {
        return BOW_TIE;
    }

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("nosiphuscustomblocks", name)));
        return register(name, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngridient,
            List<ArmorMaterial.Layer> layers
    ) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                ResourceLocation.withDefaultNamespace(name),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance)
        );
    }

}
