package com.dev.dawaswiftdriver

import com.google.android.gms.maps.model.LatLng
import com.google.gson.*

import java.lang.reflect.Type

class LatLngDeserializer : JsonDeserializer<LatLng>, JsonSerializer<LatLng> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LatLng {

        val jsonObject = json.asJsonObject

        return LatLng(
            jsonObject.get("y").asDouble,
            jsonObject.get("x").asDouble
        )
    }

    override fun serialize(src: LatLng, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("x", src.longitude)
        jsonObject.addProperty("y", src.latitude)
        return jsonObject
    }
}