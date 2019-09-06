package com.dev.dawaswiftdriver.adapters.trips


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswiftdriver.R
import java.lang.ref.WeakReference


class PickUpItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var lat: TextView
    var lon: TextView


    init {
        this.itemVew = itemView

        name = itemView.findViewById(R.id.name)
        lat = itemView.findViewById(R.id.lat)
        lon = itemView.findViewById(R.id.lon)

        itemView.setOnClickListener(this)

    }


}
