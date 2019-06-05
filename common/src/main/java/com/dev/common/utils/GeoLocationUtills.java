package com.dev.common.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.dev.common.models.location.LocationSearchModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eric on 12/27/2017.
 */

public class GeoLocationUtills {
    public static LocationSearchModel getAddress(double latitude, double longitude, Context context) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        String address = "";
        LocationSearchModel locationSearchModel = null;
        try {
            geocoder = new Geocoder(context, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            locationSearchModel = new LocationSearchModel(country, city, state, postalCode, address, knownName);
        } catch (Exception nm) {
            nm.printStackTrace();

        }
        return locationSearchModel;

    }
}
