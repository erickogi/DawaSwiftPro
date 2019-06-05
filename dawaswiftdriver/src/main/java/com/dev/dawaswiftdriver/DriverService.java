package com.dev.dawaswiftdriver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.*;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class DriverService extends Service {
    LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    Socket socket;
    Vibrator vibe;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTracking(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //return new LocationServiceBinder();
        return null;
    }

    private void initializeLocationManager() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }
    }


    @SuppressLint("MissingPermission")
    public void startTracking(StartTrackingRequestEvent event) {
        initializeLocationManager();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };
        mFusedLocationClient.requestLocationUpdates(LocationRequest.create(), locationCallback, null);
    }

    public void stopTracking(StopTrackingRequestEvent event) {
        if (mFusedLocationClient != null) {
            try {
                mFusedLocationClient.removeLocationUpdates(locationCallback);
            } catch (Exception ignored) {

            }
        }
    }

    private Notification getNotification() {

        if(Build.VERSION.SDK_INT > 26) {
            NotificationChannel channel = new NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_01").setAutoCancel(true);
        return builder.build();
    }


    public class LocationServiceBinder extends Binder {
        public DriverService getService() {
            return DriverService.this;
        }
    }



}