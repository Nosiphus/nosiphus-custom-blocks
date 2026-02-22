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
    private final TextureAtlasSprite paint;
    private final Transformation transformation;

    public RoadSlopeBakedModel(TextureAtlasSprite asphalt, @Nullable TextureAtlasSprite paint, ModelState state) {
        this.asphalt = asphalt;
        this.paint = paint;
        this.transformation = state.getRotation();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        List<BakedQuad> quads = new ArrayList<>();

        if (side != null && side.getAxis().isHorizontal()) {
            // Side triangles remain asphalt-only.
            Direction worldWest = transformation.rotateTransform(Direction.WEST);
            Direction worldEast = transformation.rotateTransform(Direction.EAST);

            if (side == worldWest) quads.add(createTriangle(0f, side));
            if (side == worldEast) quads.add(createTriangle(1f, side));
        } else if (side == null) {
            // Layer 1: Base Asphalt
            quads.add(createTop(asphalt, 0.0f));

            // Layer 2: Paint Overlay (only if the sprite is present)
            if (paint != null) {
                // We use a tiny offset (0.0005) to prevent Z-fighting while keeping it tight.
                quads.add(createTop(paint, 0.0005f));
            }
        }

        return quads;
    }

    private BakedQuad createTop(TextureAtlasSprite sprite, float offset) {
        // High North (z=0), Low South (z=1)
        // Offset is applied to Y to lift the paint, and Z/X to keep it centered.
        Vector3f nwHigh = new Vector3f(0, 1 + offset, 0);
        Vector3f neHigh = new Vector3f(1, 1 + offset, 0);
        Vector3f seLow = new Vector3f(1, 0 + offset, 1);
        Vector3f swLow = new Vector3f(0, 0 + offset, 1);

        return BakedQuadHelper.create(
                transform(swLow), transform(seLow), transform(neHigh), transform(nwHigh),
                swLow, seLow, neHigh, nwHigh,
                sprite, Direction.UP
        );
    }

    private BakedQuad createTriangle(float x, Direction worldDir) {
        Vector3f bNorth = new Vector3f(x, 0, 0);
        Vector3f bSouth = new Vector3f(x, 0, 1);
        Vector3f tNorth = new Vector3f(x, 1, 0);

        Vector3f v1 = transform(bNorth);
        Vector3f v2 = transform(bSouth);
        Vector3f v3 = transform(tNorth);

        // Map local UVs for sides to ensure they rotate with the block.
        if (x == 0f) {
            return BakedQuadHelper.create(v1, v2, v3, v3, bNorth, bSouth, tNorth, tNorth, asphalt, worldDir);
        } else {
            return BakedQuadHelper.create(v2, v1, v3, v3, bSouth, bNorth, tNorth, tNorth, asphalt, worldDir);
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