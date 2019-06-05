package com.tufike.taxi.common.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.models.Driver;
import com.tufike.taxi.common.models.Rider;

public class CommonUtils {
    public static Driver driver;
    public static Rider rider;
    public static int timeOut = 20000;
    public final static int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    public static CountDownTimer currentTimer;

    public static void displayPromptForEnablingGPS(final AppCompatActivity activity) {
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = activity.getString(R.string.prompt_enable_gps);
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogBuilder.show(activity, message, AlertDialogBuilder.DialogButton.OK_CANCEL, result -> {
                if (result == AlertDialogBuilder.DialogResult.OK)
                    activity.startActivity(new Intent(action));
            });
        }
    }

    public static boolean isInternetDisabled(AppCompatActivity activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)
            return true;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return !haveConnectedWifi && !haveConnectedMobile;
    }

    public static boolean isGPSEnabled (AppCompatActivity activity){
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        else {
            return false;
        }
    }
}