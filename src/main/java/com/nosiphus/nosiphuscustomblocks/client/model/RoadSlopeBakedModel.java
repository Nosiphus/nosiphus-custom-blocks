package com.nosiphus.nosiphuscustomblocks.client.model;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;
import java.util.ArrayList;
import java.util.List;

public class RoadSlopeBakedModel implements BakedModel {
    private final TextureAtlasSprite asphalt;
    private final Transformation transformation;

    public RoadSlopeBakedModel(TextureAtlasSprite asphalt, ModelState state) {
        this.asphalt = asphalt;
        this.transformation = state.getRotation();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        List<BakedQuad> quads = new ArrayList<>();

        // Ensure we only render triangles when requested for a horizontal side.
        if (side == null || !side.getAxis().isHorizontal()) return quads;

        // Calculate which world-side the model's West and East faces currently occupy.
        Direction worldWest = transformation.rotateTransform(Direction.WEST);
        Direction worldEast = transformation.rotateTransform(Direction.EAST);

        if (side == worldWest) {
            quads.add(createTriangle(0f, side)); // Local West
        } else if (side == worldEast) {
            quads.add(createTriangle(1f, side)); // Local East
        }

        return quads;
    }

    private BakedQuad createTriangle(float x, Direction worldDir) {
        // Base Geometry: North (z=0) is High, South (z=1) is Low.
        Vector3f bNorth = new Vector3f(x, 0, 0);
        Vector3f bSouth = new Vector3f(x, 0, 1);
        Vector3f tNorth = new Vector3f(x, 1, 0);

        // Apply world-space transformation.
        Vector3f v1 = transform(bNorth);
        Vector3f v2 = transform(bSouth);
        Vector3f v3 = transform(tNorth);

        // Restored Winding Order: West (x=0) is bNorth -> bSouth -> tNorth.
        if (x == 0f) {
            return BakedQuadHelper.create(v1, v2, v3, v3, asphalt, worldDir);
        } else {
            return BakedQuadHelper.create(v2, v1, v3, v3, asphalt, worldDir);
        }
    }

    private Vector3f transform(Vector3f pos) {
        Vector4f vec = new Vector4f(pos.x() - 0.5f, pos.y() - 0.5f, pos.z() - 0.5f, 1.0f);
        transformation.getMatrix().transform(vec);
        return new Vector3f(vec.x() + 0.5f, vec.y() + 0.5f, vec.z() + 0.5f);
    }

    @Override public TextureAtlasSprite getParticleIcon() { return asphalt; }
    @Override public boolean useAmbientOcclusion() { return true; }
    @Override public boolean isGui3d() { return true; }
    @Override public boolean usesBlockLight() { return true; }
    @Override public boolean isCustomRenderer() { return false; }
    @Override public ItemOverrides getOverrides() { return ItemOverrides.EMPTY; }
}