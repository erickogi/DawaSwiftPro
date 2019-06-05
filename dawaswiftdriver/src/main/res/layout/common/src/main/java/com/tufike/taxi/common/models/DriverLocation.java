package com.tufike.taxi.common.models;

import com.google.android.gms.maps.model.LatLng;

public class DriverLocation {
    public int id;
    public double distance;
    public LatLng location;
    public DriverLocation(int id, double distance, LatLng location){
        this. id = id;
        this.distance = distance;
        this.location = location;
    }
}
