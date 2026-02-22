package com.nosiphus.nosiphuscustomblocks.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

/**
 * Bridges the JSON files in the slope subfolder to the Java rendering engine.
 */
public class RoadModelLoader implements IGeometryLoader<RoadSlopeGeometry> {
    public static final RoadModelLoader INSTANCE = new RoadModelLoader();

    @Override
    public RoadSlopeGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) {
        // We look for the "paint" key defined in your slope/lane.json.
        String paintTexture = jsonObject.getAsJsonObject("textures").get("paint").getAsString();
        return new RoadSlopeGeometry(paintTexture);
    }
}