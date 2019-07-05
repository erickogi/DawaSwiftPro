package com.dev.dawaswift

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.cabinzz.ui.main.ProfileFragment
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.listeners.OnViewItemClick
import com.dev.common.models.oauth.Oauth
import com.dev.common.ui.auth.AuthViewModel
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.ui.MainFragment
import com.dev.dawaswift.ui.OrdersFragment
import com.dev.dawaswift.ui.productview.MenuFragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val menuFragment = MenuFragment()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)



        ViewUtils().makeFullScreen(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
        setHomeSelectedNav()
        //val badge = navigation.showBadge(R.id.navigation_profile)
        //badge.number = 2
        viewModel.liveProfile().observe(this, Observer {
            if (it != null) {

                if (PrefrenceManager(this).getFirebase() != "") {

                    val pt = PrefrenceManager(this).getFirebase()
                    if (it.firebaseToken != PrefrenceManager(this).getFirebase()) {

                        it.firebaseToken = pt
                        viewModel.updateProfile(Oauth(it))


                    }
                }
            }
        })

    }

    private fun popOutFragments() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
    fun setCatSelectedNav() {

        if (navigation.selectedItemId != R.id.navigation_categories) {
            navigation.selectedItemId = R.id.navigation_categories
        }
    }

    fun setProfileSelectedNav() {
        if (navigation.selectedItemId != R.id.navigation_profile) {
            navigation.selectedItemId = R.id.navigation_profile
        }
    }

    fun setHomeSelectedNav() {

        if (navigation.selectedItemId != R.id.navigation_home) {
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                popOutFragments()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                popOutFragments()

                supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment.newInstance())
                    .addToBackStack("profile").commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_categories -> {
                popOutFragments()

                supportFragmentManager.beginTransaction().replace(R.id.container, OrdersFragment.newInstance())
                    .addToBackStack("Fav").commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        if (f is MainFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are You Sure?")


            builder.setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }

            builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()            //additional code
        } else {
            popOutFragments()
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    fun dismissSheet() {
        menuFragment.dismiss()
    }

    fun showSheet() {
        menuFragment.show(supportFragmentManager, menuFragment.tag)

    }


    private val PERMISSIONS_REQUEST = 1
    private val PERMISSIONS_REQUEST_2 = 2
    private var googleApiClient: GoogleApiClient? = null


    private fun settings() {

        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(applicationContext).addApi(LocationServices.API).build()
            (googleApiClient as GoogleApiClient).connect()

            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = (30 * 1000).toLong()
            locationRequest.fastestInterval = (5 * 1000).toLong()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            // **************************

            builder.setAlwaysShow(true) // this is the key ingredient


            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            @Override
            fun onResult(@NonNull result: Result<LocationSettingsRequest>) {
//                                result.setResultCallback({ results: LocationSettingsResult ->
//
//                                    val status = results.status
//
//                                    val state = results.locationSettingsStates
//
//                                    when (status.statusCode) {
//
//                                        LocationSettingsStatusCodes.SUCCESS -> {
//                                        }
//
//                                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
//
//
//                                            try {
//
//                                                status.startResolutionForResult(this@AuthActivity, 1000)
//
//                                            } catch (e: IntentSender.SendIntentException) {
//
//                                            }
//
//                                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
//                                        }
//                                    }
//
//                                } as ResultCallback<LocationSettingsResult>)

            }
        }
    }

    private fun startTrackerService() {
        startService(Intent(this, TrackerService::class.java))
        // finish();
    }

    private fun setUp() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            settings()

        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {

            ViewUtils.simpleYesNoDialog(
                this@MainActivity,
                "Location Permission",
                "To make the experience of using this app better  turn on your location settings by accepting in the next dialog\n\n\n.",
                SimpleDialogModel("Okay", null, null),
                object : OnViewItemClick {
                    override fun onPositiveClick() {


                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST
                        )
                    }

                    override fun onNegativeClick() {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST
                        )
                    }

                    override fun onNeutral() {
//                        ActivityCompat.requestPermissions(
//                            this@SplashActivity,
//                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                            PERMISSIONS_REQUEST
//                        )
                    }

                })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Start the service when the permission is granted
            startTrackerService()
        } else {
            //finish();
        }



        if (requestCode == PERMISSIONS_REQUEST_2 && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {


        } else {
            //finish();
        }
    }


    private fun camera() {
        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {

            ViewUtils.simpleYesNoDialog(
                this@MainActivity,
                "Location Permission",
                "To make the experience of using this app better  turn on your location settings by accepting in the next dialog\n\n\n.",
                SimpleDialogModel("Okay", null, null),
                object : OnViewItemClick {
                    override fun onPositiveClick() {


                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            PERMISSIONS_REQUEST_2
                        )
                    }

                    override fun onNegativeClick() {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            PERMISSIONS_REQUEST_2
                        )
                    }

                    override fun onNeutral() {
//                        ActivityCompat.requestPermissions(
//                            this@SplashActivity,
//                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                            PERMISSIONS_REQUEST
//                        )
                    }

                })

        }
    }



}
