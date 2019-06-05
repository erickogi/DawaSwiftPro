package com.tufike.taxi.common.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LatLngDeserializer implements JsonDeserializer<LatLng>, JsonSerializer<LatLng> {
    @Override
    public LatLng deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {

        JsonObject jsonObject = json.getAsJsonObject();

        return new LatLng(
                jsonObject.get("y").getAsDouble(),
                jsonObject.get("x").getAsDouble());
    }

    @Override
    public JsonElement serialize(LatLng src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.longitude);
        jsonObject.addProperty("y", src.latitude);
        return jsonObject;
    }
}