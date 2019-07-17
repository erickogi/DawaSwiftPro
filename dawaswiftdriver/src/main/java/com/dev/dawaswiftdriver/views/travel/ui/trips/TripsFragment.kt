package com.dev.dawaswiftdriver.views.travel.ui.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.custom.Status
import com.dev.common.models.driver.trips.Trip
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.adapters.trips.TripItemsAdapter
import com.dev.dawaswiftdriver.data.TripsViewModel
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.trips_fragment.*

class TripsFragment : Fragment() {

    companion object {
        fun newInstance() = TripsFragment()
    }

    private lateinit var viewModel: TripsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.trips_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)
        // TODO: Use the ViewModel
        initOrders()
        viewModel.trips()
        viewModel.observeTrips().observe(this, Observer {

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

                updateOrders(it.data?.data)
            }
        })
        error_btn.setOnClickListener {
            viewModel.trips()
        }
    }

    private fun updateOrders(data: List<Trip>?) {

        items = data as ArrayList<Trip>
        adapter.refresh(items)

    }

    private lateinit var adapter: TripItemsAdapter
    private lateinit var items: ArrayList<Trip>

    private fun initOrders() {
        items = ArrayList<Trip>()
        adapter = context?.let {
            activity?.let { it1 ->
                TripItemsAdapter(it1, items, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {


                    }

                    override fun onLongClickListener(position: Int) {
                    }


                })
            }
        }!!


        recyclertrips.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclertrips.itemAnimator = DefaultItemAnimator()
        recyclertrips.adapter = adapter
        adapter.notifyDataSetChanged()


    }


}
