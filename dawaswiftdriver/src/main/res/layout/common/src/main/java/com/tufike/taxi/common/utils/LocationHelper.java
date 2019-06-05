package com.tufike.taxi.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

public class LocationHelper {
    public static LocationManager locationManager;
    private Context con;
    private static double Latitude = 0;
    private static double Longitude = 0;
    public LocationHelper(Context context){
        con = context;
    }

    public void loadGps(LocationListener listener) {
        locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, listener);

    }

    public static int distFrom(double lat1, double lng1, double lat2, double lng2) {
        Location locationA = new Location("LocationA");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);
        Location locationB = new Location("LocationB");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);
        locationA.distanceTo(locationB);

        return (int)locationA.distanceTo(locationB);
    }
    public static double[] LatLngToDoubleArray(LatLng position) {
        return new double[] {position.latitude,position.longitude};
    }
    public static LatLng DoubleArrayToLatLng(double[] position) {
        return new LatLng(position[0],position[1]);
    }
}
