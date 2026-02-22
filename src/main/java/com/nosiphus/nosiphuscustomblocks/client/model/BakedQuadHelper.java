package com.nosiphus.nosiphuscustomblocks.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

public class BakedQuadHelper {
    public static BakedQuad create(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureAtlasSprite sprite, Direction direction) {
        int[] vertices = new int[32];

        fillVertex(vertices, 0, v1, sprite, direction);
        fillVertex(vertices, 1, v2, sprite, direction);
        fillVertex(vertices, 2, v3, sprite, direction);
        fillVertex(vertices, 3, v4, sprite, direction);

        return new BakedQuad(vertices, 0, direction, sprite, true);
    }

    private static void fillVertex(int[] vertices, int index, Vector3f pos, TextureAtlasSprite sprite, Direction dir) {
        int offset = index * 8;
        vertices[offset] = Float.floatToRawIntBits(pos.x());
        vertices[offset + 1] = Float.floatToRawIntBits(pos.y());
        vertices[offset + 2] = Float.floatToRawIntBits(pos.z());
        vertices[offset + 3] = -1; // Color

        // Horizontal UV projection: North/South faces use X, East/West use Z.
        float u = (dir.getAxis() == Direction.Axis.Z) ? pos.x() : pos.z();
        float v = 1 - pos.y();

        // Standardization for Sphax orientation.
        if (dir == Direction.NORTH || dir == Direction.EAST) u = 1 - u;

        vertices[offset + 4] = Float.floatToRawIntBits(sprite.getU(u * 16));
        vertices[offset + 5] = Float.floatToRawIntBits(sprite.getV(v * 16));
        vertices[offset + 6] = 0;
        vertices[offset + 7] = 0;
    }
}