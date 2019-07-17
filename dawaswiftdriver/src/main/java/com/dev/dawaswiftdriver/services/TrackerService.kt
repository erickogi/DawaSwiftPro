package com.dev.dawaswiftdriver.services

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

/**
 * @author kogi
 */
class TrackerService : Service() {

    protected var stopReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "received stop broadcast")
            // Stop the service when the notification is tapped
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
        loginToFirebase()
    }

    private fun buildNotification() {

    }


    private fun loginToFirebase() {
        val prefManager = PrefrenceManager(this)

        /// if(prefManager.getLoginStatus()==1) {
        val email = "" + prefManager.getProfile().profile!!.email!!
        val password = "" + prefManager.getProfile().profile!!.password!!

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "firebase auth success" + task.result!!.toString())
                requestLocationUpdates()
            } else {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { tasks ->

                        loginToFirebase()
                    }

            }
        }
        // }
    }

    private fun requestLocationUpdates() {
        Log.d(TAG, "called giv")

        val prefManager = PrefrenceManager(this)
        val request = LocationRequest()
        request.interval = 5000
        request.fastestInterval = 5000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(this)
        val path = "drivers" + "/" + prefManager.getProfile().profile!!.id + ""
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase

            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    val ref = FirebaseDatabase.getInstance().getReference(path)
                    val location = locationResult!!.lastLocation
                    Log.d(TAG, "lPermiss giv" + location!!.toString())

                    var signInRepository = OauthRepository(application)

                    var profile: Profile = signInRepository.fetchProfile()

                    if (profile == null) {
                        profile = Profile()
                    }

                    profile.lat = location.latitude.toString()
                    profile.lon = location.longitude.toString()

                    Log.d("PROFILESAVED", Gson().toJson(profile))
                    signInRepository.saveProfile(Oauth(profile))


                    if (location != null) {
                        Log.d(TAG, "location update $location")
                        ref.setValue(location)
                    }
                }
            }, null)
        }
    }

    companion object {

        private val TAG = "KLMFKLMKLD"
    }
}
