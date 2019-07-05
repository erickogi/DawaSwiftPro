package com.dev.dawaswift.ui.cart.delivery.ui.createdelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.custom.Status
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.utils.OnDeliveryViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.GeoLocationUtills
import com.dev.dawaswift.adapters.delivery.DeliveryAdapter
import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.ui.CommonMainViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.schibstedspain.leku.*
import kotlinx.android.synthetic.main.delivery_fragment.*

class CreateDeliveryFragment : Fragment() {

    companion object {
        fun newInstance() = CreateDeliveryFragment()
    }


    private lateinit var viewModel: CommonMainViewModel
    private var selectedAddress: Address? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(com.dev.dawaswift.R.layout.delivery_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        // TODO: Use the ViewModel

        initAddress()
        error_view.visibility = View.VISIBLE

        fab.setOnClickListener {

            showPlacePicker()

        }


        error_view.visibility = View.GONE

        viewModel.observeAddress().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                true,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                update(it.data?.data)
                if (it.data != null && it!!.data!!.data != null && it.data!!.data!!.isNotEmpty()) {
                    error_view.visibility = View.GONE
                }
            } else {
                error_view.visibility = View.VISIBLE

            }
        })
        viewModel.fetchAddress()


    }

    private fun showPlacePicker() {
        val locationPickerIntent = LocationPickerActivity.Builder()
            // .withLocation(41.4036299, 2.1743558)


            .withGeolocApiKey("AIzaSyAkMWJ0p-e5jxdc643zcVQ6pfyYwESDQMc")
            //  intent.putExtra(LocationPickerActivity.SEARCH_ZONE_DEFAULT_LOCALE, true)
            // .withGooglePlacesEnabled()
            .withSearchZone("es_KE")
            // .withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
            // .withDefaultLocaleSearchZone()
            .shouldReturnOkOnBackPressed()
            //.withStreetHidden()
            //.withCityHidden()
            // .withZipCodeHidden()
            .withSatelliteViewHidden()
            .withGooglePlacesEnabled()
            .withGoogleTimeZoneEnabled()
            .withVoiceSearchHidden()
            .withUnnamedRoadHidden()
            .build(context!!)

        startActivityForResult(locationPickerIntent, 1213)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Log.d("dfgRESULT****", "OK" + Gson().toJson(data))
            var latitude = 0.0
            var longitude = 0.0
            if (requestCode == 1213) {
                latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("dfgLATITUDE****", latitude.toString())
                longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("dfgLONGITUDE****", longitude.toString())
//                val address = data.getStringExtra(LOCATION_ADDRESS)
//                Log.d("ADDRESS****", address.toString())
//                val postalcode = data.getStringExtra(ZIPCODE)
//                Log.d("POSTALCODE****", postalcode.toString())
//                val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
//                Log.d("BUNDLE TEXT****", bundle.getString("test"))
                val fullAddress = data.getParcelableExtra<android.location.Address>(ADDRESS)
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString())
                }
//                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
//                Log.d("TIME ZONE ID****", timeZoneId)
//                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
//                Log.d("TIME ZONE NAME****", timeZoneDisplayName)


            } else if (requestCode == 2) {
                latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("dfgLATITUDE****", latitude.toString())
                longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                Log.d("LekuPoi****", lekuPoi.toString())
            }

            val ad = GeoLocationUtills.getAddress(latitude, longitude, context)

            startDialog(ad, latitude, longitude)
        }
        if (resultCode == AppCompatActivity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED")
        }


    }

    private fun startDialog(
        addresss: LocationSearchModel?,
        latitude: Double,
        longitude: Double
    ) {

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Location")
        // set the custom layout
        val customLayout = layoutInflater.inflate(com.dev.dawaswift.R.layout.address_details, null)
        builder.setView(customLayout)


        val country: TextView = customLayout.findViewById(com.dev.dawaswift.R.id.country)

        val state: TextView = customLayout.findViewById(com.dev.dawaswift.R.id.state)
        val town: TextView = customLayout.findViewById(com.dev.dawaswift.R.id.town)
        val zip: TextView = customLayout.findViewById(com.dev.dawaswift.R.id.zip)
        val addrjess: TextView = customLayout.findViewById(com.dev.dawaswift.R.id.address)
        val name: TextInputEditText = customLayout.findViewById(com.dev.dawaswift.R.id.dname)
        val phone: TextInputEditText = customLayout.findViewById(com.dev.dawaswift.R.id.dedt_phone)

        country.text = addresss?.country
        state.text = addresss?.state
        town.text = addresss?.city
        zip.text = addresss?.postalCode
        addrjess.text = addresss?.address
        // add a button
        builder.setPositiveButton("OK") { dialog, which ->

            var addres: Address = Address()
            addres.country = addresss?.country
            addres.state = addresss?.state
            addres.town = addresss?.city
            addres.description = addresss?.address
            addres.name = name.text.toString()
            addres.mobile = phone.text.toString()
            addres.description = addresss?.address
            addres.latitude = latitude.toString()
            addres.longitude = longitude.toString()
            addres.zip = addresss?.postalCode

            viewModel.createAddress(addres)


        }

        builder.setNegativeButton("Dismiss") { dialog, which ->
            // send data from the AlertDialog to the Activity

            dialog.dismiss()


        }
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

    private fun update(data: List<Address>?) {
        if (data != null) {
            items = data as ArrayList<Address>
        } else {
            items = ArrayList()

        }
        adapter.refresh(items)

    }

    private lateinit var adapter: DeliveryAdapter
    private lateinit var items: ArrayList<Address>

    private fun initAddress() {
        items = ArrayList<Address>()
        adapter = context?.let {
            activity?.let { it1 ->
                DeliveryAdapter(2, it1, items, object : OnDeliveryViewItemClick {
                    override fun onEdit(position: Int) {

                    }

                    override fun onDelete(position: Int) {
                        viewModel.deleteAddress(items[position])

                    }

                    override fun onToDefault(position: Int, boolean: Boolean) {


                    }

                })
            }
        }!!


        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()


    }


}
