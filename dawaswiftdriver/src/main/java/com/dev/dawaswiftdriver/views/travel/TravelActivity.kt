package com.dev.dawaswiftdriver.views.travel

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.models.driver.requests.PickUpPoint
import com.dev.common.models.driver.requests.TripRequest
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.viewUtils.OnViewItemClick
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.data.TripsViewModel
import com.dev.dawaswiftdriver.utils.KGDirectionsUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_travel.*
import kotlinx.android.synthetic.main.toolback_bar.*


class TravelActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {
    override fun onMapReady(p0: GoogleMap?) {
        gMap = p0
        gMap?.isTrafficEnabled = true
        setUpPolygon()


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
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
                PERMISSIONS_REQUEST
            )

            //Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
        }
    }

    private lateinit var viewModel: TripsViewModel

    var origin: LatLng? = null
    var dest: LatLng? = null

    private var mMap: GoogleMap? = null
    internal var driverPoint: Marker? = null
    internal val ACTIVITY_PROFILE = 11
    internal val ACTIVITY_WALLET = 12
    internal val ACTIVITY_TRAVEL = 14


    var pickupMarker: Marker? = null
    var driverMarker: Marker? = null
    var destinationMarker: Marker? = null
    var driverLocation: LatLng? = null
    var gMap: GoogleMap? = null

    private val PERMISSIONS_REQUEST = 1
    private val v: Float = 0.toFloat()
    private val lat: Double = 0.toDouble()
    private val lng: Double = 0.toDouble()
    private val handler: Handler? = null
    private val startPosition: LatLng? = null
    private val endPosition: LatLng? = null
    private val index: Int = 0
    private val next: Int = 0


    private var clientProfile: Profile? = null
    private var tripRequest: TripRequest? = null
    private var pickupPlaces: List<PickUpPoint>? = null

    internal var mapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)
        ViewUtils().makeFullScreen(this)

        linear_back.setOnClickListener {
            onBackPressed()
        }


        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)
        linear_call.visibility = View.VISIBLE
        slide_start.visibility = View.GONE
        slide_finish.visibility = View.GONE

        viewModel.observeCurrent().observe(this, Observer {
            ViewUtils.setStatus(
                this,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {

                if (it.data != null && it.data!!.data != null) {

                    tripRequest = it.data!!.data

                    if (it.data!!.data!!.dropOffPoint!!.lat != null && it.data!!.data!!.dropOffPoint!!.lon != null) {
                        dest = LatLng(
                            it.data!!.data!!.dropOffPoint!!.lat!!.toDouble(),
                            it.data!!.data!!.dropOffPoint!!.lon!!.toDouble()
                        )
                    }
                    pickupPlaces = it!!.data!!.data!!.pickUpPoints!!
                    setUpPolygon()

                }

                if (it.data!!.data!!.clientModel != null) {
                    linear_call.visibility = View.VISIBLE
                    clientProfile = it.data!!.data!!.clientModel
                }


                if (it.data!!.data!!.statusCode == Constants().TRIP_ONGOING) {
                    slide_start.visibility = View.GONE
                    slide_finish.visibility = View.VISIBLE
                }

                if (it.data!!.data!!.statusCode == Constants().TRIP_ENDED) {
                    slide_start.visibility = View.GONE
                    slide_finish.visibility = View.GONE
                }

                if (it.data!!.data!!.statusCode == Constants().TRIP_NOT_STARTED) {
                    slide_start.visibility = View.VISIBLE
                    slide_finish.visibility = View.GONE
                }


            }
        })
        viewModel.observeEnd().observe(this, Observer {
            ViewUtils.setStatus(
                this,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                var message: String =
                    " Hello .. Ride ended succesfully \n You have earned " + tripRequest?.earnings + " ksh \n Distance covered was " +
                            "" + distance_text + "\n Thank you for partnering with  Dawaswift"


                ViewUtils.simpleYesNoDialog(this,
                    "Dawaswift",
                    message,
                    SimpleDialogModel("Finish", null, null),
                    object : OnViewItemClick {
                        override fun onPositiveClick() {


                            val data = Intent()
                            setResult(RESULT_OK, data)
                            finish()


                        }

                        override fun onNegativeClick() {
                        }

                        override fun onNeutral() {
                        }

                    })


            }
        })
        viewModel.observeBegin().observe(this, Observer {
            ViewUtils.setStatus(
                this,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                viewModel.requestCurrent()

                try {
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                } catch (x: Exception) {
                    x.printStackTrace()
                }


            }
        })
        viewModel.requestCurrent()


        viewModel.liveProfile().observe(this, Observer {

            Log.d("CallOnResponse", "Profile ----- " + Gson().toJson(it))

            if (it.lat != null && it.lon != null) {

                origin = LatLng(it.lat!!.toDouble(), it.lon!!.toDouble())
                setUpPolygon()
            }


        })

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)

        call_button.setOnClickListener {
            checkPermission()
        }
        linear_call.setOnClickListener {
            checkPermission()
        }
        slide_start.setOnSlideCompleteListener {
            viewModel.requestBegin(tripRequest!!)
        }
        slide_finish.setOnSlideCompleteListener {

            viewModel.requestEnd(tripRequest!!)

        }


    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0" + clientProfile?.mobile))
        startActivity(intent)
    }

    fun setUpPolygon() {
        val height = 40
        val width = 40
        val bitmapdraw = resources.getDrawable(R.drawable.phamarcy) as BitmapDrawable
        val bitmapdrawhime = resources.getDrawable(R.drawable.homeong) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val bhome = bitmapdrawhime.bitmap

        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
        val smallMarkerhome = Bitmap.createScaledBitmap(bhome, width, height, false)


        var markerPlaces: MutableList<LatLng> = ArrayList()
        var markerPlacesl: MutableList<com.google.maps.model.LatLng> = ArrayList()

        if (pickupPlaces != null) {
            for (pickup in pickupPlaces as List<PickUpPoint>) {
                markerPlaces.add(LatLng(pickup.lat!!.toDouble(), pickup.lon!!.toDouble()))
                markerPlacesl.add(com.google.maps.model.LatLng(pickup.lat!!.toDouble(), pickup.lon!!.toDouble()))

                pickupMarker = gMap?.addMarker(
                    MarkerOptions()
                        .position(LatLng(pickup.lat!!.toDouble(), pickup.lon!!.toDouble()))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .title(pickup.location).snippet(pickup.location + "  " + pickup.lat + "  " + pickup.lon)

                )

                pickupMarker?.showInfoWindow()
            }
        }

        if (dest != null) {
            destinationMarker = gMap?.addMarker(
                MarkerOptions()
                    .position(dest!!)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerhome))
                    .title("Delivery Point").snippet("" + dest!!.latitude + "  " + dest!!.longitude)


            )
            destinationMarker?.showInfoWindow()
        }


        if (gMap != null) {


            Log.d(
                "kdgdsgf",
                Gson().toJson(driverLocation) + "  " + Gson().toJson(dest) + "  markerplaces " + Gson().toJson(
                    markerPlaces
                )
            )

            if (driverLocation != null && dest != null && pickupPlaces != null) {

//                val url = KGDirectionsUtils().getDirectionsUrl(driverLocation, dest, gMap, markerPlaces,time_text,distance_text)
//                Log.d("kdgdsgf", url)
//
//                val downloadTask = KGDirectionsUtils.DownloadTask()
//                downloadTask.execute(url)
//                KGDirectionsUtils().animateMarkerNew(driverLocation, dest, driverMarker)

                KGDirectionsUtils().setPoly(
                    com.google.maps.model.LatLng(driverLocation!!.latitude, driverLocation!!.longitude),
                    com.google.maps.model.LatLng(dest!!.latitude, dest!!.longitude),
                    gMap, markerPlacesl, time_text, distance_text


                )
                KGDirectionsUtils().animateMarkerNew(driverLocation, dest, driverMarker, gMap)

            }
        }
        if (driverLocation != null) {
            gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, 12F))
        }

    }

    fun moveDriverPin(lat: Double, lng: Double) {
        val driver = LatLng(lat, lng)
        driverPoint?.position = driver
    }

    private fun getLastKnownLocation() {
        val height = 50
        val width = 50
        val bitmapdraw = resources.getDrawable(R.drawable.motobike) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
                    .title("My Location").snippet("" + latLng.latitude + "  " + latLng.longitude)

            )
        else {

            driverPoint!!.position = latLng
        }


        driverLocation = latLng

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun onLocationChanged(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            latLng,
            if (mMap?.cameraPosition!!.zoom > 5) mMap?.cameraPosition!!.zoom else 12F
        )
        driverLocation = latLng
        mMap?.animateCamera(cameraUpdate)
        moveDriverPin(location.latitude, location.longitude)

        setUpPolygon()
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }
}
