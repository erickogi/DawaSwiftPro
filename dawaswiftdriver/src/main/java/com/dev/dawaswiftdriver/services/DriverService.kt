package com.dev.dawaswiftdriver.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.dev.dawaswiftdriver.StartTrackingRequestEvent
import com.dev.dawaswiftdriver.StopTrackingRequestEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.net.Socket


class DriverService : Service() {
    internal lateinit var locationCallback: LocationCallback
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    internal var socket: Socket? = null
    internal lateinit var vibe: Vibrator

    private val notification: Notification
        get() {

            if (Build.VERSION.SDK_INT > 26) {
                val channel = NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(applicationContext, "channel_01").setAutoCancel(true)
            return builder.build()
        }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        vibe = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTracking(null)
    }

    override fun onBind(intent: Intent): IBinder? {
        //return new LocationServiceBinder();
        return null
    }

    private fun initializeLocationManager() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
    }


    @SuppressLint("MissingPermission")
    fun startTracking(event: StartTrackingRequestEvent) {
        initializeLocationManager()
        locationCallback = object : LocationCallback() {
        }
        mFusedLocationClient!!.requestLocationUpdates(LocationRequest.create(), locationCallback, null)
    }

    fun stopTracking(event: StopTrackingRequestEvent?) {
        if (mFusedLocationClient != null) {
            try {
                mFusedLocationClient!!.removeLocationUpdates(locationCallback)
            } catch (ignored: Exception) {

            }

        }
    }


    inner class LocationServiceBinder : Binder() {
        val service: DriverService
            get() = this@DriverService
    }


}