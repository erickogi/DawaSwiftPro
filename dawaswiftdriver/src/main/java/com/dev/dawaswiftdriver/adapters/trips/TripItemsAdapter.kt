package com.dev.dawaswiftdriver.adapters.trips


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.models.driver.trips.Trip
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswiftdriver.R

class TripItemsAdapter(
    internal var main: Activity,
    private var modelList: List<Trip>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<TripItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemViewHolder {
        var itemView: View? = null
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)
        return TripItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: TripItemViewHolder, position: Int) {

        val model = modelList!![position]



        holder.status.text = model.statusName
        holder.earnings.text = "Ksh  " + model.earnings
        try {
            holder.distance.text = "KM" + "  " + CommonUtils().roundOff(model.tripData?.distance.toString()) + ""
        } catch (x: Exception) {
            x.printStackTrace()
        }


        if (model.beginDateTime != null) {
            CommonUtils().displayFormattedDate(
                model.beginDateTime!!,
                holder.startDay,
                holder.startMonth,
                holder.startYear,
                holder.startTime
            )
        }
        if (model.endDateTime != null) {
            CommonUtils().displayFormattedDate(
                model.endDateTime!!,
                holder.endDay,
                holder.endMonth,
                holder.endYear,
                holder.endTime
            )
        }
    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Trip>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
