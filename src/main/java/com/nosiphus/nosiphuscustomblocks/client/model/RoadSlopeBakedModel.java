package com.nosiphus.nosiphuscustomblocks.client.model;

import com.mojang.math.Transformation;
import com.nosiphus.nosiphuscustomblocks.world.level.block.RoadBlock;
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
            Direction worldWest = transformation.rotateTransform(Direction.WEST);
            Direction worldEast = transformation.rotateTransform(Direction.EAST);
            if (side == worldWest) quads.add(createTriangle(0f, side));
            if (side == worldEast) quads.add(createTriangle(1f, side));
        } else if (side == null) {
            quads.add(createTop(asphalt, 0.0f));

            if (paint != null && state != null) {
                // theory #2: Direct Enum Detection
                RoadBlock.RoadTexture texture = state.getValue(RoadBlock.TEXTURE);
                RoadBlock.SlopeState slope = state.getValue(RoadBlock.SLOPE);

                boolean needsFlip = false;

                // Explicit Check logic based on your provided list
                if (slope == RoadBlock.SlopeState.NORTH ||
                        slope == RoadBlock.SlopeState.EAST) {
                    if (texture == RoadBlock.RoadTexture.SHOULDER_RIGHT ||
                            texture == RoadBlock.RoadTexture.EVEN_DIVIDER_RIGHT ||
                            texture == RoadBlock.RoadTexture.SHOULDER_DIVIDER_RIGHT) {
                        needsFlip = true;
                    }
                } else if (slope == RoadBlock.SlopeState.SOUTH ||
                        slope == RoadBlock.SlopeState.WEST) {
                    if (texture == RoadBlock.RoadTexture.SHOULDER_LEFT ||
                            texture == RoadBlock.RoadTexture.EVEN_DIVIDER_LEFT ||
                            texture == RoadBlock.RoadTexture.SHOULDER_DIVIDER_LEFT) {
                        needsFlip = true;
                    }
                }

                // Debug Logging
                if (rand.nextInt(100) == 0) { // Log 1% of calls to avoid spamming the console
                    System.out.println("[RoadSlope] Texture: " + texture.getSerializedName() + " | Slope: " + slope + " | Flip: " + needsFlip);
                }

                if (needsFlip) {
                    quads.add(createInvertedTop(paint, 0.0005f));
                } else {
                    quads.add(createTop(paint, 0.0005f));
                }
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

    private BakedQuad createInvertedTop(TextureAtlasSprite sprite, float offset) {
        Vector3f nwH = new Vector3f(0, 1 + offset, 0);
        Vector3f neH = new Vector3f(1, 1 + offset, 0);
        Vector3f seL = new Vector3f(1, 0 + offset, 1);
        Vector3f swL = new Vector3f(0, 0 + offset, 1);

        // Explicitly map the U/V corners
        // Standard: (0,0) -> (1,0) -> (1,1) -> (0,1)
        // Inverted: (1,1) -> (0,1) -> (0,0) -> (1,0)
        return BakedQuadHelper.create(
                transform(swL), transform(seL), transform(neH), transform(nwH), // Geometry
                new Vector3f(1, 0, 1), new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), // Explicit 180-degree flip
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