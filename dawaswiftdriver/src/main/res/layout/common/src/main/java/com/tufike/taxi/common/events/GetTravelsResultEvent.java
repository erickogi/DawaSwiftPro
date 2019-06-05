package com.tufike.taxi.common.events;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tufike.taxi.common.models.Travel;
import com.tufike.taxi.common.utils.LatLngDeserializer;
import com.tufike.taxi.common.utils.ServerResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetTravelsResultEvent extends BaseResultEvent {
    public ArrayList<Travel> travels;
    public GetTravelsResultEvent(int response, String travelEntities) {
        super(response);
        if(this.response != ServerResponse.OK)
            return;
        Type type = new TypeToken<List<Travel>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LatLng.class, new LatLngDeserializer());
        Gson customGson = gsonBuilder.create();
        this.travels = customGson.fromJson(travelEntities, type);
    }
}
