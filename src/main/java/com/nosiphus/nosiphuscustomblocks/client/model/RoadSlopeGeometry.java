package com.nosiphus.nosiphuscustomblocks.client.model;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

/**
 * The blueprint that gathers sprites and prepares the BakedModel.
 */
public class RoadSlopeGeometry implements IUnbakedGeometry<RoadSlopeGeometry> {
    private final String paintTexture;

    public RoadSlopeGeometry(String paintTexture) {
        this.paintTexture = paintTexture;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker,
                           Function<Material, TextureAtlasSprite> spriteGetter,
                           ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {

        TextureAtlasSprite asphalt = spriteGetter.apply(new Material(InventoryMenu.BLOCK_ATLAS,
                new ResourceLocation("minecraft:block/gray_concrete")));

        // Pass modelState to handle the Y-rotation from the blockstate.
        return new RoadSlopeBakedModel(asphalt, modelState);
    }
}