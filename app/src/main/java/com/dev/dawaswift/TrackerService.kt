package com.dev.dawaswift

import android.Manifest
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson


class TrackerService : Service() {
    protected var stopReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            unregisterReceiver(this)
            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        buildNotification()
        requestLocationUpdates()
        Log.d("PROFILESAVED", "loca str")


    }

    private fun buildNotification() {


    }

    private fun requestLocationUpdates() {
        val prefrenceManager = PrefrenceManager(this)

        val request = LocationRequest()
        request.interval = 10000
        request.fastestInterval = 500
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(this)

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            Log.d("PROFILESAVED", "permission ok")

            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    val location = locationResult!!.lastLocation
                    if (location != null) {

                        Log.d("PROFILESAVED", Gson().toJson(location))

                        var signInRepository = OauthRepository(application)

                        var profile: Profile = signInRepository.fetchProfile()

                        if (profile == null) {
                            profile = Profile()
                        }

                        profile.lat = location.latitude.toString()
                        profile.lon = location.longitude.toString()

                        Log.d("PROFILESAVED", Gson().toJson(profile))
                        signInRepository.saveProfile(Oauth(profile))


                    } else {
                        Log.d("PROFILESAVED", "location  null")

                    }

                }
            }, null)
        }
    }

    companion object {

        private val TAG = TrackerService::class.java.simpleName
    }
}