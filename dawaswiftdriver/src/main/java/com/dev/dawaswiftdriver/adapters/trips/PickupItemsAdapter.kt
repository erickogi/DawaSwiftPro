package com.dev.dawaswiftdriver.adapters.trips


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.models.driver.requests.PickUpPoint
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswiftdriver.R

class PickupItemsAdapter(
    internal var main: Activity,
    private var modelList: List<PickUpPoint>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<PickUpItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickUpItemViewHolder {
        var itemView: View? = null
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.picup_item, parent, false)
        return PickUpItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: PickUpItemViewHolder, position: Int) {

        val model = modelList!![position]



        holder.name.text = model.location
        holder.lat.text = CommonUtils().roundOffD(model.lat)
        holder.lon.text = CommonUtils().roundOffD(model.lon)

    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<PickUpPoint>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
