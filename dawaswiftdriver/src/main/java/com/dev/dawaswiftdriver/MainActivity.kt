package com.dev.dawaswiftdriver

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.dev.common.data.local.PrefrenceManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() , OnMapReadyCallback , LocationListener, RequestCardFragment.OnFragmentInteractionListener{
    override fun onAccept(request: Request?) {
    }

    override fun onDecline(request: Request?) {
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
           // googleMap.setMyLocationEnabled(true);
           // googleMap.getUiSettings().setMyLocationButtonEnabled(true);

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        requestCardsAdapter = RequestsFragmentPagerAdapter(supportFragmentManager, ArrayList())
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setSupportActionBar(appbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.menu)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val bottomSheetBehavior = BottomSheetBehavior.from<View>(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED


        var request: ArrayList<Request>?=getRequests()
        requestCardsAdapter = RequestsFragmentPagerAdapter(supportFragmentManager, request)
        requests_view_pager.adapter = requestCardsAdapter
        requests_view_pager.offscreenPageLimit = 3

    }

    private fun getRequests(): ArrayList<Request>? {


        val tr=TravelSerializable()
        tr.pickupAddress="Nairobi Central Chemist"
        tr.destinationAddress="Kasarani Kwa mti "

        val request1=Request(tr,400.0,500.0,350.0)
        val request2=Request(tr,400.0,500.0,350.0)
        val request3=Request(tr,400.0,500.0,350.0)
        val request4=Request(tr,400.0,500.0,350.0)


        val requests: ArrayList<Request>?= ArrayList()
        requests?.add(request1)
        requests?.add(request2)
        requests?.add(request3)
        requests?.add(request4)

        return requests
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
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_taxi))
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


}
