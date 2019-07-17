package com.dev.dawaswiftdriver.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.listeners.OnViewItemClick
import com.dev.common.models.custom.Status
import com.dev.common.models.driver.requests.TripRequest
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.adapters.RequestsFragmentPagerAdapter
import com.dev.dawaswiftdriver.data.TripsViewModel
import com.dev.dawaswiftdriver.interfaces.RequestAction
import com.dev.dawaswiftdriver.services.TrackerService
import com.dev.dawaswiftdriver.views.account.ui.myprofile.Profile
import com.dev.dawaswiftdriver.views.requests.RequestCardFragment
import com.dev.dawaswiftdriver.views.travel.TravelActivity
import com.dev.dawaswiftdriver.views.travel.Trips
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, RequestAction,
    RequestCardFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        when (item.itemId) {
            R.id.nav_item_travels -> startActivity(Intent(this@MainActivity, Trips::class.java))
            R.id.nav_item_profile -> startActivity(Intent(this@MainActivity, Profile::class.java))
            R.id.nav_item_about -> startActivity(Intent(this@MainActivity, Profile::class.java))
            R.id.nav_item_exit -> logout()

        }

        return true
    }


    override fun onAccept(request: TripRequest) {

        viewModel.requestAccept(request)
    }


    override fun onDecline(request: TripRequest) {

        viewModel.requestReject(request)

    }

    override fun accept(request: TripRequest) {
        viewModel.requestAccept(request)
    }

    override fun reject(request: TripRequest) {
        viewModel.requestReject(request)
    }

    fun logout() {
        ViewUtils.simpleYesNoDialog(this,
            "Dawaswift",
            "Please confirm that you want to log out",
            SimpleDialogModel("Yes Proceed", "No", null),
            object : OnViewItemClick {
                override fun onPositiveClick() {


                    viewModel.logout()
                    finish()

                }

                override fun onNegativeClick() {
                }

                override fun onNeutral() {
                }

            })

    }
    override fun onMapReady(p0: GoogleMap?) {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            mMap = p0
            mMap?.isTrafficEnabled = true
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
            getLastKnownLocation()
            mMap?.isMyLocationEnabled = true
            mMap?.uiSettings?.isMyLocationButtonEnabled = true

        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST)

            //Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
        }




//        if (resources.getBoolean(R.bool.isNightMode)) {
//            val success = mMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    this, R.raw.map_night
//                )
//            )
//            if (!success)
//                Log.e("MapsActivityRaw", "Style parsing failed.")
//        }
    }
    private val PERMISSIONS_REQUEST = 1

    internal var SP: PrefrenceManager?=null
    private var mMap: GoogleMap? = null
    internal var driverPoint: Marker? = null
    private var requestCardsAdapter: RequestsFragmentPagerAdapter? = null
    internal val ACTIVITY_PROFILE = 11
    internal val ACTIVITY_WALLET = 12
    internal val ACTIVITY_TRAVEL = 14
    internal var mapFragment: SupportMapFragment?=null
    private var loadingRequestsLoadingDialog: MaterialDialog? = null
    private lateinit var viewModel: TripsViewModel

    private var trips: List<TripRequest> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, TrackerService::class.java))
        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)

        viewModel.observeCurrent().observe(this, Observer {
            ViewUtils.setStatus(
                this,
                bottom_sheet,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {

                if (it.data != null && it.data!!.data != null) {

                    startActivity(Intent(this@MainActivity, TravelActivity::class.java))

                }


            }
        })
        viewModel.requestCurrent()
        viewModel.observeAccept().observe(this, Observer {

            ViewUtils.setStatus(
                this,
                bottom_sheet,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                if (it.data != null && it.data!!.data != null) {

                    startActivity(Intent(this@MainActivity, TravelActivity::class.java))

                }

            }


        })
        viewModel.observeRequests().observe(this, Observer {

            ViewUtils.setStatus(
                this,
                bottom_sheet,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {

                if (it.data != null && it.data!!.data != null) {
                    trips = it.data!!.data!!
                } else {
                    trips = ArrayList()
                }
                requestCardsAdapter = RequestsFragmentPagerAdapter(supportFragmentManager, trips)
                requests_view_pager.adapter = requestCardsAdapter
                requests_view_pager.offscreenPageLimit = 3


            }


        })
        viewModel.observeReject().observe(this, Observer {

            ViewUtils.setStatus(
                this,
                bottom_sheet,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                viewModel.trips()
            }


        })

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        requestCardsAdapter =
            RequestsFragmentPagerAdapter(supportFragmentManager, ArrayList())
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setSupportActionBar(appbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.menu)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val bottomSheetBehavior = BottomSheetBehavior.from<View>(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED



        navigation_view.setNavigationItemSelectedListener(this)

        viewModel.liveProfile().observe(this, Observer {

            if (it != null) {

                CommonUtils().loadImage(this.applicationContext, it.avatar, n_image as ImageView)

            }
        })
        request_show_fab.setOnClickListener {
            viewModel.requests()

        }




    }


    private fun popOutFragments() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
    fun moveDriverPin(lat: Double, lng: Double) {
        val driver = LatLng(lat, lng)
        driverPoint?.position = driver
    }
    private fun getLastKnownLocation() {
        val height = 100
        val width = 100
        val bitmapdraw = resources.getDrawable(R.drawable.motobike) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val manager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        val providers: List<String>
        if (manager != null) {
            providers = manager.getProviders(true)
        } else
            return
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = manager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
        val latLng: LatLng
        if (bestLocation == null)
            latLng = LatLng(
                java.lang.Float.parseFloat(getString(R.string.defaultLocation).split(",")[0]).toDouble(),
                java.lang.Float.parseFloat(getString(R.string.defaultLocation).split(",")[1]).toDouble()
            )
        else
            latLng = LatLng(bestLocation.latitude, bestLocation.longitude)
        if (driverPoint == null)
            driverPoint = mMap?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            )
        else
            driverPoint!!.position = latLng

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
    }
    override fun onLocationChanged(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
       val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            latLng,
            if (mMap?.cameraPosition!!.zoom > 5) mMap?.cameraPosition!!.zoom else 16F
        )
        mMap?.animateCamera(cameraUpdate)

        moveDriverPin(location.latitude, location.longitude)
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.requestCurrent()
        viewModel.requests()

    }
}
