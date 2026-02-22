package com.nosiphus.nosiphuscustomblocks.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

public class BakedQuadHelper {
    public static BakedQuad create(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4,
                                   Vector3f l1, Vector3f l2, Vector3f l3, Vector3f l4,
                                   TextureAtlasSprite sprite, Direction direction) {
        int[] vertices = new int[32];

        fillVertex(vertices, 0, v1, l1, sprite, direction);
        fillVertex(vertices, 1, v2, l2, sprite, direction);
        fillVertex(vertices, 2, v3, l3, sprite, direction);
        fillVertex(vertices, 3, v4, l4, sprite, direction);

        // The 'true' at the end tells the engine to respect the sprite's shading.
        return new BakedQuad(vertices, 0, direction, sprite, true);
    }

    private static void fillVertex(int[] vertices, int index, Vector3f pos, Vector3f local, TextureAtlasSprite sprite, Direction dir) {
        int offset = index * 8;
        vertices[offset] = Float.floatToRawIntBits(pos.x());
        vertices[offset + 1] = Float.floatToRawIntBits(pos.y());
        vertices[offset + 2] = Float.floatToRawIntBits(pos.z());
        vertices[offset + 3] = -1; // Color

        // Local-based UVs prevent stretching and misalignment.
        float u, v;
        if (dir == Direction.UP) { // Sloped Top
            // U = Width (Local X), V = Depth (Local Z).
            u = local.x() * 16;
            v = local.z() * 16;
        } else { // Sides
            u = local.z() * 16;
            v = (1 - local.y()) * 16;
        }

        vertices[offset + 4] = Float.floatToRawIntBits(sprite.getU(u));
        vertices[offset + 5] = Float.floatToRawIntBits(sprite.getV(v));

        vertices[offset + 6] = 0; // Lightmap
        vertices[offset + 7] = (dir.getStepX() & 0xFF) | ((dir.getStepY() & 0xFF) << 8) | ((dir.getStepZ() & 0xFF) << 16);
    }
}