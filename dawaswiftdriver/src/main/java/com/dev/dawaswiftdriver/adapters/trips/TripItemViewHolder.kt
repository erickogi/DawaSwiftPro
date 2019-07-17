package com.dev.dawaswiftdriver.adapters.trips


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswiftdriver.R
import java.lang.ref.WeakReference


class TripItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var status: TextView
    var distance: TextView
    var earnings: TextView

    var startYear: TextView
    var startMonth: TextView
    var startDay: TextView
    var startTime: TextView


    var endYear: TextView
    var endMonth: TextView
    var endDay: TextView
    var endTime: TextView


    init {
        this.itemVew = itemView

        startTime = itemView.findViewById(R.id.time)
        startDay = itemView.findViewById(R.id.day)
        startMonth = itemView.findViewById(R.id.month)
        startYear = itemView.findViewById(R.id.year)

        endTime = itemView.findViewById(R.id.time_end)
        endDay = itemView.findViewById(R.id.day_end)
        endMonth = itemView.findViewById(R.id.month_end)
        endYear = itemView.findViewById(R.id.year_end)

        distance = itemView.findViewById(R.id.distance)
        earnings = itemView.findViewById(R.id.earnings)
        status = itemView.findViewById(R.id.status)

        itemView.setOnClickListener(this)

    }


}
