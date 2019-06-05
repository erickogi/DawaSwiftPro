package com.tufike.taxi.common.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class TravelType {
    public Integer id;
    public String title;
    public String icon;
    public Double initial;
    @SerializedName("every_km")
    public Double everyKm;
    @SerializedName("every_minute_gone")
    public Double everyMinuteGone;
    @SerializedName("every_minute_wait")
    public Double everyMinuteWait;

    public TravelType fromJson(String json) {
        return (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create().fromJson(json, TravelType.class);
    }

}
