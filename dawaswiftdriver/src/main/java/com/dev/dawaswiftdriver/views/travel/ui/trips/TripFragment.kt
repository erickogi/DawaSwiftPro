package com.dev.dawaswiftdriver.views.travel.ui.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.driver.requests.PickUpPoint
import com.dev.common.models.driver.trips.Trip
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.adapters.trips.PickupItemsAdapter
import com.dev.dawaswiftdriver.data.TripsViewModel
import kotlinx.android.synthetic.main.trip_fragment.*

class TripFragment : Fragment() {

    companion object {
        fun newInstance() = TripFragment()
    }

    private lateinit var viewModel: TripsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.trip_fragment, container, false)
    }

    var trip: Trip? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)
        // TODO: Use the ViewModel
        initOrders()



        try {
            trip = arguments?.getSerializable("data") as Trip
        } catch (x: Exception) {
            x.printStackTrace()
        }
        if (trip != null) {

            edt_distance.setText(CommonUtils().roundOffD(trip!!.tripData?.distance))
            edt_earnings.setText(trip!!.earnings)
            edt_drop_off_point.setText(trip!!.tripData?.dropOffPoint?.location)

            if (trip!!.tripData?.pickUpPoints != null) {

                updateOrders(trip!!.tripData?.pickUpPoints)
            }
        }

    }

    private fun updateOrders(data: List<PickUpPoint>?) {

        items = data as ArrayList<PickUpPoint>
        adapter.refresh(items)

    }

    private lateinit var adapter: PickupItemsAdapter
    private lateinit var items: ArrayList<PickUpPoint>

    private fun initOrders() {
        items = ArrayList<PickUpPoint>()
        adapter = context?.let {
            activity?.let { it1 ->
                PickupItemsAdapter(it1, items, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {


                    }

                    override fun onLongClickListener(position: Int) {
                    }


                })
            }
        }!!


        recyclerpickup.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerpickup.itemAnimator = DefaultItemAnimator()
        recyclerpickup.adapter = adapter
        adapter.notifyDataSetChanged()


    }


}
