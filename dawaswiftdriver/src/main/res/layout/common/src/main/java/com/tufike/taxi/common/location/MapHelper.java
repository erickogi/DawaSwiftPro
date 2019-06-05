package com.tufike.taxi.common.location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

public class MapHelper {
    public static void centerLatLngsInMap(GoogleMap googleMap, List<LatLng> locations, boolean animate){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng location : locations)
            builder.include(location);
        LatLngBounds bounds = builder.build();
        int padding = 100; // in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.setOnMapLoadedCallback(() -> {
            if(animate)
                googleMap.animateCamera(cu);
            else
                googleMap.moveCamera(cu);
        });
    }
}
